
package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Md Zeeshan
 */
public class ConnectionProvider {

    private static final String DB_NAME = "attendanceJframebd";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    public static Connection getCon() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL server without specifying a database
            Connection con = DriverManager.getConnection(DB_URL + "?useSSL=false&allowPublicKeyRetrieval=true", DB_USERNAME, DB_PASSWORD);

            // Check if the database exists
            if (!databaseExists(con, DB_NAME)) {
                createDatabase(con, DB_NAME);
            }

            // Connect to the specific database
            con = DriverManager.getConnection(DB_URL + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true", DB_USERNAME, DB_PASSWORD);

            return con;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static boolean databaseExists(Connection con, String dbName) throws Exception {
        try (Statement stmt = con.createStatement()) {
            return stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'").next();
        }
    }

    private static void createDatabase(Connection con, String dbName) throws Exception {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE " + dbName);
            System.out.println("Database '" + dbName + "' created successfully.");
        }
    }
}
