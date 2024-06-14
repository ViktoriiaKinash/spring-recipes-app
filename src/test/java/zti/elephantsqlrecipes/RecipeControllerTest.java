package zti.elephantsqlrecipes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import zti.elephantsqlrecipes.Recipe;
import zti.elephantsqlrecipes.RecipeController;
import zti.elephantsqlrecipes.RecipeService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @Test
    public void testCreateRecipe() {
        // Mock recipe
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Test Recipe");

        // Mock service response
        when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(recipe);

        // Perform request
        Recipe createdRecipe = recipeController.createRecipe(recipe);

        // Verify response
        assertEquals(recipe.getId(), createdRecipe.getId());
        assertEquals(recipe.getTitle(), createdRecipe.getTitle());
    }

    @Test
    public void testDeleteRecipe() {
        // Mock recipe ID
        Long recipeId = 1L;

        // Perform request
        ResponseEntity<Void> responseEntity = recipeController.deleteRecipe(recipeId);

        // Verify service method called
        verify(recipeService, times(1)).deleteRecipe(recipeId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    // Add more test methods to cover other endpoints and edge cases as needed
}

