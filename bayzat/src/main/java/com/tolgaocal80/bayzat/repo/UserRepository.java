package com.tolgaocal80.bayzat.repo;


import com.tolgaocal80.bayzat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndActiveTrue(String username);

}
