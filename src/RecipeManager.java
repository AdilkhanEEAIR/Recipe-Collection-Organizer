import java.sql.*;
import java.util.*;

public class RecipeManager {
    private Connection conn;

    public RecipeManager(String dbFileName) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDatabase() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS recipes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "description TEXT," +
                    "ingredients TEXT," +
                    "steps TEXT," +
                    "category TEXT," +
                    "cookingTime TEXT," +
                    "servingSize TEXT," +
                    "isFavorite INTEGER," +
                    "createdDate TEXT," +
                    "inPlan INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecipe(Recipe recipe) {
        String sql = "INSERT INTO recipes (name, description, ingredients, steps, category, cookingTime, servingSize, isFavorite, createdDate, inPlan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, recipe.getName());
            pstmt.setString(2, recipe.getDescription());
            pstmt.setString(3, String.join(",", recipe.getIngredients()));
            pstmt.setString(4, String.join(",", recipe.getSteps()));
            pstmt.setString(5, recipe.getCategory());
            pstmt.setString(6, recipe.getCookingTime());
            pstmt.setString(7, recipe.getServingSize());
            pstmt.setInt(8, recipe.isFavorite() ? 1 : 0);
            pstmt.setString(9, recipe.getCreatedDate());
            pstmt.setInt(10, recipe.isInPlan() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                recipes.add(parseRecipe(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private Recipe parseRecipe(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        List<String> ingredients = Arrays.asList(rs.getString("ingredients").split(","));
        List<String> steps = Arrays.asList(rs.getString("steps").split(","));
        String category = rs.getString("category");
        String cookingTime = rs.getString("cookingTime");
        String servingSize = rs.getString("servingSize");
        boolean isFavorite = rs.getInt("isFavorite") == 1;
        String createdDate = rs.getString("createdDate");
        boolean inPlan = rs.getInt("inPlan") == 1;

        return new Recipe(id, name, description, ingredients, steps, category, cookingTime, servingSize, isFavorite, createdDate, inPlan);
    }

    public String recipeToString(Recipe recipe) {
        return "ID: " + recipe.getId() + "\n" +
                "Название: " + recipe.getName() + "\n" +
                "Описание: " + recipe.getDescription() + "\n" +
                "Ингредиенты: " + String.join(", ", recipe.getIngredients()) + "\n" +
                "Шаги: " + String.join(", ", recipe.getSteps()) + "\n" +
                "Категория: " + recipe.getCategory() + "\n" +
                "Время приготовления: " + recipe.getCookingTime() + "\n" +
                "Порции: " + recipe.getServingSize() + "\n" +
                "Избранное: " + (recipe.isFavorite() ? "Да" : "Нет") + "\n" +
                "Дата создания: " + recipe.getCreatedDate() + "\n" +
                "В плане: " + (recipe.isInPlan() ? "Да" : "Нет") + "\n";
    }

    public List<Recipe> searchByIngredient(String ingredient) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : getAllRecipes()) {
            for (String ing : recipe.getIngredients()) {
                if (ing.toLowerCase().contains(ingredient.toLowerCase())) {
                    result.add(recipe);
                    break;
                }
            }
        }
        return result;
    }

    public List<Recipe> filterByCategory(String category) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : getAllRecipes()) {
            if (recipe.getCategory().equalsIgnoreCase(category)) {
                result.add(recipe);
            }
        }
        return result;
    }

    public List<Recipe> sortByCookingTime() {
        List<Recipe> recipes = getAllRecipes();
        recipes.sort(new Comparator<Recipe>() {
            public int compare(Recipe r1, Recipe r2) {
                try {
                    return Integer.parseInt(r1.getCookingTime()) - Integer.parseInt(r2.getCookingTime());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });
        return recipes;
    }

    public List<Recipe> getFavorites() {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : getAllRecipes()) {
            if (recipe.isFavorite()) {
                result.add(recipe);
            }
        }
        return result;
    }

    public List<Recipe> getPlannedRecipes() {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : getAllRecipes()) {
            if (recipe.isInPlan()) {
                result.add(recipe);
            }
        }
        return result;
    }

    public List<Recipe> advancedSearch(String ingredient, String category, String time, String servings) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : getAllRecipes()) {
            boolean matchesIngredient = ingredient.isEmpty() || recipe.getIngredients().toString().toLowerCase().contains(ingredient.toLowerCase());
            boolean matchesCategory = category.isEmpty() || recipe.getCategory().equalsIgnoreCase(category);
            boolean matchesTime = time.isEmpty() || recipe.getCookingTime().equals(time);
            boolean matchesServings = servings.isEmpty() || recipe.getServingSize().equals(servings);

            if (matchesIngredient && matchesCategory && matchesTime && matchesServings) {
                result.add(recipe);
            }
        }
        return result;
    }
}
