package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = this.conn.prepareStatement(
                    "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            pst.setString(1, seller.getName());
            pst.setString(2, seller.getEmail());
            pst.setDate(3, java.sql.Date.valueOf(seller.getBirthDate()));
            pst.setDouble(4, seller.getBaseSalary());
            pst.setInt(5, seller.getDepartment().getId());

            int rows = pst.executeUpdate();

            if(rows > 0) {
                rs = pst.getGeneratedKeys();
                if(rs.next()) {
                    seller.setId(rs.getInt(1));
                }
            } else {
                throw new DbException("Could not add new seller.");
            }
        } catch(SQLException e) {
            throw new DbException("Error while trying to add a new seller to the database.");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement pst = null;

        try {
            pst = this.conn.prepareStatement(
                    "UPDATE seller "
                    + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                    + "WHERE Id = ?"
            );

            pst.setString(1, seller.getName());
            pst.setString(2, seller.getEmail());
            pst.setDate(3, java.sql.Date.valueOf(seller.getBirthDate()));
            pst.setDouble(4, seller.getBaseSalary());
            pst.setInt(5, seller.getDepartment().getId());
            pst.setInt(6, seller.getId());

            pst.executeUpdate();
        } catch(SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement pst = null;

        try {
            pst = this.conn.prepareStatement(
                    "DELETE FROM seller "
                    + "WHERE Id = ?"
            );

            pst.setInt(1, id);

            int rows = pst.executeUpdate();

            if(rows < 1) {
                throw new DbException("The specified id does not exist.");
            }
        } catch(SQLException e) {
            throw new DbException("Error while deleting row.");
        } finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = this.conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?"
            );

            pst.setInt(1, id);

            rs = pst.executeQuery();

            if(rs.next()) {
                Department dep = this.instantiateDepartment(rs);
                Seller seller = this.instantiateSeller(rs, dep);

                return seller;
            }

            return null;
        } catch(SQLException e) {
            throw new DbException("Error while searching by ID");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = this.conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.DepartmentId = ? "
                    + "ORDER BY Name"
            );

            pst.setInt(1, department.getId());

            rs = pst.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null) {
                    dep = this.instantiateDepartment(rs);
                    map.put(dep.getId(), dep);
                }

                Seller seller = this.instantiateSeller(rs, dep);
                sellers.add(seller);
            }

            return sellers;
        } catch(SQLException e) {
            throw new DbException("Error while searching by Department Id");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

    @Override
    public List<Seller> findAll() {
        Statement st = null;
        ResultSet rs = null;

        List<Seller> sellers = new ArrayList<>();

        try {
            st = this.conn.createStatement();
            rs = st.executeQuery(
                    "SELECT seller.*, department.Name as DepName FROM seller "
                    + "INNER JOIN department ON seller.DepartmentId = department.Id "
                    + "ORDER BY Id"
            );

            Map<Integer, Department> departs = new HashMap<>();

            while(rs.next()) {
                if(!departs.containsKey(rs.getInt("DepartmentId"))) {
                    departs.put(rs.getInt("DepartmentId"), this.instantiateDepartment(rs));
                }

                sellers.add(this.instantiateSeller(rs, departs.get(rs.getInt("DepartmentId"))));
            }

            return sellers;
        } catch(SQLException e) {
            throw new DbException("Error while trying to select all elements from table seller.");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        return new Seller(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getDate("BirthDate").toLocalDate(),
                rs.getDouble("BaseSalary"),
                dep
        );
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
    }
}
