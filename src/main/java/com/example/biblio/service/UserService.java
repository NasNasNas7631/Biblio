package com.example.biblio.service;

import com.example.biblio.obj.Role;
import com.example.biblio.obj.User;
import com.example.biblio.repo.RoleRepository;
import com.example.biblio.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public User save_user(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }
    public List<User> searchUsers(String username) {
        return userRepository.search(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> listAll(String keyword) {
        if (keyword != null) {
            return userRepository.search(keyword);
        }
        return userRepository.findAll();
    }

    public List<User> findByColumn(String keyword) {
        return userRepository.findListusername(keyword);
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
