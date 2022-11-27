package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.channels.ConnectionPendingException;
import java.sql.Statement;

public class ConnectDB {
    private static String url = "jdbc:mysql://127.0.0.1:3306/cloth_shop";
    private static String user = "hbstudent";
    private static String password="hbstudent";

    public static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    public static void connectDB(String query) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Statement statement=connection.createStatement();
        statement.execute(query);
    }
}
