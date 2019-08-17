package co.jratil.blog.controller;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthorControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void register() throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("name", "aa");
            put("account", "11");
            put("password", "aa");
            put("verifyCode", "123");
        }};

        MvcResult result = mockMvc.perform(post("/author/register").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(hashMap)))
                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void login() throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("account", "123321");
            put("password", "123123");
        }};

        MvcResult result = mockMvc.perform(post("/author/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("account", "123321")
                .param("password", "123123")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
    }
}