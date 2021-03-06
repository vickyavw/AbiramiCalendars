package com.abirami.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.abirami.model.Category;
import com.abirami.model.Format;
import com.abirami.model.Product;

public class HibernateConfig {

    private static SessionFactory sessionFactory ;
    static {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(Format.class);
        configuration.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/abirami_traders");                                
        configuration.setProperty("hibernate.connection.username", "springuser");     
        configuration.setProperty("hibernate.connection.password", "admin@123");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.connection.pool_size", "10");
        configuration.setProperty("hibernate.cache.use_second_level_cache", "true");
        configuration.setProperty("hibernate.cache.use_query_cache", "true");
        configuration.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
//      try {
        	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        	sessionFactory = configuration.buildSessionFactory(builder.build());
//        }
//        catch (Exception e) {
//			throw new ExceptionInInitializerError(e);
//		}
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
} 