import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    private static RecipeManager manager = new RecipeManager();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showMenu();
            }
        });
    }

    public static void showMenu() {
        JFrame frame = new JFrame("Органайзер рецептов");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(10, 1, 10, 10));

        JButton addBtn = new JButton("Добавить рецепт");
        JButton viewBtn = new JButton("Показать все рецепты");
        JButton viewByIdBtn = new JButton("Показать по ID");
        JButton updateBtn = new JButton("Редактировать рецепт");
        JButton deleteBtn = new JButton("Удалить рецепт");
        JButton findByCategory = new JButton("Поиск по категории");
        JButton findByIngredient = new JButton("Поиск по ингредиенту");
        JButton importBtn = new JButton("Импорт из JSON");
        JButton exportBtn = new JButton("Экспорт в JSON");
        JButton exitBtn = new JButton("Выход");

        frame.add(addBtn);
        frame.add(viewBtn);
        frame.add(viewByIdBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);
        frame.add(findByCategory);
        frame.add(findByIngredient);
        frame.add(importBtn);
        frame.add(exportBtn);
        frame.add(exitBtn);

        addBtn.addActionListener(e -> manager.addRecipeViaDialog());
        viewBtn.addActionListener(e -> manager.displayAllRecipes());
        viewByIdBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Введите ID рецепта:");
            manager.viewRecipeById(id);
        });
        updateBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Введите ID рецепта для редактирования:");
            manager.updateRecipeViaDialog(id);
        });
        deleteBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Введите ID рецепта для удаления:");
            manager.deleteRecipe(id);
        });
        findByCategory.addActionListener(e -> {
            String category = JOptionPane.showInputDialog("Введите категорию:");
            manager.editOrDeleteByCategory(category);
        });
        findByIngredient.addActionListener(e -> {
            String ingredient = JOptionPane.showInputDialog("Введите ингредиент:");
            manager.editOrDeleteByIngredient(ingredient);
        });
        importBtn.addActionListener(e -> manager.importFromJson());
        exportBtn.addActionListener(e -> manager.exportToJson());
        exitBtn.addActionListener(e -> frame.dispose());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
