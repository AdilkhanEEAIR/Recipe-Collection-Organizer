import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main {
    private static RecipeManager manager;

    public static void main(String[] args) {
        manager = new RecipeManager("recipes.db");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Recipe Collection Organizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 1, 10, 10));

            JButton addButton = new JButton("Добавить рецепт");
            JButton viewButton = new JButton("Просмотреть все рецепты");
            JButton searchButton = new JButton("Поиск по ингредиенту");
            JButton filterButton = new JButton("Фильтрация по категории");
            JButton sortButton = new JButton("Сортировка по времени");
            JButton favoritesButton = new JButton("Избранные рецепты");
            JButton planButton = new JButton("Запланированные рецепты");
            JButton advancedSearchButton = new JButton("Расширенный поиск");
            JButton exitButton = new JButton("Выйти");

            panel.add(addButton);
            panel.add(viewButton);
            panel.add(searchButton);
            panel.add(filterButton);
            panel.add(sortButton);
            panel.add(favoritesButton);
            panel.add(planButton);
            panel.add(advancedSearchButton);
            panel.add(exitButton);

            frame.add(panel);
            frame.setVisible(true);

            addButton.addActionListener(e -> showAddRecipeDialog());
            viewButton.addActionListener(e -> showRecipeList(manager.getAllRecipes()));
            searchButton.addActionListener(e -> {
                String ingredient = JOptionPane.showInputDialog("Введите ингредиент:");
                if (ingredient != null) {
                    showRecipeList(manager.searchByIngredient(ingredient));
                }
            });
            filterButton.addActionListener(e -> {
                String category = JOptionPane.showInputDialog("Введите категорию:");
                if (category != null) {
                    showRecipeList(manager.filterByCategory(category));
                }
            });
            sortButton.addActionListener(e -> showRecipeList(manager.sortByCookingTime()));
            favoritesButton.addActionListener(e -> showRecipeList(manager.getFavorites()));
            planButton.addActionListener(e -> showRecipeList(manager.getPlannedRecipes()));
            advancedSearchButton.addActionListener(e -> showAdvancedSearchDialog());
            exitButton.addActionListener(e -> System.exit(0));
        });
    }

    private static void showAddRecipeDialog() {
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField ingredientsField = new JTextField();
        JTextField stepsField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField cookingTimeField = new JTextField();
        JTextField servingSizeField = new JTextField();
        JCheckBox favoriteBox = new JCheckBox("Избранное");
        JCheckBox planBox = new JCheckBox("В план");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Название:")); panel.add(nameField);
        panel.add(new JLabel("Описание:")); panel.add(descriptionField);
        panel.add(new JLabel("Ингредиенты (через запятую):")); panel.add(ingredientsField);
        panel.add(new JLabel("Шаги (через запятую):")); panel.add(stepsField);
        panel.add(new JLabel("Категория:")); panel.add(categoryField);
        panel.add(new JLabel("Время приготовления:")); panel.add(cookingTimeField);
        panel.add(new JLabel("Порции:")); panel.add(servingSizeField);
        panel.add(favoriteBox);
        panel.add(planBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Добавить рецепт",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Recipe recipe = new Recipe(
                    0,
                    nameField.getText(),
                    descriptionField.getText(),
                    List.of(ingredientsField.getText().split(",")),
                    List.of(stepsField.getText().split(",")),
                    categoryField.getText(),
                    cookingTimeField.getText(),
                    servingSizeField.getText(),
                    favoriteBox.isSelected(),
                    getCurrentDate(),
                    planBox.isSelected()
            );
            manager.addRecipe(recipe);
            JOptionPane.showMessageDialog(null, "Рецепт добавлен.");
        }
    }

    private static void showRecipeList(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Рецепты не найдены.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Recipe r : recipes) {
            sb.append(manager.recipeToString(r)).append("\n-------------------\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setPreferredSize(new Dimension(480, 350));
        JOptionPane.showMessageDialog(null, scrollPane, "Список рецептов", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showAdvancedSearchDialog() {
        JTextField ingredientField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField servingField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Ингредиент:")); panel.add(ingredientField);
        panel.add(new JLabel("Категория:")); panel.add(categoryField);
        panel.add(new JLabel("Время приготовления:")); panel.add(timeField);
        panel.add(new JLabel("Порции:")); panel.add(servingField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Расширенный поиск",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<Recipe> found = manager.advancedSearch(
                    ingredientField.getText(),
                    categoryField.getText(),
                    timeField.getText(),
                    servingField.getText()
            );
            showRecipeList(found);
        }
    }
    private static String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
}