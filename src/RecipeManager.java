import java.sql.*;
import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
public class RecipeManager {
    private String dbPath;

    public RecipeManager(String dbPath) {
        this.dbPath = dbPath;
        createTableIfNotExists();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    private void createTableIfNotExists() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS recipes (" +
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
                    "inPlan INTEGER)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecipe(Recipe recipe) {
        String sql = "INSERT INTO recipes(name, description, ingredients, steps, category, cookingTime, servingSize, isFavorite, createdDate, inPlan) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

            exportToJson("recipes.json");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM recipes")) {
            while (rs.next()) {
                list.add(extractRecipeFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Recipe> searchByIngredient(String ingredient) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : getAllRecipes()) {
            for (String ing : r.getIngredients()) {
                if (ing.trim().equalsIgnoreCase(ingredient.trim())) {
                    result.add(r);
                    break;
                }
            }
        }
        return result;
    }

    public List<Recipe> filterByCategory(String category) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : getAllRecipes()) {
            if (r.getCategory().equalsIgnoreCase(category)) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Recipe> sortByCookingTime() {
        List<Recipe> sorted = new ArrayList<>(getAllRecipes());
        sorted.sort(Comparator.comparing(Recipe::getCookingTime));
        return sorted;
    }

    public List<Recipe> getFavorites() {
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : getAllRecipes()) {
            if (r.isFavorite()) result.add(r);
        }
        return result;
    }

    public List<Recipe> getPlannedRecipes() {
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : getAllRecipes()) {
            if (r.isInPlan()) result.add(r);
        }
        return result;
    }

    public List<Recipe> advancedSearch(String ingredient, String category, String time, String serving) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : getAllRecipes()) {
            boolean match = true;
            if (!ingredient.isEmpty() && r.getIngredients().stream().noneMatch(i -> i.contains(ingredient))) match = false;
            if (!category.isEmpty() && !r.getCategory().equalsIgnoreCase(category)) match = false;
            if (!time.isEmpty() && !r.getCookingTime().equalsIgnoreCase(time)) match = false;
            if (!serving.isEmpty() && !r.getServingSize().equalsIgnoreCase(serving)) match = false;
            if (match) result.add(r);
        }
        return result;
    }

    public String recipeToString(Recipe r) {
        return "ID: " + r.getId() + "\nНазвание: " + r.getName() + "\nОписание: " + r.getDescription() +
                "\nИнгредиенты: " + String.join(", ", r.getIngredients()) +
                "\nШаги: " + String.join(", ", r.getSteps()) +
                "\nКатегория: " + r.getCategory() +
                "\nВремя приготовления: " + r.getCookingTime() +
                "\nПорции: " + r.getServingSize() +
                "\nИзбранное: " + (r.isFavorite() ? "Да" : "Нет") +
                "\nДата создания: " + r.getCreatedDate() +
                "\nВ плане: " + (r.isInPlan() ? "Да" : "Нет");
    }

    public void deleteRecipeById(int id) {
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement("DELETE FROM recipes WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void importFromJson(String path) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader(path));
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;

                List<String> ingredients = new ArrayList<>();
                List<String> steps = new ArrayList<>();

                if (obj.get("ingredients") instanceof JSONArray) {
                    for (Object ing : (JSONArray) obj.get("ingredients")) {
                        ingredients.add((String) ing);
                    }
                } else {
                    ingredients = Arrays.asList(((String) obj.get("ingredients")).split(","));
                }

                if (obj.get("steps") instanceof JSONArray) {
                    for (Object step : (JSONArray) obj.get("steps")) {
                        steps.add((String) step);
                    }
                } else {
                    steps = Arrays.asList(((String) obj.get("steps")).split(","));
                }

                Recipe r = new Recipe(
                        0,
                        (String) obj.get("name"),
                        (String) obj.get("description"),
                        ingredients,
                        steps,
                        (String) obj.get("category"),
                        (String) obj.get("cookingTime"),
                        (String) obj.get("servingSize"),
                        Boolean.parseBoolean(obj.get("isFavorite").toString()),
                        (String) obj.get("createdDate"),
                        Boolean.parseBoolean(obj.get("inPlan").toString())
                );
                addRecipe(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportToJson(String path) {
        JSONArray arr = new JSONArray();
        for (Recipe r : getAllRecipes()) {
            JSONObject obj = new JSONObject();
            obj.put("name", r.getName());
            obj.put("description", r.getDescription());

            JSONArray ingredientsArray = new JSONArray();
            for (String ing : r.getIngredients()) {
                ingredientsArray.add(ing);
            }
            obj.put("ingredients", ingredientsArray);

            JSONArray stepsArray = new JSONArray();
            for (String step : r.getSteps()) {
                stepsArray.add(step);
            }
            obj.put("steps", stepsArray);

            obj.put("category", r.getCategory());
            obj.put("cookingTime", r.getCookingTime());
            obj.put("servingSize", r.getServingSize());
            obj.put("isFavorite", r.isFavorite());
            obj.put("createdDate", r.getCreatedDate());
            obj.put("inPlan", r.isInPlan());

            arr.add(obj);
        }
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(arr.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Recipe extractRecipeFromResultSet(ResultSet rs) throws SQLException {
        return new Recipe(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                Arrays.asList(rs.getString("ingredients").split(",")),
                Arrays.asList(rs.getString("steps").split(",")),
                rs.getString("category"),
                rs.getString("cookingTime"),
                rs.getString("servingSize"),
                rs.getInt("isFavorite") == 1,
                rs.getString("createdDate"),
                rs.getInt("inPlan") == 1
        );
    }
}