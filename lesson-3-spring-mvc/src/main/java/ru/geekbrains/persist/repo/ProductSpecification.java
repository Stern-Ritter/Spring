package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }
    /*
    public static Specification<Product> minPrice(BigDecimal minPrice){
        return (root, query, builder) -> builder.gt(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPrice(BigDecimal maxPrice){
        return (root, query, builder) -> builder.lt(root.get("price"), maxPrice);
    }
    TODO добавить спецификации для условий согласно ДЗ */
}
