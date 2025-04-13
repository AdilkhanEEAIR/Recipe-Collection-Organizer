import javax.swing.*;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class RecipeManager {
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipeViaDialog() {
        String name = JOptionPane.showInputDialog("Введите название рецепта:");
        String description = JOptionPane.showInputDialog("Введите описание:");
        String ingredientsStr = JOptionPane.showInputDialog("Введите ингредиенты (через запятую):");
        String stepsStr = JOptionPane.showInputDialog("Введите шаги (через запятую):");
        String category = JOptionPane.showInputDialog("Введите категорию:");
        String cookingTime = JOptionPane.showInputDialog("Введите время приготовления:");
        String servingSize = JOptionPane.showInputDialog("Введите размер порции:");

        List<String> ingredients = Arrays.asList(ingredientsStr.split(","));
        List<String> steps = Arrays.asList(stepsStr.split(","));

        String id = String.valueOf(recipes.size() + 1);

        Recipe recipe = new Recipe(id, name, description, ingredients, steps, category, cookingTime, servingSize);
        recipes.add(recipe);
        JOptionPane.showMessageDialog(null, "Рецепт добавлен!");
    }

    public void displayAllRecipes() {
        if (recipes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Нет доступных рецептов.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Recipe r : recipes) {
            sb.append("ID: ").append(r.getId()).append(", Название: ").append(r.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void viewRecipeById(String id) {
        for (Recipe r : recipes) {
            if (r.getId().equals(id)) {
                JOptionPane.showMessageDialog(null,
                        "Название: " + r.getName() +
                                "\nОписание: " + r.getDescription() +
                                "\nИнгредиенты: " + String.join(", ", r.getIngredients()) +
                                "\nШаги: " + String.join(", ", r.getSteps()) +
                                "\nКатегория: " + r.getCategory() +
                                "\nВремя приготовления: " + r.getCookingTime() +
                                "\nРазмер порции: " + r.getServingSize());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Рецепт с ID " + id + " не найден.");
    }

    public void updateRecipeViaDialog(String id) {
        for (Recipe r : recipes) {
            if (r.getId().equals(id)) {
                String name = JOptionPane.showInputDialog("Новое название:", r.getName());
                String description = JOptionPane.showInputDialog("Новое описание:", r.getDescription());
                String ingredientsStr = JOptionPane.showInputDialog("Новые ингредиенты (через запятую):", String.join(",", r.getIngredients()));
                String stepsStr = JOptionPane.showInputDialog("Новые шаги (через запятую):", String.join(",", r.getSteps()));
                String category = JOptionPane.showInputDialog("Новая категория:", r.getCategory());
                String cookingTime = JOptionPane.showInputDialog("Новое время приготовления:", r.getCookingTime());
                String servingSize = JOptionPane.showInputDialog("Новый размер порции:", r.getServingSize());

                r.setName(name);
                r.setDescription(description);
                r.setIngredients(Arrays.asList(ingredientsStr.split(",")));
                r.setSteps(Arrays.asList(stepsStr.split(",")));
                r.setCategory(category);
                r.setCookingTime(cookingTime);
                r.setServingSize(servingSize);

                JOptionPane.showMessageDialog(null, "Рецепт обновлён!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Рецепт с ID " + id + " не найден.");
    }

    public void deleteRecipe(String id) {
        Iterator<Recipe> it = recipes.iterator();
        while (it.hasNext()) {
            Recipe r = it.next();
            if (r.getId().equals(id)) {
                it.remove();
                JOptionPane.showMessageDialog(null, "Рецепт удалён!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Рецепт с ID " + id + " не найден.");
    }

    public void exportToJson() {
        JSONArray jsonList = new JSONArray();

        for (Recipe r : recipes) {
            JSONObject obj = new JSONObject();
            obj.put("id", r.getId());
            obj.put("name", r.getName());
            obj.put("description", r.getDescription());
            obj.put("ingredients", r.getIngredients());
            obj.put("steps", r.getSteps());
            obj.put("category", r.getCategory());
            obj.put("cookingTime", r.getCookingTime());
            obj.put("servingSize", r.getServingSize());
            jsonList.add(obj);
        }

        try (FileWriter file = new FileWriter("recipes.json")) {
            file.write(jsonList.toJSONString());
            file.flush();
            JOptionPane.showMessageDialog(null, "Экспорт в JSON выполнен!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при экспорте: " + e.getMessage());
        }
    }

    public void importFromJson() {
        try (FileReader reader = new FileReader("recipes.json")) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            recipes.clear();

            for (Object obj : jsonArray) {
                JSONObject json = (JSONObject) obj;

                String id = (String) json.get("id");
                String name = (String) json.get("name");
                String description = (String) json.get("description");

                List<String> ingredients = (List<String>) json.get("ingredients");
                List<String> steps = (List<String>) json.get("steps");

                String category = (String) json.get("category");
                String cookingTime = (String) json.get("cookingTime");
                String servingSize = (String) json.get("servingSize");

                Recipe recipe = new Recipe(id, name, description, ingredients, steps, category, cookingTime, servingSize);
                recipes.add(recipe);
            }

            JOptionPane.showMessageDialog(null, "Импорт из JSON выполнен!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка при импорте: " + e.getMessage());
        }
    }
}