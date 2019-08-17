package co.jratil.blog.service.impl;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 因为选择了上传到七牛云，所以直接上传到服务器也不用了，随时删除
 * TODO 直接上传文件到服务器，七牛云稳定后删除
 *
 * @create 2019-07-31 15:50
 */
public class UploadServiceImpl {

//    @Value("${file-upload.image-path}")
    private String imagePath;

//    @Override
    public Map<String, Object> uploadFile(File file) {
        return null;
    }

//    @Override
    public Map<String, Object> uploadFile(InputStream inputStream, String suffix) {
        return null;
    }

//    @Override
    public Map<String, Object> uploadImage(MultipartFile multipartFile) {

        String filename = multipartFile.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

        // 获取服务器某个根路径
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(imagePath);
        String path = factory.createMultipartConfig().getLocation();

        File file = new File(path + filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new HashMap<String, Object>(3) {{
            put("url", "");
            put("success", 1);
            put("message", "upload success!");
        }};
    }

//    @Override
    public Map<String, Object> delete(String key) {
        return null;
    }
}
