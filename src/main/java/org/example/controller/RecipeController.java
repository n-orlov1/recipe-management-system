package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.example.adapter.UserAdapter;
import org.example.entity.Recipe;
import org.example.service.RecipeService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final ObjectMapper mapper;
    @PostMapping("/api/recipe/new")
    public ResponseEntity<ObjectNode> createRecipe(@AuthenticationPrincipal UserAdapter userAdapter, @RequestBody Recipe recipe) {
        if(!recipe.validate()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Recipe savedRecipe = recipeService.createRecipe(userAdapter.getUser(), recipe);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", recipe.getId());

        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe retrievedRecipe = recipeService.findRecipe(id);

        return new ResponseEntity<>(retrievedRecipe,HttpStatus.OK);
    }
    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipe(@AuthenticationPrincipal UserAdapter userAdapter, @PathVariable int id) {
        recipeService.deleteRecipe(userAdapter.getUser(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/api/recipe/{id}")
    public ResponseEntity updateRecipe(@AuthenticationPrincipal UserAdapter userAdapter, @PathVariable int id, @RequestBody Recipe recipe) {

        if(!recipe.validate()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        recipeService.updateRecipe(userAdapter.getUser(), id, recipe);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/api/recipe/search")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam Map<String, String> params) {

        if(params.isEmpty() || params.entrySet().size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Recipe> foundRecipes;

        if (params.containsKey("category")) {
            foundRecipes = recipeService.searchRecipeByCategory(params.get("category"));
        } else if (params.containsKey("name")) {
            foundRecipes = recipeService.searchRecipeByName(params.get("name"));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(foundRecipes.size() == 0) {
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }

        return new ResponseEntity<>(foundRecipes, HttpStatus.OK);
    }
}
