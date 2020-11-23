package com.geekbrains.entity_manager;


import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;

public class MainApp {

    public static void main(String[] args) {

        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

    }
}
