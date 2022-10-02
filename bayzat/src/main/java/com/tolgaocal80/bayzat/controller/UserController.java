package com.tolgaocal80.bayzat.controller;

import com.tolgaocal80.bayzat.dto.UserDTO;
import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.User;
import com.tolgaocal80.bayzat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Returns all users, ONLY admins are allowed
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    // Returns user with given name, ONLY admins are allowed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-username")
    public ResponseEntity<User> searchUser(@RequestParam(name = "username") String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    // Returns user with given id, ONLY admins are allowed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable(name = "userId") long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    // Adds user to the database, requires valid UserDTO, ONLY admins are allowed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user){
        return ResponseEntity.ok(userService.save(user));
    }

    // Returns alerts of the user
    @GetMapping("/{userId}/alerts")
    public ResponseEntity<List<Alert>> getUsersAlerts(@PathVariable(name = "userId") long userId){
        return ResponseEntity.ok(userService.getUsersAlerts(userId));
    }


}


