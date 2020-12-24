package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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

    private static final int INITIAL_PAGE_SIZE = 5;

    @Override
    public Page<Product> findWithFilter(Model model,
                                        Optional<String> nameFilter,
                                        Optional<BigDecimal> minPrice,
                                        Optional<BigDecimal> maxPrice,
                                        Optional<Integer> page,
                                        Optional<Integer> size,
                                        Optional<String> sortField,
                                        Optional<String> sortOrder) {

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

        int evalPageSize = size.orElse(INITIAL_PAGE_SIZE);
        String sortValue = sortField.orElse("id");
        String sortDirection = sortOrder.orElse("ASC");
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortValue);
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("sortValue", sortValue);
        model.addAttribute("sortDirection", sortDirection);
        return productRepository.findAll(spec, PageRequest.of(page.orElse(1) - 1, evalPageSize, sort));
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