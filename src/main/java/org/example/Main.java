package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Group;
import org.example.entities.User;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String puName = "pu-name";
        Map<String,String> props = new HashMap<>();
        props.put("hibernate.show_sql","true");
        // props.put("hibernate.hbm2ddl.auto","create");
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName),props);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            User u1 = new User();
            u1.setName("Bahattin");

            User u2 = new User();
            u2.setName("Salih");

            Group g1 = new Group();
            g1.setName("Group 1");

            Group g2 = new Group();
            g2.setName("Group 2");

            g1.setUsers(List.of(u1));
            g2.setUsers(List.of(u1,u2));

            u1.setGroups(List.of(g1,g2));
            u2.setGroups(List.of(g2));

            //em.persist(g1);
            //em.persist(g2);

            em.remove(em.find(User.class,1));

            em.getTransaction().commit();
        }finally {
            em.close();
        }

    }
}