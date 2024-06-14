package zti.elephantsqlrecipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Repository
public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> searchByKeyword(String keyword) {
        String sql = "SELECT * FROM recipes WHERE tsv @@ to_tsquery(:keyword)";
        Query query = entityManager.createNativeQuery(sql, Recipe.class);
        query.setParameter("keyword", keyword);
        return query.getResultList();
    }
}
