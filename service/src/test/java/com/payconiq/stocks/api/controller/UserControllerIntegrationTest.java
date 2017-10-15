package com.payconiq.stocks.api.controller;

import com.payconiq.stocks.api.ServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({ServiceApplication.class})
public class UserControllerIntegrationTest {

    @Autowired
    UserController userController;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_return_user() throws Exception {
        mockMvc.perform(get("/users/tester1")).andExpect(status().isOk())
                .andExpect(jsonPath("password").value("abcde"));
    }

    @Test
    public void should_throw_404_is_username_is_not_found() throws Exception {
        mockMvc.perform(get("/users/tester144")).andExpect(status().isNotFound());
    }


}