package org.example.userservice.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.example.userservice.entity.User;

import java.util.List;

@Stateless
public class UserRepository {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findByUsername(String username) {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        return users.isEmpty() ? null : users.get(0);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {

        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
