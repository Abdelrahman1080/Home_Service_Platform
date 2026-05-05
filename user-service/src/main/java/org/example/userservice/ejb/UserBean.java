package org.example.userservice.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.userservice.entity.User;
import org.example.userservice.enums.Role;
import org.example.userservice.repository.UserRepository;

@Stateless
public class UserBean {

    @Inject
    private UserRepository repo;

    public User register(String username, String password, String roleStr, String profession) {
        Role role = Role.valueOf(roleStr.toUpperCase());
        User user = new User(username, password, role, profession);
        repo.save(user);
        return user;
    }

    public User login(String username, String password) {
        User user = repo.findByUsername(username);
        if (user == null) return null;
        return user.getPassword().equals(password) ? user : null;
    }

    public User findById(Long id) {
        return repo.findById(id);
    }
}
