package com.tolgaocal80.bayzat.repository;


import com.tolgaocal80.bayzat.entity.User;
import com.tolgaocal80.bayzat.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        User user = new User();
        user.setUsername("test-user-1");
        user.setPassword("admin");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }




}
