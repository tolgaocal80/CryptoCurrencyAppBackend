package com.tolgaocal80.bayzat.service;


import com.tolgaocal80.bayzat.dto.MyUserDetails;
import com.tolgaocal80.bayzat.dto.UserDTO;
import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.Role;
import com.tolgaocal80.bayzat.entity.User;
import com.tolgaocal80.bayzat.repo.RoleRepository;
import com.tolgaocal80.bayzat.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public User save(UserDTO userDTO){

        User user = new User();
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUsername());

        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");

        if (optionalRoleAdmin.isPresent() && userDTO.getUsername().contains("admin")){
            user.setRoles(Set.of(optionalRoleAdmin.get()));
        }else{
            optionalRoleUser.ifPresent(role -> user.setRoles(Set.of(optionalRoleUser.get())));
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username){
        var userOpt = userRepository.findByUsernameAndActiveTrue(username);
        return userOpt.orElseThrow( () -> {
            throw new IllegalArgumentException("User not found");
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.findByUsername(username);
        return new MyUserDetails(user);
    }

    public List<Alert> getUsersAlerts(long userId){
        var user = this.findById(userId);
        return user.getAlerts().stream().toList();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id){
        var userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
    }

}
