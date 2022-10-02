package com.tolgaocal80.bayzat.config;

import com.tolgaocal80.bayzat.dto.UserDTO;
import com.tolgaocal80.bayzat.entity.Role;
import com.tolgaocal80.bayzat.entity.User;
import com.tolgaocal80.bayzat.repo.RoleRepository;
import com.tolgaocal80.bayzat.repo.UserRepository;
import com.tolgaocal80.bayzat.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;


// Implements ApplicationRunner, will run at start to load roles to role table
// etc. etc
@Component
public class PreDataLoader implements ApplicationRunner {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    private RoleRepository roleRepository;
    private final UserService userService;

    public PreDataLoader(RoleRepository roleRepository, UserService userService){
        this.roleRepository = roleRepository;
        this.userService = userService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Adds ROLE_USER and ROLE_ADMIN to the database when app runs (if not exists this entries on ROLE table)

        var userRoleExists = roleRepository.existsByName(ROLE_USER);

        if(!userRoleExists){
            var userRole = new Role();
            userRole.setName(ROLE_USER);
            roleRepository.save(userRole);

            // This will add a user who has an ADMIN_ROLE so, tester will be able to login as admin.
            UserDTO adminUserDTO = new UserDTO();
            adminUserDTO.setUsername("user1");
            adminUserDTO.setPassword("admin");
            userService.save(adminUserDTO);
        }

        var adminRoleExists = roleRepository.existsByName(ROLE_ADMIN);

        if(!adminRoleExists){
            var adminRole = new Role();
            adminRole.setName(ROLE_ADMIN);
            roleRepository.save(adminRole);

            // This will add a user who has an USER_ROLE so, tester will be able to login as user.
            UserDTO adminUserDTO = new UserDTO();
            adminUserDTO.setUsername("admin1");
            adminUserDTO.setPassword("admin");
            userService.save(adminUserDTO);
        }



    }


}
