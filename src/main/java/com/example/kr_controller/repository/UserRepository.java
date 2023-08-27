package com.example.kr_controller.repository;

import com.example.kr_controller.model.User;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        @Cleanup var session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        session.beginTransaction();
        rsl = session.createQuery("from User u where u.username = :username and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResultOptional();
        session.getTransaction().commit();
        return rsl;
    }
}
