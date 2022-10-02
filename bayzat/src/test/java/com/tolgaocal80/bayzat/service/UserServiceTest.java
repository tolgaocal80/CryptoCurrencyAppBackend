package com.tolgaocal80.bayzat.service;

import com.tolgaocal80.bayzat.config.PasswordConfig;
import com.tolgaocal80.bayzat.dto.UserDTO;
import com.tolgaocal80.bayzat.entity.Role;
import com.tolgaocal80.bayzat.entity.User;
import com.tolgaocal80.bayzat.repo.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Import({UserService.class, PasswordConfig.class})
@DataJpaTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @BeforeEach
    public void initializeRoles(){
        var userRoleExists = roleRepository.existsByName(ROLE_USER);

        if(!userRoleExists){
            var userRole = new Role();
            userRole.setName(ROLE_USER);
            roleRepository.save(userRole);
        }

        var adminRoleExists = roleRepository.existsByName(ROLE_ADMIN);

        if(!adminRoleExists){
            var adminRole = new Role();
            adminRole.setName(ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }



    @Test
    public void shouldSaveUserAsUserRole(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user-test");
        userDTO.setPassword("admin");
        User savedUser = userService.save(userDTO);

        User retrievedUser = userService.findById(savedUser.getId());

        Role role = roleRepository.findByName(ROLE_USER).get();

        assertThat(role).isIn(retrievedUser.getRoles());
    }

    // If username includes "admin" user role will be admin otherwise will be user.
    // for testing purpose.
    @Test
    public void shouldSaveUserAsAdminRole(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin-test");
        userDTO.setPassword("admin");
        User savedUser = userService.save(userDTO);

        User retrievedUser = userService.findById(savedUser.getId());

        Role role = roleRepository.findByName(ROLE_ADMIN).get();

        assertThat(role).isIn(retrievedUser.getRoles());
    }




}
