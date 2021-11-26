package db;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

/**
 * Constructs and executes all the required queries
 */
public class Queries {
    private final Bridge bridge;
    private Connection conn;
    private Statement stmt;
    private ResultSet rst;

    public Queries() {
        bridge = new Bridge();
        try {
            conn = bridge.getConnection("adminRen", "adminRen");
            stmt = conn.createStatement();
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("Could not connect to database");
        }
    }

    public ResultSet execute(String query) {
        try {
            rst = stmt.executeQuery(query);
//            System.out.println(rst);
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getMessage());
        }
        return rst;
    }

    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public void close() {
        bridge.closeAll(rst, stmt, conn);
    }
}