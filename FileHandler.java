import java.io.*;
import java.util.*;

public class FileHandler {

    public static void saveRecipesToCSV(List<Recipe> recipes, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Recipe r : recipes) {
                writer.println(r.getId() + ";" +
                        r.getName() + ";" +
                        r.getDescription() + ";" +
                        String.join("|", r.getIngredients()) + ";" +
                        String.join("|", r.getSteps()) + ";" +
                        r.getCategory() + ";" +
                        r.getCookingTime() + ";" +
                        r.getServingSize());
            }
            System.out.println("Сохранено в CSV-файл: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении в CSV: " + e.getMessage());
        }
    }

    public static List<Recipe> loadRecipesFromCSV(String filename) {
        List<Recipe> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 8) continue;

                String id = parts[0];
                String name = parts[1];
                String description = parts[2];
                List<String> ingredients = Arrays.asList(parts[3].split("\\|"));
                List<String> steps = Arrays.asList(parts[4].split("\\|"));
                String category = parts[5];
                String cookingTime = parts[6];
                String servingSize = parts[7];

                loaded.add(new Recipe(id, name, description, ingredients, steps, category, cookingTime, servingSize));
            }
            System.out.println("Загружено из CSV: " + loaded.size() + " рецептов");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении CSV: " + e.getMessage());
        }
        return loaded;
    }
}
