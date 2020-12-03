package ru.geekbrains.persistence.interfaces;

import ru.geekbrains.persistence.entities.Customer;

import java.util.List;

public interface DatabaseProductService <E, K> {
    void insert (E entity);
    E getEntityById(K id);
    List<E> getAllEntities();
    void update(E entity);
    void delete (K id);
    List<Customer> findAllCustomers(Long id);
}
