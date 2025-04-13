import org.json.simple.*;
import org.json.simple.parser.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class RecipeManager {
    private List<Recipe> recipes = new ArrayList<>();
    private int idCounter = 1;

    public void addRecipeViaDialog() {
        String name = JOptionPane.showInputDialog("Название:");
        String description = JOptionPane.showInputDialog("Описание:");
        String ingredientsStr = JOptionPane.showInputDialog("Ингредиенты (через запятую):");
        String stepsStr = JOptionPane.showInputDialog("Шаги (через запятую):");
        String category = JOptionPane.showInputDialog("Категория:");
        String time = JOptionPane.showInputDialog("Время приготовления:");
        String size = JOptionPane.showInputDialog("Порции:");

        Recipe recipe = new Recipe(
                String.valueOf(idCounter++),
                name,
                description,
                Arrays.asList(ingredientsStr.split(",")),
                Arrays.asList(stepsStr.split(",")),
                category,
                time,
                size
        );
        recipes.add(recipe);
        JOptionPane.showMessageDialog(null, "Рецепт добавлен!");
    }

    public void displayAllRecipes() {
        if (recipes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Список рецептов пуст.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Recipe r : recipes) {
            sb.append("ID: ").append(r.getId()).append(" | ").append(r.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void viewRecipeById(String id) {
        for (Recipe r : recipes) {
            if (r.getId().equals(id)) {
                JOptionPane.showMessageDialog(null, getRecipeDetails(r));
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Рецепт не найден.");
    }

    public void updateRecipeViaDialog(String id) {
        for (Recipe r : recipes) {
            if (r.getId().equals(id)) {
                r.setName(JOptionPane.showInputDialog("Новое название:", r.getName()));
                r.setDescription(JOptionPane.showInputDialog("Новое описание:", r.getDescription()));
                r.setIngredients(Arrays.asList(JOptionPane.showInputDialog("Ингредиенты (через запятую):", String.join(",", r.getIngredients())).split(",")));
                r.setSteps(Arrays.asList(JOptionPane.showInputDialog("Шаги (через запятую):", String.join(",", r.getSteps())).split(",")));
                r.setCategory(JOptionPane.showInputDialog("Категория:", r.getCategory()));
                r.setCookingTime(JOptionPane.showInputDialog("Время приготовления:", r.getCookingTime()));
                r.setServingSize(JOptionPane.showInputDialog("Порции:", r.getServingSize()));
                JOptionPane.showMessageDialog(null, "Рецепт обновлён!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Рецепт не найден.");
    }

    public void deleteRecipe(String id) {
        Iterator<Recipe> iterator = recipes.iterator();
        while (iterator.hasNext()) {
            Recipe r = iterator.next();
            if (r.getId().equals(id)) {
                iterator.remove();
                JOptionPane.showMessageDialog(null, "Рецепт удалён.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Рецепт не найден.");
    }

    public void editOrDeleteByCategory(String category) {
        for (Recipe r : recipes) {
            if (r.getCategory().equalsIgnoreCase(category)) {
                int option = JOptionPane.showConfirmDialog(null, getRecipeDetails(r) + "\nРедактировать этот рецепт?", "Выбор", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    updateRecipeViaDialog(r.getId());
                    return;
                }
                option = JOptionPane.showConfirmDialog(null, "Удалить этот рецепт?", "Удаление", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    deleteRecipe(r.getId());
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Рецептов с этой категорией не найдено.");
    }

    public void editOrDeleteByIngredient(String ingredient) {
        for (Recipe r : recipes) {
            for (String ing : r.getIngredients()) {
                if (ing.trim().equalsIgnoreCase(ingredient)) {
                    int option = JOptionPane.showConfirmDialog(null, getRecipeDetails(r) + "\nРедактировать этот рецепт?", "Выбор", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        updateRecipeViaDialog(r.getId());
                        return;
                    }
                    option = JOptionPane.showConfirmDialog(null, "Удалить этот рецепт?", "Удаление", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        deleteRecipe(r.getId());
                        return;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Рецептов с таким ингредиентом не найдено.");
    }

    public void exportToJson() {
        JSONArray arr = new JSONArray();
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
            arr.add(obj);
        }
        try (FileWriter file = new FileWriter("recipes.json")) {
            file.write(arr.toJSONString());
            JOptionPane.showMessageDialog(null, "Экспорт завершён.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка при экспорте.");
        }
    }

    public void importFromJson() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader("recipes.json"));
            recipes.clear();
            idCounter = 1;
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                Recipe r = new Recipe(
                        obj.get("id").toString(),
                        (String) obj.get("name"),
                        (String) obj.get("description"),
                        (List<String>) obj.get("ingredients"),
                        (List<String>) obj.get("steps"),
                        (String) obj.get("category"),
                        (String) obj.get("cookingTime"),
                        (String) obj.get("servingSize")
                );
                recipes.add(r);
                int currentId = Integer.parseInt(r.getId());
                if (currentId >= idCounter) idCounter = currentId + 1;
            }
            JOptionPane.showMessageDialog(null, "Импорт завершён.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка при импорте.");
        }
    }

    private String getRecipeDetails(Recipe r) {
        return "ID: " + r.getId() + "\nНазвание: " + r.getName() + "\nОписание: " + r.getDescription() +
                "\nИнгредиенты: " + r.getIngredients() + "\nШаги: " + r.getSteps() +
                "\nКатегория: " + r.getCategory() + "\nВремя: " + r.getCookingTime() +
                "\nПорции: " + r.getServingSize();
    }
}
