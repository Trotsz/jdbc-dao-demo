package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn;

    public static Connection getConnection() {
        if(conn == null) {
            try {
                Properties props = loadProperties();

                String url = props.getProperty("dburl");

                conn = DriverManager.getConnection(url, props);
            } catch(SQLException e) {
                e.printStackTrace();
                throw new DbException("There was an error while trying to get connection with the database.");
            }
        }

        return conn;
    }

    private static Properties loadProperties() {
        try(FileInputStream fis = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fis);

            return props;
        } catch(IOException e) {
            throw new DbException("There was an error while trying to load properties.");
        }
    }

    public static void closeConnection() {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch(SQLException e) {
            throw new DbException("Error while closing the connection.");
        }
    }

    public static void closeStatement(Statement st) {
        try {
            if(st != null && !st.isClosed()) {
                st.close();
            }
        } catch(SQLException e) {
            throw new DbException("Error while closing statement");
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if(rs != null && !rs.isClosed()) {
                rs.close();
            }
         } catch(SQLException e) {
            throw new DbException("Eror while closing result set");
        }
    }
}
