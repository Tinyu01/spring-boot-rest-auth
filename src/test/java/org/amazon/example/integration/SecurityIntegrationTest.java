import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testAccessToProtectedResource() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new SecurityConfig()).build();
        mockMvc.perform(get("/protected-resource"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessToPublicResource() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new SecurityConfig()).build();
        mockMvc.perform(get("/public-resource"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessToAdminResource() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new SecurityConfig()).build();
        mockMvc.perform(get("/admin-resource"))
                .andExpect(status().isForbidden());
    }
}