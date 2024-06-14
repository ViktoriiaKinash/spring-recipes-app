package zti.elephantsqlrecipes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ElephantsqlRecipesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFalse() {
        assertFalse(false);
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testGetUserProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAddRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/recipes")
                        .content("{\"title\": \"Test Recipe\", \"photo\": \"test.jpg\", \"ingredients\": \"test ingredients\", \"description\": \"test description\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testUpdateRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/1")
                        .content("{\"title\": \"Updated Recipe\", \"photo\": \"updated.jpg\", \"ingredients\": \"updated ingredients\", \"description\": \"updated description\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testRemoveRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testGetRecipeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
