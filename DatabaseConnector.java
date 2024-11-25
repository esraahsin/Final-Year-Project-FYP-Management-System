import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/mybdd";
    private static final String USER = "root";
    private static final String PASSWORD = "essrahssin";

    private static Connection connection = null;

    // Méthode pour établir la connexion à la base de données
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données établie.");
        }
        return connection;
    }

    // Méthode pour fermer la connexion à la base de données
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connexion à la base de données fermée.");
        }
    }
}
