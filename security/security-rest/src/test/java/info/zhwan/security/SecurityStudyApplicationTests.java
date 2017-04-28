package info.zhwan.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityStudyApplicationTests {

  @Autowired
  WebApplicationContext wac;

  MockMvc mvc;

  @Before
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(wac)
        .apply(springSecurity())
        .build();
  }

  @Test
  public void login() throws Exception {
    mvc.perform(
        post("/login")
            .param("username", "user")
            .param("password", "user")
    )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void loginFail() throws Exception {
    mvc.perform(
        post("/login")
            .param("username", "user")
            .param("password", "****")
    )
        .andDo(print())
        .andExpect(status().isUnauthorized())
    ;
  }

  @Test
//  @WithMockUser(username = "user", password = "user", roles = {"USER"}) // 이것은 버그가 있는거 같다.
  @WithUserDetails("user")
  public void getUser() throws Exception {

    mvc.perform(
        get("/api/user")
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("hello user index"))
    ;
  }
}
