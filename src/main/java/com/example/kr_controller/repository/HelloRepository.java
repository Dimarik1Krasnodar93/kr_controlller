package com.example.kr_controller.repository;

import com.example.kr_controller.model.Hello;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HelloRepository {
    private final SessionFactory sf;

    public Hello findFirstHello() {
        if (sf.getCurrentSession() == null) {
            return findFirstHello2(sf.openSession());
        } else {
            return findFirstHello2(sf.getCurrentSession());
        }
    }

    private Hello findFirstHello2(Session session) {
        Hello rsl;
        session.beginTransaction();
        rsl = session.find(Hello.class, 1);
        session.getTransaction().commit();
        return rsl;
    }
}
