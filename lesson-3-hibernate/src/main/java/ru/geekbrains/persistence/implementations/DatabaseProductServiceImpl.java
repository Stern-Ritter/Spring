package ru.geekbrains.persistence.implementations;

import ru.geekbrains.persistence.datasource.DatabaseSource;
import ru.geekbrains.persistence.entities.Customer;
import ru.geekbrains.persistence.entities.Product;
import ru.geekbrains.persistence.interfaces.DatabaseProductService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

public class DatabaseProductServiceImpl implements DatabaseProductService<Product, Long> {
    @Override
    public Product getEntityById(Long id) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        Product result;
        em.getTransaction().begin();
        try {
            result = em.find(Product.class, id);
            em.getTransaction().commit();
            if (result == null) {
                result = new Product();
                result.setId(-1L);
            }
            return result;
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Product #" + id + "receive error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Product entity) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Product #" + entity.getId() + " insert error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Product entity) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entity);
            em.getTransaction().commit();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Product #" + entity.getId() + " update error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            Product find = em.find(Product.class, id);
            if (find == null) {
                em.getTransaction().commit();
            } else {
                em.remove(find);
                em.getTransaction().commit();
            }
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Product #" + id + " delete error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> getAllEntities() {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("All products get error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> findAllCustomers(Long id) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            List<Customer> customers = em.createNamedQuery("Product.findAllCustomers", Customer.class)
                    .setParameter("id", id)
                    .getResultList();
            return customers;
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Find all customers of product error.", ex);
        } finally {
            em.close();
        }
    }
}