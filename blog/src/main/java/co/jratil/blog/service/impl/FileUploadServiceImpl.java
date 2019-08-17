package co.jratil.blog.service.impl;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.service.FileUploadService;
import co.jratil.blog.utils.ResultVOUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传服务
 * 选择上传到七牛云保存
 *
 * @date 2019-07-31 16:44
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.link-pre}")
    private String linkPre;

    @Autowired
    private Auth auth;

    @Autowired
    private Configuration configuration;

    @Override
    public Map<String, Object> uploadFile(File file) {
        return null;
    }

    @Override
    public Map<String, Object> uploadFile(InputStream inputStream) {
        return null;
    }

    /**
     * 上传图片
     *
     * @param inputStream 图片文件转换后的输入流
     * @param suffix      文件的后缀
     * @return 返回给前端的结果，按照前段格式返回
     */
    @Override
    public Map<String, Object> uploadImage(InputStream inputStream, String suffix) {
        UploadManager uploadManager = new UploadManager(configuration);

        String filename = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody",
                "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        String upToken = auth.uploadToken(bucket, null, 3600L, putPolicy);

        Response response;
        String picAddr = "";
        String fileSize = "";
        try {
            response = uploadManager.put(inputStream, filename, upToken, null, null);
            // 获取上传成功后返回的保存图片的名称
            picAddr = new Gson().fromJson(response.bodyString(), Map.class).get("key").toString();
            // 获取上传图片后保存的图片的大小
            fileSize = new Gson().fromJson(response.bodyString(), Map.class).get("fsize").toString();
        } catch (QiniuException e) {
            response = e.response;
            log.error("【上传服务】上传图片发生出错误！{}", response.toString());
            e.printStackTrace();

            // 前端editor.md编辑器要求返回的格式
            Map<String, Object> result = new HashMap<String, Object>(3) {{
                put("url", "");
                put("success", 0);
            }};
            result.put("message", "上传图片失败" + response.toString());
            return result;
        }

        String fileLink = linkPre + picAddr;

        Map<String, Object> result = new HashMap<String, Object>(3) {{
            put("url", fileLink);
            put("success", 1);
            put("message", "上传成功");
        }};
        result.put("fsize", fileSize);

        return result;
    }

    /**
     * 从七牛云删除文件
     *
     * @param key 保存的文件的 key（相当于保存在七牛云的文件名称）
     * @return 删除的结果
     */
    @Override
    public ResultVO delete(String key) {
        BucketManager bucketManager = new BucketManager(auth, configuration);
        Response response = null;
        try {
            response = bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            log.error("【删除服务器文件】删除文件出错，response={}", response);
            return ResultVOUtil.error(ResultEnum.SERVER_FILE_DELETE_ERROR.getCode(),
                    ResultEnum.SERVER_FILE_DELETE_ERROR.getMessage());
        }
        return ResultVOUtil.success();
    }
}
