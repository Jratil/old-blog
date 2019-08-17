package co.jratil.blog.service;

import co.jratil.blog.pojo.vo.ResultVO;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @create 2019-07-30 11:17
 */
public interface FileUploadService {

    Map<String, Object> uploadFile(File file);

    Map<String, Object> uploadFile(InputStream inputStream);

    Map<String, Object> uploadImage(InputStream inputStream, String suffix);

    ResultVO delete(String key);
}
