import java.util.*;
import java.io.*;

public class RecipeManager {
    private List<Recipe> recipes;

    public RecipeManager() {
        recipes = new ArrayList<Recipe>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public Recipe getRecipeById(int id) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == id) {
                return recipes.get(i);
            }
        }
        return null;
    }

    public boolean deleteRecipeById(int id) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == id) {
                recipes.remove(i);
                return true;
            }
        }
        return false;
    }

    public void updateRecipe(int id, Recipe updatedRecipe) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == id) {
                recipes.set(i, updatedRecipe);
                return;
            }
        }
    }

    public int generateId() {
        int maxId = 0;
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() > maxId) {
                maxId = recipes.get(i).getId();
            }
        }
        return maxId + 1;
    }

    public List<Recipe> searchByIngredient(String ingredient) {
        List<Recipe> results = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe r = recipes.get(i);
            if (r.getIngredients().contains(ingredient)) {
                results.add(r);
            }
        }
        return results;
    }

    public List<Recipe> filterByCategory(String category) {
        List<Recipe> results = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getCategory().equalsIgnoreCase(category)) {
                results.add(recipes.get(i));
            }
        }
        return results;
    }

    public List<Recipe> sortByCookingTime() {
        List<Recipe> sorted = new ArrayList<Recipe>(recipes);
        Collections.sort(sorted, new Comparator<Recipe>() {
            public int compare(Recipe r1, Recipe r2) {
                return r1.getCookingTime().compareTo(r2.getCookingTime());
            }
        });
        return sorted;
    }

    public List<Recipe> getFavorites() {
        List<Recipe> favorites = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).isFavorite()) {
                favorites.add(recipes.get(i));
            }
        }
        return favorites;
    }

    public List<Recipe> advancedSearch(List<String> ingredients, String category, String time, String servings) {
        List<Recipe> results = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe r = recipes.get(i);
            if (r.getIngredients().containsAll(ingredients)
                    && r.getCategory().equalsIgnoreCase(category)
                    && r.getCookingTime().equalsIgnoreCase(time)
                    && r.getServingSize().equalsIgnoreCase(servings)) {
                results.add(r);
            }
        }
        return results;
    }

    public List<Recipe> getPlannedRecipes() {
        List<Recipe> planned = new ArrayList<Recipe>();
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).isInPlan()) {
                planned.add(recipes.get(i));
            }
        }
        return planned;
    }

    public String getCurrentDate() {
        Date date = new Date();
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String recipeToString(Recipe r) {
        return "ID: " + r.getId() + "\nНазвание: " + r.getName() +
                "\nОписание: " + r.getDescription() +
                "\nИнгредиенты: " + r.getIngredients() +
                "\nШаги: " + r.getSteps() +
                "\nКатегория: " + r.getCategory() +
                "\nВремя приготовления: " + r.getCookingTime() +
                "\nПорции: " + r.getServingSize() +
                "\nИзбранное: " + (r.isFavorite() ? "Да" : "Нет") +
                "\nДата создания: " + r.getCreatedDate() +
                "\nВ плане: " + (r.isInPlan() ? "Да" : "Нет") + "\n";
    }
}
