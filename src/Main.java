import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();

        JFrame frame = new JFrame("Органайзер рецептов");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton addButton = new JButton("Добавить рецепт");
        JButton viewButton = new JButton("Показать все рецепты");
        JButton searchButton = new JButton("Поиск по ингредиенту");
        JButton filterButton = new JButton("Фильтр по категории");
        JButton sortButton = new JButton("Сортировка по времени");
        JButton favoritesButton = new JButton("Избранные рецепты");
        JButton advancedSearchButton = new JButton("Расширенный поиск");
        JButton planButton = new JButton("План на готовку");
        JButton exitButton = new JButton("Выйти");

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(filterButton);
        panel.add(sortButton);
        panel.add(favoritesButton);
        panel.add(advancedSearchButton);
        panel.add(planButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);

        // Добавление рецепта
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Введите название рецепта:");
                String description = JOptionPane.showInputDialog("Введите описание:");
                String ingredients = JOptionPane.showInputDialog("Введите ингредиенты (через запятую):");
                String steps = JOptionPane.showInputDialog("Введите шаги (через запятую):");
                String category = JOptionPane.showInputDialog("Введите категорию:");
                String time = JOptionPane.showInputDialog("Введите время приготовления:");
                String servings = JOptionPane.showInputDialog("Введите количество порций:");

                int id = manager.generateId();
                List<String> ingList = Arrays.asList(ingredients.split(","));
                List<String> stepList = Arrays.asList(steps.split(","));

                Recipe recipe = new Recipe(
                        id,
                        name,
                        description,
                        ingList,
                        stepList,
                        category,
                        time,
                        servings,
                        false,
                        manager.getCurrentDate(),
                        false
                );

                manager.addRecipe(recipe);
                JOptionPane.showMessageDialog(frame, "Рецепт добавлен!");
            }
        });

        // Просмотр всех рецептов 
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Recipe> all = manager.getAllRecipes();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < all.size(); i++) {
                    sb.append(manager.recipeToString(all.get(i))).append("\n\n");
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));
                JOptionPane.showMessageDialog(frame, scrollPane, "Все рецепты", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Поиск по ингредиенту рецепта
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ingredient = JOptionPane.showInputDialog("Введите ингредиент:");
                List<Recipe> results = manager.searchByIngredient(ingredient);
                showResults(results, frame, "Результаты поиска по ингредиенту");
            }
        });

        // Фильтр по категории рецептов
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String category = JOptionPane.showInputDialog("Введите категорию:");
                List<Recipe> results = manager.filterByCategory(category);
                showResults(results, frame, "Рецепты по категории");
            }
        });

        // Сортировка по времени
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Recipe> results = manager.sortByCookingTime();
                showResults(results, frame, "Рецепты по времени приготовления");
            }
        });

        // Избранные рецепты
        favoritesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Recipe> results = manager.getFavorites();
                showResults(results, frame, "Избранные рецепты");
            }
        });

        // Расширенный поиск
        advancedSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ingredients = JOptionPane.showInputDialog("Ингредиенты (через запятую):");
                String category = JOptionPane.showInputDialog("Категория:");
                String time = JOptionPane.showInputDialog("Время приготовления:");
                String servings = JOptionPane.showInputDialog("Количество порций:");

                List<String> ingredientList = Arrays.asList(ingredients.split(","));
                List<Recipe> results = manager.advancedSearch(ingredientList, category, time, servings);
                showResults(results, frame, "Расширенный поиск");
            }
        });

        // План на готовку
        planButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Recipe> results = manager.getPlannedRecipes();
                showResults(results, frame, "План готовки");
            }
        });

        // Выход из приложения
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    // Метод для отображения списка рецептов
    private static void showResults(List<Recipe> results, JFrame frame, String title) {
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Рецепты не найдены.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        RecipeManager manager = new RecipeManager(); // только для вызова метода recipeToString
        for (Recipe r : results) {
            sb.append(manager.recipeToString(r)).append("\n\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
