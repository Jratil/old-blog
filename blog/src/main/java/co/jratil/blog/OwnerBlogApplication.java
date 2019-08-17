package co.jratil.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(value = "co.jratil.blog.mapper")
@EnableCaching
public class OwnerBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnerBlogApplication.class, args);
    }

}
