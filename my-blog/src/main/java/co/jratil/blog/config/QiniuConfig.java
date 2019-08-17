package co.jratil.blog.config;

import com.qiniu.common.Zone;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置类
 * @create 2019-07-31 16:47
 */
@Configuration
public class QiniuConfig {

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Bean
    public com.qiniu.storage.Configuration getQiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone2());
    }

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    
}
