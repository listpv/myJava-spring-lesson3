package com.geekbrains.session_factory;

import com.geekbrains.entity.SimpleItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryApp {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(SimpleItem.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            SimpleItem newSimpleItem = new SimpleItem("Orange", 100);
            session.save(newSimpleItem);
            session.getTransaction().commit();


            session = factory.getCurrentSession();
            session.beginTransaction();
            SimpleItem simpleItemFromDb = session.get(SimpleItem.class, newSimpleItem.getId());
            System.out.println(simpleItemFromDb);
            session.getTransaction().commit();

        } finally {
            factory.close();
            if (session != null) {
                session.close();
            }
        }
    }
}
