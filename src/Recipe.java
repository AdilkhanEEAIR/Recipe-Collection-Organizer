import java.util.*;

public class Recipe {
    private int id;
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private String category;
    private String cookingTime;
    private String servingSize;
    private boolean isFavorite;
    private String createdDate;
    private boolean inPlan;

    public Recipe(int id, String name, String description, List<String> ingredients, List<String> steps,
                  String category, String cookingTime, String servingSize, boolean isFavorite,
                  String createdDate, boolean inPlan) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.category = category;
        this.cookingTime = cookingTime;
        this.servingSize = servingSize;
        this.isFavorite = isFavorite;
        this.createdDate = createdDate;
        this.inPlan = inPlan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

    public boolean isInPlan() { return inPlan; }
    public void setInPlan(boolean inPlan) { this.inPlan = inPlan; }
}