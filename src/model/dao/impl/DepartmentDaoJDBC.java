package model.dao.impl;

import db.*;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = this.conn.prepareStatement(
                    "INSERT INTO department (Name) "
                    + "VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            pst.setString(1, department.getName());

            int rows = pst.executeUpdate();

            if(rows > 0) {
                rs = pst.getGeneratedKeys();

                if(rs.next()) {
                    department.setId(rs.getInt(1));
                }
            }
        } catch(SQLException e) {
            throw new DbException("Error while inserting into table department");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement pst = null;

        try {
            pst = this.conn.prepareStatement(
                    "UPDATE department "
                    + "SET Name = ? "
                    + "WHERE Id = ?"
            );

            pst.setString(1, department.getName());
            pst.setInt(2, department.getId());

            int rows = pst.executeUpdate();
        } catch(SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = this.conn.prepareStatement(
                    "SELECT * FROM department "
                    + "WHERE Id = ?"
            );

            pst.setInt(1, id);

            rs = pst.executeQuery();

            if(rs.next()) {
                return new Department(rs.getInt("Id"), rs.getString("Name"));
            }

            return null;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DbException("Error while trying to find by id.");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

    @Override
    public List<Department> findAll() {
        return null;
    }
}
