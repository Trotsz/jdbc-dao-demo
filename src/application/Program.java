package application;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SellerDao sdjdbc = DaoFactory.createSellerDao();

//        System.out.println("==== TEST SELLER: Method findById ====");
//        Seller seller1 = sdjdbc.findById(2);
//        System.out.println(seller1);
//
//        System.out.println("\n==== TEST SELLER: Method findByDepartment ====");
//        List<Seller> sellers = sdjdbc.findByDepartment(new Department(1, "Computer"));
//        sellers.forEach(System.out::println);
//
//        System.out.println("\n ==== TEST SELLER: Method findAll ==== ");
//        for(Seller s : sdjdbc.findAll()) {
//            System.out.println(s);
//        }
//
//        System.out.println("\n ==== TEST SELLER: Method insert ==== ");
//        Department dep1 = new Department(3, "Fashion");
//        Seller seller2 = new Seller(
//                "Julia Souza",
//                "julia@gmail.com",
//                LocalDate.parse("2004-07-12"),
//                2340.00,
//                dep1
//        );
//        sdjdbc.insert(seller2);
//
//        System.out.println("New seller inserted, Id: " + seller2.getId());
//
//        sellers = sdjdbc.findAll();
//        sellers.forEach(System.out::println);
//
//        System.out.println("\n ==== TEST SELLER: Method update ==== ");
//        Seller seller3 = sdjdbc.findById(14);
//        seller3.setBaseSalary(seller3.getBaseSalary() + 300.00);
//        sdjdbc.update(seller3);
//        System.out.println("Updated row");
//
//        System.out.println("\n ==== TEST SELLER: Method deleteById ==== ");
//        System.out.println("Insert the ID to be deleted: ");
//        int id = sc.nextInt();
//
//        sdjdbc.deleteById(id);
//        System.out.println("Deleted row");

        DepartmentDao ddjdbc = DaoFactory.createDepartmentDao();

        System.out.println("\n ==== TEST DEPARTMENT: method insert ==== ");
        ddjdbc.insert(new Department("Vehicles"));

        System.out.println("\n ==== TEST DEPARTMENT: method findById ==== ");
        Department dep1 = ddjdbc.findById(3);
        System.out.println(dep1);

        System.out.println("\n ==== TEST DEPARTMENT: method update ==== ");
        Department dep2 = ddjdbc.findById(6);
        dep2.setName("Psychology");
        ddjdbc.update(dep2);

        System.out.println("\n ==== TEST DEPARTMENT: method deleteById ==== ");
        System.out.print("Insert the id to be removed: ");
        int id = sc.nextInt();
        ddjdbc.deleteById(id, sdjdbc);

        System.out.println("\n ==== TEST DEPARTMENT: method findAll ==== ");
        List<Department> departs = ddjdbc.findAll();
        departs.forEach(System.out::println);

        sc.close();
    }
}
