package ru.geekbrains.persistence.implementations;

import ru.geekbrains.persistence.datasource.DatabaseSource;
import ru.geekbrains.persistence.entities.Customer;
import ru.geekbrains.persistence.entities.Product;
import ru.geekbrains.persistence.interfaces.DatabaseCustomerService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

public class DatabaseCustomerServiceImpl implements DatabaseCustomerService<Customer, Long> {
    @Override
    public Customer getEntityById(Long id) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        Customer result;
        em.getTransaction().begin();
        try {
            result = em.find(Customer.class, id);
            em.getTransaction().commit();
            if (result == null) {
                result = new Customer();
                result.setId(-1L);
            }
            return result;
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Customer #" + id + "receive error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Customer entity) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Customer #" + entity.getId() + " insert error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Customer entity) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entity);
            em.getTransaction().commit();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Customer #" + entity.getId() + " update error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            Customer find = em.find(Customer.class, id);
            if (find == null) {
                em.getTransaction().commit();
            } else {
                em.remove(find);
                em.getTransaction().commit();
            }
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Customer #" + id + " delete error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> getAllEntities() {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("All customers get error.", ex);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> findAllPurchases(Long id) {
        EntityManager em = DatabaseSource.getEmFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            List<Product> products = em.createNamedQuery("Customer.findAllPurchases", Product.class)
                    .setParameter("id", id)
                    .getResultList();
            return products;
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new RuntimeException("Find all purchases of customer error.", ex);
        } finally {
            em.close();
        }
    }
}