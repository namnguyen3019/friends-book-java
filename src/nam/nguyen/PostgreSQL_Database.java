package nam.nguyen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQL_Database implements DataStorage {

    final String url = "jdbc:postgresql://localhost:5432/friendsbook";
    final String user = "namnguyen";
    final String password = "123456";
    Connection conn = null;
    Statement statement = null;

    @Override
    public void createAUser(String username, String password, String name, String school) {
        try {
            conn = DriverManager.getConnection(url, user, password);
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            int insertResult = statement.executeUpdate("INSERT INTO users(username, password, name, school) VALUES ( '"
                    + username + "' , '" + password + "', '" + name + "', '" + school + "')");
            if (insertResult == 1) {
                conn.commit();
                conn.setAutoCommit(true);
                System.out.println("New account has been created");
            } else {
                System.out.println("Oh no cannot create a new account");
            }

        } catch (SQLException e) {
            // handle the exceptions
            System.out.println("Account creation failed");
            e.printStackTrace();

        } finally {
            try {
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
