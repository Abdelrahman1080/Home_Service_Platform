package org.example.userservice.ejb;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import org.example.userservice.entity.User;
import org.example.userservice.repository.UserRepository;

import java.util.List;

@Singleton
public class AdminBean {

    @Inject
    private UserRepository repo;

    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
