package zti.elephantsqlrecipes;
import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> searchByKeyword(String keyword);
}
