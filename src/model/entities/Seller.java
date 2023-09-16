package model.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Seller implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Integer id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private Double baseSalary;

    private Department department;

    public Seller(String name, String email, LocalDate birthDate, double baseSalary, Department department) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    public Seller(int id, String name, String email, LocalDate birthDate, double baseSalary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Double getBaseSalary() {
        return this.baseSalary;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getEmail());
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(this.getClass() != other.getClass()) return false;
    
        return this.getId().equals(((Seller)other).getId()) && this.getEmail().equals(((Seller)other).getEmail());
    }

    @Override
    public String toString() {
        return String.format("Id: %s, Name: %s | Email: %s | Birth Date: %s | Base Salary: %.2f | Department Id: %d", this.getId(), this.getName(), this.getEmail(), fmt.format(this.getBirthDate()), this.getBaseSalary(), this.getDepartment().getId());
    }
}
