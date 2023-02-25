package com.mahendrafajar.dansmultipro.services;

import com.mahendrafajar.dansmultipro.models.Role;
import com.mahendrafajar.dansmultipro.models.User;
import com.mahendrafajar.dansmultipro.repositories.RoleRepository;
import com.mahendrafajar.dansmultipro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username with username %s found", username)));
    }

    public User signUp(User user){
        boolean isUserExists = userRepository.findByUsername(user.getUsername()).isPresent();
        if(isUserExists){
            throw new RuntimeException(String.format("User with username %s already exists", user.getUsername()));
        }

        String passwordEncoded = passwordEncoder.encode(user.getPassword());

        Role role = roleRepository.findByName("user").orElseThrow(() -> new RuntimeException("Role user not found"));

        user.setPassword(passwordEncoded);
        user.setRole(role);
        user.setCreatedAt(new Date());

        return userRepository.save(user);
    }
}
