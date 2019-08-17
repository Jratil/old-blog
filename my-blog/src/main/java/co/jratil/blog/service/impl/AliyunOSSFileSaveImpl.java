package co.jratil.blog.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 作废，本来是用阿里云 oss储存图片
 * 然后发现购买储存空间后，还要购买什么下行流量，挺贵的
 * 对于我这种穷逼，只好用自己服务器储存了
 *
 * @create 2019-07-30 11:19
 */
@Slf4j
public class AliyunOSSFileSaveImpl  {

    /**
     * oss处访问图片的 url
     */
//    @Value("${aliyun.oss.address}")
    private String address;

    /**
     * oss的 endpoint
     */
//    @Value("${aliyun.oss.end-point}")
    private String endPoint;

//    @Value("${aliyun.oss.access-key}")
    private String accessKey;

//    @Value("${aliyun.oss.secret-key}")
    private String secretKey;

    /**
     * oss的储存名称
     */
//    @Value("${aliyun.oss.bucket}")
    private String bucket;

    /**
     * 将前端传入的 file转为 inputStream保存
     *
     * @param inputStream file转换后的 fileStream
     * @param suffix      文件的后缀
     * @return 前端所需的 json格式
     */
//    @Override
    public Map<String, Object> uploadFile(InputStream inputStream, String suffix) {
        // 生成随机的文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

        // 调用阿里云 oss把图片上传到 oss
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, secretKey);
        ossClient.putObject(bucket, fileName, inputStream);
        ossClient.shutdown();

        // 获取上传后的图片链接
        // 后端将地址拼接一下，oss那里设为了公共读，阿里云屁事太多了
        String picAddr = address + "/" + fileName;

        // 前端所必须的返回格式
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("url", picAddr);
            put("success", 1);
            put("message", "upload success!");
        }};
        return map;
    }
}
