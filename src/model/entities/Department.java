package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    private List<Seller> sellers = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Seller> getSellers() {
        return this.sellers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(this.getClass() != other.getClass()) return false;

        return this.getId().equals(((Department)other).getId());
    }

    @Override
    public String toString() {
        return String.format("Id: %s, Name: %s", this.getId(), this.getName());
    }
}
