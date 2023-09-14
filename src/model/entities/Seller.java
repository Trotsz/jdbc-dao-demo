package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;

    private Department department;

    public Seller(int id, String name, String email, Date birthDate, double baseSalary, Department department) {
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

    public Date getBirthDate() {
        return this.birthDate;
    }

    public Double getBaseSalary() {
        return this.baseSalary;
    }

    public Department getDepartment() {
        return this.department;
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
        return String.format("Id: %s, Name: %s | Email: %s", this.getId(), this.getName(), this.getEmail());
    }
}
