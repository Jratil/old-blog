package co.jratil.blog.service.impl;

import co.jratil.blog.service.UserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRoleServiceImplTest {

    @Autowired
    UserRoleService userRoleService;

    @Test
    public void findRole() {
        System.out.println(userRoleService.findRole("15626680"));
    }

    @Test
    public void deleteRole() {
    }

    @Test
    public void updateRole() {
    }

    @Test
    public void addRole() {
    }
}