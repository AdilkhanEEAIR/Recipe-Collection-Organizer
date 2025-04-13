import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
public class FileHandler {
    private static final String FILE_NAME = "recipes.json";

    // Метод для сохранения списка рецептов в JSON-файл
    public static void saveToFile(List<Recipe> recipes) {
        JSONArray array = new JSONArray();

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
            obj.put("favorite", r.isFavorite());
            obj.put("creationDate", r.getCreatedDate());
            obj.put("inPlan", r.isInPlan());
            array.add(obj);
        }

        // Запись JSON-массива в файл
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(array.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    // Метод для загрузки рецептов из JSON-файла
    public static List<Recipe> loadFromFile() {
        List<Recipe> recipes = new ArrayList<>();  // Список рецептов, который вернём
        try (FileReader reader = new FileReader(FILE_NAME)) {
            // Считываем содержимое файла
            JSONArray array = (JSONArray) new JSONParser().parse(reader);

            // Проходим по каждому объекту массива
            for (Object obj : array) {
                JSONObject o = (JSONObject) obj;

                // Создаём объект рецепта на основе данных JSON
                Recipe r = new Recipe(
                        ((Long) o.get("id")).intValue(),
                        (String) o.get("name"),
                        (String) o.get("description"),
                        (List<String>) o.get("ingredients"),
                        (List<String>) o.get("steps"),
                        (String) o.get("category"),
                        (String) o.get("cookingTime"),
                        (String) o.get("servingSize"),
                        (Boolean) o.get("favorite"),
                        (String) o.get("creationDate"),
                        (Boolean) o.get("inPlan")
                );
                recipes.add(r);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }

        return recipes;
    }
}