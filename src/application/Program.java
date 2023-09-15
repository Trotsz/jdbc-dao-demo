package application;

import db.*;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.*;

import java.text.SimpleDateFormat;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

        SellerDao sdjdbc = DaoFactory.createSellerDao();

        System.out.println("==== TEST SELLER: Method findById ====");
        Seller seller1 = sdjdbc.findById(2);
        System.out.println(seller1);

        System.out.println("\n==== TEST SELLER: Method findByDepartment ====");
        List<Seller> sellers = sdjdbc.findByDepartment(new Department(1, "Computer"));
        sellers.forEach(System.out::println);

        System.out.println("\n ==== TEST SELLER: Method findAll ==== ");
        for(Seller s : sdjdbc.findAll()) {
            System.out.println(s);
        }
    }
}
