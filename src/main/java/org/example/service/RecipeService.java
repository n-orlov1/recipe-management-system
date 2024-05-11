package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.repository.RecipeRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Recipe createRecipe(User user, Recipe recipe) {

        recipe.setUser(user);

        return recipeRepository.save(recipe);
    }

    public Recipe findRecipe(int id) {

        Optional<Recipe> retrievedRecipe = recipeRepository.findById(id);

        if(retrievedRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return retrievedRecipe.get();
    }

    public void deleteRecipe(User user, int id) {

        Optional<Recipe> retrievedRecipe = recipeRepository.findById(id);

        if(retrievedRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User recipeCreator = retrievedRecipe.get().getUser();

        if(!recipeCreator.getEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recipeRepository.deleteById(id);
    }

    public void updateRecipe(User user, int id, Recipe recipe) {

        Optional<Recipe> retrievedRecipe = recipeRepository.findById(id);

        if(retrievedRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Recipe updatedRecipe = retrievedRecipe.get();

        User recipeCreator = retrievedRecipe.get().getUser();

        if(!recipeCreator.getEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        updatedRecipe.setCategory(recipe.getCategory());
        updatedRecipe.setName(recipe.getName());
        updatedRecipe.setDirections(recipe.getDirections());
        updatedRecipe.setDescription(recipe.getDescription());
        updatedRecipe.setIngredients(recipe.getIngredients());
        updatedRecipe.setUser(user);

        recipeRepository.save(updatedRecipe);
    }

    public List<Recipe> searchRecipeByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchRecipeByName(String name) {
        return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
    }
}
