package com.abirami.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.abirami.model.Category;
import com.abirami.model.Product;

public class HibernateConfig {

    private static SessionFactory sessionFactory ;
    static {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/abirami_traders");                                
        configuration.setProperty("hibernate.connection.username", "springuser");     
        configuration.setProperty("hibernate.connection.password", "admin@123");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.connection.pool_size", "10");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
} 