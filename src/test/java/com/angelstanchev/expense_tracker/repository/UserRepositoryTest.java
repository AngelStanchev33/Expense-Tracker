package com.angelstanchev.expense_tracker.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.angelstanchev.expense_tracker.model.entity.UserEntity;

import com.angelstanchev.expense_tracker.model.entity.UserRoleEntity;
import com.angelstanchev.expense_tracker.model.entity.enums.UserRoleEnum;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    @Transactional
    void dbInit() {
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRoleName(UserRoleEnum.valueOf("ADMIN"));

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRoleName(UserRoleEnum.valueOf("USER"));

        entityManager.persist(adminRole);
        entityManager.persist(userRole);
        entityManager.flush();

        UserEntity user = new UserEntity();
        user.setEmail("AngelStanchev33@gmail.com");
        user.setFirstName("Angel");
        user.setLastName("Stanchev");
        user.setPassword(passwordEncoder.encode("NiceSpice123"));
        user.setImageUrl("null");
        user.setActive(true);
        user.setRoles(List.of(adminRole, userRole));

        entityManager.persist(user);
    }

    @Test
    void findByEmail_ShouldReturnIsPresent() {
        Optional<UserEntity> user = userRepository.findByEmail("AngelStanchev33@gmail.com");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals("Angel", user.get().getFirstName());
        Assertions.assertEquals("Stanchev", user.get().getLastName());
        Assertions.assertEquals("AngelStanchev33@gmail.com", user.get().getEmail());
    }

    @Test
    void findByEmail_ShouldReturnEmpty() {
        Optional<UserEntity> user = userRepository.findByEmail("Angel33@gmail.com");

        Assertions.assertTrue(user.isEmpty());
    }


}
