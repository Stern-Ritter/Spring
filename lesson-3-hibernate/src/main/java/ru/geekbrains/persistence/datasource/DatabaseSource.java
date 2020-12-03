package ru.geekbrains.persistence.datasource;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;

public class DatabaseSource {

    private DatabaseSource(){}

    private static final EntityManagerFactory emFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static EntityManagerFactory getEmFactory() {
        return emFactory;
    }
}