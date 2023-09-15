package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

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
            pst = conn.prepareStatement(
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

                sellers.add(this.instantiateSeller(rs, dep));
            }

            return sellers;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DbException("Error while searching by Department Id");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        return new Seller(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getDate("BirthDate"),
                rs.getDouble("BaseSalary"),
                dep
        );
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
    }
}
