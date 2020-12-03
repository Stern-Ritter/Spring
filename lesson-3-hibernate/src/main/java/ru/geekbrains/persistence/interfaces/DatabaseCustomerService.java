package ru.geekbrains.persistence.interfaces;

import ru.geekbrains.persistence.entities.Product;

import java.util.List;

public interface DatabaseCustomerService <E, K> {
    void insert (E entity);
    E getEntityById(K id);
    List<E> getAllEntities();
    void update(E entity);
    void delete (K id);
    List<Product> findAllPurchases(Long id);
}
