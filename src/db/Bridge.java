package db;

import java.sql.*;

/**
 * Class that connects with the database
 */
public class Bridge {
    final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    final String URL = "jdbc:oracle:thin:@localhost:1521:xe";

    /**
     * Searches the Driver class
     */
    public Bridge() {
        try {
            System.out.println(Class.forName(DRIVER));
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
            e.printStackTrace();
        }
    }

    public Connection getConnection(String user, String pwd) throws SQLException {
        return DriverManager.getConnection(URL, user, pwd);
    }

    public void closeAll(ResultSet rst, Statement stmt, Connection conn) {
        if (rst != null) {
            try {
                rst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}