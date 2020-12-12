package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findWithFilter(Optional<String> nameFilter,
                                        Optional<BigDecimal> minPrice,
                                        Optional<BigDecimal> maxPrice) {

        Specification<Product> spec = Specification.where(null);

        if (nameFilter.isPresent() && !nameFilter.get().isEmpty()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        if (minPrice.isPresent()) {
            spec = spec.and(ProductSpecification.minPrice(minPrice.get()));
        }
        if (maxPrice.isPresent() ) {
            spec = spec.and(ProductSpecification.maxPrice(maxPrice.get()));
        }

        return productRepository.findAll(spec);
    }

    @Override
    public List<Product> findAll(Specification<Product> spec) {
        return productRepository.findAll(spec);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}