import java.util.*;

public class Recipe {
    private String id;
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private String category;
    private String cookingTime;
    private String servingSize;

    public Recipe(String id, String name, String description, List<String> ingredients, List<String> steps, String category, String cookingTime, String servingSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.category = category;
        this.cookingTime = cookingTime;
        this.servingSize = servingSize;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCookingTime() { return cookingTime; }
    public void setCookingTime(String cookingTime) { this.cookingTime = cookingTime; }

    public String getServingSize() { return servingSize; }
    public void setServingSize(String servingSize) { this.servingSize = servingSize; }
}
