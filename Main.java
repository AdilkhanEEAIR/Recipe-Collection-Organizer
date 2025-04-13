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
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8, 1, 10, 10));

        JButton addBtn = new JButton("Добавить рецепт");
        JButton viewBtn = new JButton("Показать все рецепты");
        JButton viewDetailBtn = new JButton("Показать по ID");
        JButton updateBtn = new JButton("Обновить рецепт");
        JButton deleteBtn = new JButton("Удалить рецепт");
        JButton importBtn = new JButton("Импорт из JSON");
        JButton exportBtn = new JButton("Экспорт в JSON");
        JButton exitBtn = new JButton("Выход");

        frame.add(addBtn);
        frame.add(viewBtn);
        frame.add(viewDetailBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);
        frame.add(importBtn);
        frame.add(exportBtn);
        frame.add(exitBtn);

        addBtn.addActionListener(e -> manager.addRecipeViaDialog());
        viewBtn.addActionListener(e -> manager.displayAllRecipes());
        viewDetailBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Введите ID рецепта:");
            manager.viewRecipeById(id);
        });
        updateBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Введите ID рецепта для обновления:");
            manager.updateRecipeViaDialog(id);
        });
        deleteBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Введите ID рецепта для удаления:");
            manager.deleteRecipe(id);
        });
        importBtn.addActionListener(e -> manager.importFromJson());
        exportBtn.addActionListener(e -> manager.exportToJson());
        exitBtn.addActionListener(e -> frame.dispose());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

