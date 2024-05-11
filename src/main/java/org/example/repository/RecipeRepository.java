package org.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.example.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name);
}
