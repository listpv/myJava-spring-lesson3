package com.geekbrains.task;

import com.geekbrains.entity.Book;
import com.geekbrains.entity.Person;
import com.geekbrains.entity.Reader;
import com.geekbrains.entity.Thing;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class AddedApp {

    public static void main(String[] args) {

        EntityManagerFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Thing.class)
                .addAnnotatedClass(Person.class)
                .buildSessionFactory();
        EntityManager em = factory.createEntityManager();

        clear(em);

        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Person person1 = new Person("One person");
            Person person2 = new Person("Two person");

            Thing thing1 = new Thing("One thing");
            Thing thing2 = new Thing("Two thing");

//            List<Person> personList= new ArrayList<>();
//            personList.add(person1);
//            personList.add(person2);
//
//            List<Thing> thingList = new ArrayList<>();
//            thingList.add(thing1);
//            thingList.add(thing2);


//            person1.setThings(thingList);
//            person2.setThings(thingList);

//            thing1.setPersons(personList);
//            thing2.setPersons(personList);

            person1.setTing(thing1);
            person1.setTing(thing2);

            em.persist(person1);
            em.persist(person2);

            transaction.commit();

            Person person = em.find(Person.class, person1.getId());
            System.out.println(person);
            System.out.println("Thing: ");
            for (Thing o : person.getThings()) {
                System.out.println(o.getTitle());
            }

            List<Person> readers = em.createQuery("SELECT p FROM Person p ORDER BY size(p.things) DESC").getResultList();
            System.out.println(readers);

            transaction.begin();
            em.createQuery("DELETE FROM Thing th WHERE th.title = 'thing1'").executeUpdate();
            transaction.commit();

            List<Thing> things = em.createQuery("FROM Thing").getResultList();
            System.out.println(things);

        } finally {
            factory.close();
            if (em != null) {
                em.close();
            }
        }
    }

    private static void clear(EntityManager em) {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person").executeUpdate();
        em.createQuery("DELETE FROM Thing").executeUpdate();
        em.getTransaction().commit();
    }
}
