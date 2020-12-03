package ru.geekbrains.persistence.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@NamedQuery(name = "Product.findAllCustomers", query = "SELECT c FROM Product p INNER JOIN p.buyers c WHERE p.id = :id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name ="products_customers",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> buyers;

    public Product() {
    }

    public Product(Long id, String name, String description, BigDecimal price, List<Customer> buyers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.buyers = buyers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Customer> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Customer> buyers) {
        this.buyers = buyers;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' +
                ", price=" + price + '}';
    }
}