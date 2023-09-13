package application;

import db.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT * FROM seller"
            );

            while(rs.next()) {
                System.out.print(rs.getInt("Id") + " | ");
                System.out.print(rs.getString("Name") + " | ");
                System.out.print(rs.getString("Email") + " | ");
                System.out.print(rs.getDate("BirthDate") + " | ");
                System.out.print(rs.getDouble("BaseSalary") + " | ");
                System.out.print(rs.getInt("DepartmentId") + " | ");
                System.out.println();
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
