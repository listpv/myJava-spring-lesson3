package com.geekbrains.task;

import com.geekbrains.entity.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DataApp {

    public static void main(String[] args) {

        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Good.class)
                .addAnnotatedClass(GoodData.class)
                .buildSessionFactory();
        EntityManager em = factory.createEntityManager();

        clear(em);

        try {

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();



            User user1 = new User("user1");
            User user2 = new User("user2");
            User user3 = new User("user3");
            User user4 = new User("user4");

            Good good1 = new Good("good1", 100.0);
            Good good2 = new Good("good2", 200.0);
            Good good3 = new Good("good3", 300.0);
            Good good4 = new Good("good4", 400.0);

            GoodData goodData1 = new GoodData(user1, good1);
            GoodData goodData2 = new GoodData(user2, good2);
            GoodData goodData3 = new GoodData(user3, good3);
            GoodData goodData4 = new GoodData(user4, good4);
            GoodData goodData5 = new GoodData(user1, good4);
            GoodData goodData6 = new GoodData(user4, good1);
            GoodData goodData7 = new GoodData(user2, good3);
            GoodData goodData8 = new GoodData(user3, good2);


            em.persist(goodData1);
            em.persist(goodData2);
            em.persist(goodData3);
            em.persist(goodData4);
            em.persist(goodData5);
            em.persist(goodData6);
            em.persist(goodData7);
            em.persist(goodData8);

            transaction.commit();

//            User user = em.find(User.class, user1.getId());
//            System.out.println(user);
//            List<GoodData> goodData = em.createQuery("FROM GoodData gd WHERE gd.user.id = : id", GoodData.class).
//                    setParameter("id", user.getId()).getResultList();
//            for(GoodData o : goodData){
//                System.out.println(0);
//            }

            Good good = em.find(Good.class, good1.getId());
            transaction.begin();
            good.setPrice(150.0);
            transaction.commit();

            List<GoodData> goodData10 = em.createQuery("From GoodData", GoodData.class).getResultList();
            for (GoodData o : goodData10){
                if(o.getGood().equals(good)){
                    System.out.println(o);
                }
            }


        }finally {
            factory.close();
            if (em != null) {
                em.close();
            }
        }

    }

    private static void clear(EntityManager em) {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM GoodData").executeUpdate();
        em.createQuery("DELETE FROM Good").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.getTransaction().commit();
    }


}
