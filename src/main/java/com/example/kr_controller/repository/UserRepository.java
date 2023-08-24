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
        rsl = session.createQuery("from User u where u.username = :1? and u.password = :2?", User.class)
                .setParameter("1?", username)
                .setParameter("2?", password)
                .uniqueResultOptional();
        session.beginTransaction().commit();
        return rsl;
    }
}
