package co.jratil.blog.mapper;

import co.jratil.blog.constant.RedisConstant;
import co.jratil.blog.utils.IdGenerateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorMapperTest {

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    IdGenerateUtil idGenerate;

    @Test
    public void select() {
        System.out.println(authorMapper.findAuthorById(""));
    }

    @Test
    public void test() {

        System.out.println(idGenerate.generateId(RedisConstant.AUTHOR_ID));
        System.out.println(idGenerate.generateId(RedisConstant.CATEGORY_ID));
//        Set<String> idSet = new HashSet<>();
//        for (int i = 0; i < 10000; i++) {
//            idSet.add(idGenerate.generateAuthorId());
//        }
//        System.out.println(idSet.size());
    }
}