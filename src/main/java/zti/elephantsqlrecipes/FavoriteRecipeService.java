package zti.elephantsqlrecipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FavoriteRecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final FavoriteRecipeRepository favoriteRecipeRepository;

    public FavoriteRecipeService(UserRepository userRepository, RecipeRepository recipeRepository, FavoriteRecipeRepository favoriteRecipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }

    public void addRecipeToFavorites(String username, Long recipeId) {
        User user = userRepository.findByUsername(username);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));;

        FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
        favoriteRecipe.setUserId(user.getId());
        favoriteRecipe.setRecipeId(recipe.getId());

        favoriteRecipeRepository.save(favoriteRecipe);
    }
}