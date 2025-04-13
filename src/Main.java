import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.io.File;
public class Main {
    private static RecipeManager manager;

    public static void main(String[] args) {
        manager = new RecipeManager("recipes.db");

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Recipe Collection Organizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(520, 600);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            String[] buttonLabels = {
                    "Добавить рецепт",
                    "Посмотреть все рецепты",
                    "Поиск по ингредиенту",
                    "Фильтрация по категориям блюд",
                    "Сортировка по времени приготовления",
                    "Избранные рецепты",
                    "Запланированные рецепты",
                    "Расширенный поиск",
                    "Удалить рецепт",
                    "Импортировать JSON-файл",
                    "Экспортировать JSON-файл",
                    "Выйти"
            };

            JButton[] buttons = new JButton[buttonLabels.length];
            for (int i = 0; i < buttonLabels.length; i++) {
                buttons[i] = new JButton(buttonLabels[i]);
                buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
                buttons[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                panel.add(Box.createVerticalStrut(5));
                panel.add(buttons[i]);
            }

            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setBorder(null);
            frame.add(scrollPane);
            frame.setVisible(true);

            // Обработчики событий кнопок
            buttons[0].addActionListener(e -> showAddRecipeDialog());
            buttons[1].addActionListener(e -> showRecipeList(manager.getAllRecipes()));
            buttons[2].addActionListener(e -> {
                String input = JOptionPane.showInputDialog("Введите ингредиент:");
                if (input != null) showRecipeList(manager.searchByIngredient(input));
            });
            buttons[3].addActionListener(e -> {
                String input = JOptionPane.showInputDialog("Введите категорию:");
                if (input != null) showRecipeList(manager.filterByCategory(input));
            });
            buttons[4].addActionListener(e -> showRecipeList(manager.sortByCookingTime()));
            buttons[5].addActionListener(e -> showRecipeList(manager.getFavorites()));
            buttons[6].addActionListener(e -> showRecipeList(manager.getPlannedRecipes()));
            buttons[7].addActionListener(e -> showAdvancedSearchDialog());
            buttons[8].addActionListener(e -> {
                String input = JOptionPane.showInputDialog("Введите ID рецепта для удаления:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int id = Integer.parseInt(input.trim());
                        manager.deleteRecipeById(id);
                        JOptionPane.showMessageDialog(null, "Рецепт удалён.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Ошибка при удалении рецепта. Убедитесь, что ID — число.");
                    }
                }
            });
            buttons[9].addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (file.exists() && file.length() > 0) {
                        manager.importFromJson(file.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Импорт завершён.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Файл пустой или не существует.");
                    }
                }
            });
            buttons[10].addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    manager.exportToJson(chooser.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(null, "Экспорт завершён.");
                }
            });
            buttons[11].addActionListener(e -> System.exit(0));
        });
    }

    // Диалог добавления рецепта
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

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Название:")); panel.add(nameField);
        panel.add(new JLabel("Описание:")); panel.add(descriptionField);
        panel.add(new JLabel("Ингредиенты (через запятую):")); panel.add(ingredientsField);
        panel.add(new JLabel("Шаги (через запятую):")); panel.add(stepsField);
        panel.add(new JLabel("Категория:")); panel.add(categoryField);
        panel.add(new JLabel("Время приготовления:")); panel.add(cookingTimeField);
        panel.add(new JLabel("Порции:")); panel.add(servingSizeField);
        panel.add(favoriteBox); panel.add(planBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Добавить рецепт",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Название рецепта не может быть пустым.");
                return;
            }

            Recipe recipe = new Recipe(
                    0,
                    nameField.getText().trim(),
                    descriptionField.getText().trim(),
                    List.of(ingredientsField.getText().split("\\s*,\\s*")),
                    List.of(stepsField.getText().split("\\s*,\\s*")),
                    categoryField.getText().trim(),
                    cookingTimeField.getText().trim(),
                    servingSizeField.getText().trim(),
                    favoriteBox.isSelected(),
                    getCurrentDate(),
                    planBox.isSelected()
            );

            manager.addRecipe(recipe);
            JOptionPane.showMessageDialog(null, "Рецепт добавлен.");
        }
    }

    // Показ списка рецептов
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

    // Диалог расширенного поиска
    private static void showAdvancedSearchDialog() {
        JTextField ingredientField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField servingField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Ингредиент:")); panel.add(ingredientField);
        panel.add(new JLabel("Категория:")); panel.add(categoryField);
        panel.add(new JLabel("Время приготовления:")); panel.add(timeField);
        panel.add(new JLabel("Порции:")); panel.add(servingField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Расширенный поиск",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            List<Recipe> found = manager.advancedSearch(
                    ingredientField.getText().trim(),
                    categoryField.getText().trim(),
                    timeField.getText().trim(),
                    servingField.getText().trim()
            );
            showRecipeList(found);
        }
    }

    // Получение текущей даты
    private static String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
}