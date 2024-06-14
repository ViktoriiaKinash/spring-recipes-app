package zti.elephantsqlrecipes;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final FavoriteRecipeService favoriteRecipeService;

    public UserController(FavoriteRecipeService favoriteRecipeService) {
        this.favoriteRecipeService = favoriteRecipeService;
    }

    @PostMapping("/favorites/add")
    public ResponseEntity<String> addRecipeToFavorites(@RequestBody AddFavoriteRequest addFavoriteRequest, Authentication authentication) {
        String username = authentication.getName();
        Long recipeId = addFavoriteRequest.getRecipeId();

        favoriteRecipeService.addRecipeToFavorites(username, recipeId);

        return ResponseEntity.ok("Recipe added to favorites.");
    }

    @Autowired
    private FavoriteRecipeRepository favoriteRecipeRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/favorite")
    public ResponseEntity<String> addRecipeToFavorites(@RequestBody RecipeIdRequest recipeIdRequest, Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        Long recipeId = recipeIdRequest.getRecipeId();

        FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
        favoriteRecipe.setUserId(userId);
        favoriteRecipe.setRecipeId(recipeId);

        favoriteRecipeRepository.save(favoriteRecipe);
        return ResponseEntity.ok("Recipe added to favorites");
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<Recipe>> getFavoriteRecipes(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeRepository.findByUserId(userId);

        List<Long> recipeIds = favoriteRecipes.stream()
                .map(FavoriteRecipe::getRecipeId)
                .collect(Collectors.toList());

        List<Recipe> recipes = recipeRepository.findAllById(recipeIds);
        return ResponseEntity.ok(recipes);
    }

    Long getUserIdFromAuthentication(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    @Transactional
    @DeleteMapping("/favorite/{recipeId}")
    public ResponseEntity<Long> removeRecipeFromFavorites(@PathVariable Long recipeId, Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);

        favoriteRecipeRepository.deleteByUserIdAndRecipeId(userId, recipeId);
        return ResponseEntity.ok(recipeId);
    }

}