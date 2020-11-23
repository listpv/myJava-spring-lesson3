package com.geekbrains.task;

import com.geekbrains.entity.Client;
import com.geekbrains.entity.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TaskApp {

    public static void main(String[] args) {

        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
        EntityManager em = factory.createEntityManager();

        showAll(em);

        clear(em);

        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Client client1 = new Client("client1");
            Client client2 = new Client("client2");
            Client client3 = new Client("client3");
            Client client4 = new Client("client4");

            Product product1 = new Product("product1", 100.0);
            Product product2 = new Product("product2", 200.0);
            Product product3 = new Product("product3", 300.0);
            Product product4 = new Product("product4", 400.0);

            client1.setProducts(product1);
            client2.setProducts(product2);
            client3.setProducts(product3);
            client4.setProducts(product4);
            client1.setProducts(product4);
            client2.setProducts(product3);
            client3.setProducts(product2);
            client4.setProducts(product1);

            em.persist(client1);
            em.persist(client2);
            em.persist(client3);
            em.persist(client4);

            transaction.commit();

            Client client = em.find(Client.class, client1.getId());
            System.out.println(client);
            for(Product product : client.getProducts()){
                System.out.println(product.getTitle());
            }

            Product product5 = em.find(Product.class, product1.getId());
            System.out.println(product5);
            for(Client client5 : product5.getClients()){
                System.out.println(client5.getName());
            }

            em.getTransaction().begin();
            em.createQuery("DELETE FROM Product p WHERE p.title = 'product1'").executeUpdate();
            em.getTransaction().commit();

            Client client_1 = em.find(Client.class, client1.getId());
            System.out.println(client_1);
            for(Product product : client_1.getProducts()){
                System.out.println(product.getTitle());
            }

            System.out.println("----------------------------------------");


            Product product_5 = em.find(Product.class, product1.getId());
            System.out.println(product_5);
            for(Client client5 : product_5.getClients()){
                System.out.println(client5.getName());
            }

            showAll(em);
//            List<Product> products = em.createQuery("FROM Product", Product.class).getResultList();
//            List<Client> clients = em.createQuery("FROM Client", Client.class).getResultList();
//            System.out.println("FROM Product  " + products);
//            System.out.println("FROM Client  " + clients);

            em.getTransaction().begin();;
            em.createQuery("DELETE FROM Client c WHERE c.name = 'client1'").executeUpdate();
            em.getTransaction().commit();

            System.out.println("----------------------------------------");

            List<Product> products1 = em.createQuery("FROM Product", Product.class).getResultList();
            List<Client> clients1 = em.createQuery("FROM Client", Client.class).getResultList();
            System.out.println("FROM Product  " + products1);
            System.out.println("FROM Client  " + clients1);







        }finally {
            factory.close();
            if (em != null) {
                em.close();
            }
        }

    }

    private static void clear(EntityManager em) {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Product").executeUpdate();
        em.createQuery("DELETE FROM Client").executeUpdate();
        em.getTransaction().commit();
    }

    private static void showAll(EntityManager em){

        List<Product> products = em.createQuery("FROM Product", Product.class).getResultList();
        List<Client> clients = em.createQuery("FROM Client", Client.class).getResultList();
        System.out.println("FROM Product  " + products);
        System.out.println("FROM Client  " + clients);
    }
}
