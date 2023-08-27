package com.example.kr_controller.repository;

import com.example.kr_controller.model.User;
import lombok.Cleanup;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class UserRepositoryTest {
    private static UserRepository userRepository;
    @BeforeAll
    private static void configurationHibernateAndCreateValue() {
        StandardServiceRegistry registry
                = new StandardServiceRegistryBuilder().configure().build();
        var sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new User("admin", "password"));
        session.save(new User("user", "password1"));
        session.getTransaction().commit();
        userRepository = new UserRepository(sessionFactory);
    }

    @Test
    public void testFindByUsernameAndId() {
        User user1 = new User("admin", "password");
        var user2 = userRepository.findByUsernameAndPassword(user1.getUsername(), user1.getPassword()).get();
        Assertions.assertEquals(user1, user2);
    }
}
