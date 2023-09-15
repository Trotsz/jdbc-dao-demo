package application;

import db.*;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

public class Program {
    public static void main(String[] args) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

        SellerDao sdjdbc = DaoFactory.createSellerDao();


        System.out.println("==== TEST SELLER: Method findById ====");
        Seller seller1 = sdjdbc.findById(2);
        System.out.println("Id: " + seller1.getId());
        System.out.println("Name: " + seller1.getName());
        System.out.println("Email: " + seller1.getEmail());
        System.out.println("Birth Date: " + fmt.format(seller1.getBirthDate()));
        System.out.println("Base Salary: " + seller1.getBaseSalary());
        System.out.println("Department Id: " + seller1.getDepartment().getId());
        System.out.println("Department Name: " + seller1.getDepartment().getName());

        System.out.println("\n==== TEST SELLER: Method findByDepartment ====");
        List<Seller> sellers = sdjdbc.findByDepartment(new Department(1, "Computer"));
        sellers.forEach(System.out::println);
    }
}
