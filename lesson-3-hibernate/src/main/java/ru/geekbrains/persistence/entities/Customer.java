package ru.geekbrains.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@NamedQuery(name = "Customer.findAllPurchases", query = "SELECT p FROM Customer c INNER JOIN c.purchases p WHERE c.id = :id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToMany
    @JoinTable(
            name ="products_customers",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> purchases;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, List<Product> purchases) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.purchases = purchases;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Product> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Product> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
    }
}