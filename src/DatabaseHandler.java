import java.sql.*;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:recipes.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "ingredients TEXT," +
                "steps TEXT," +
                "category TEXT," +
                "cookingTime TEXT," +
                "servingSize TEXT," +
                "isFavorite INTEGER," +
                "createdDate TEXT," +
                "inPlan INTEGER" +
                ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}