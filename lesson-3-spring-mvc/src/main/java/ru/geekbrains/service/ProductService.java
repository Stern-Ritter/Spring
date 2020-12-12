package ru.geekbrains.service;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findWithFilter(Optional<String> nameFilter, Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice);

    List<Product> findAll(Specification<Product> spec);

    Optional<Product> findById(Long id);

    void save(Product product);

    void deleteById(Long id);
}