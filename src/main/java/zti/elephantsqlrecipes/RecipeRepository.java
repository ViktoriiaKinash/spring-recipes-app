package zti.elephantsqlrecipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {
    List<Recipe> findByTitle(String title);
    List<Recipe> findAllById(Long id);
}


