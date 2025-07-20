package com.angelstanchev.expense_tracker.web;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.angelstanchev.expense_tracker.model.entity.UserEntity;
import com.angelstanchev.expense_tracker.model.entity.UserRoleEntity;
import com.angelstanchev.expense_tracker.model.entity.enums.UserRoleEnum;
import com.angelstanchev.expense_tracker.repository.UserRepository;
import com.angelstanchev.expense_tracker.repository.UserRoleRepository;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @BeforeEach
    void dbInit() {
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRoleName(UserRoleEnum.valueOf("ADMIN"));

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRoleName(UserRoleEnum.valueOf("USER"));

        UserEntity user = new UserEntity();
        user.setEmail("AngelStanchev33@gmail.com");
        user.setFirstName("Angel");
        user.setLastName("Stanchev");
        user.setPassword(passwordEncoder.encode("NiceSpice123"));
        user.setImageUrl("null");
        user.setActive(true);
        user.setRoles(List.of(adminRole, userRole));

        userRoleRepository.saveAll(List.of(userRole, adminRole));
        userRepository.save(user);

    }

    @Test
    void shouldLogin_Successfully() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"AngelStanchev33@gmail.com\",\"password\":\"NiceSpice123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void shouldLogin_UnSuccessful_WrongEmail() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Angel@gmail.com\",\"password\":\"NiceSpice123\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.token").doesNotExist());

    }

    @Test
    void shouldLogin_UnSuccessful_WrongPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"AngelStanchev33@gmail.com\",\"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.token").doesNotExist());
    }

    @Test
    void shouldLogin_Fail_EmptyUsername() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"password\":\"NiceSpice123\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldLogin_Fail_NullUsername() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"NiceSpice123\"}"))
                .andExpect(status().isBadRequest()); // или 400
    }

    @Test
    void shouldLogin_Fail_EmptyPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"AngelStanchev33@gmail.com\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldLogin_Fail_NullPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"AngelStanchev33@gmail.com\"}"))
                .andExpect(status().isBadRequest());
    }
}
