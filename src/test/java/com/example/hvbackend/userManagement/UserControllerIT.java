package com.example.hvbackend.userManagement;

import com.example.hvbackend.dto.UserCreateDTO;
import com.example.hvbackend.dto.UserRole;
import com.example.hvbackend.userManagement.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Elindítja a teljes Spring kontextust
@AutoConfigureMockMvc // Konfigurálja a MockMvc-t (HTTP szimuláció)
@ActiveProfiles("test") // Az application-test.yml beállításait fogja használni
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired // Ez hiányzott!
    private UserRepository userRepository;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // Minden teszt előtt töröljük a felhasználókat, hogy tiszta legyen a környezet
        userRepository.deleteAll();
    }

    @Test
    void createUser_ShouldReturnCreatedAndSavedUser() throws Exception {
        // 1. Arrange: Összerakjuk a kérést (JSON)
        UserCreateDTO request = new UserCreateDTO()
                .username("it_user")
                .email("it@teszt.hu")
                .role(UserRole.USER)
                .password("Secret123!");

        // 2. Act & Assert: Meghívjuk az API-t és rögtön ellenőrizzük az eredményt
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Elvárjuk a 201-es kódot
                .andExpect(jsonPath("$.username").value("it_user"))
                .andExpect(jsonPath("$.id").exists()); // Az adatbázis adott neki ID-t
    }

    @Test
    @DisplayName("Regisztráció már létező emaillel - BusinessException kód ellenőrzése")
    void createUser_DuplicateEmail_ShouldReturnProperErrorCode() throws Exception {
        // 1. Elmentünk egy felhasználót, hogy legyen ütközés
        userRepository.save(User.builder()
                .username("existingUser")
                .email("conflict@test.com")
                .password("password")
                .build());

        // 2. Új kérés ugyanazzal az emaillel
        UserCreateDTO duplicateRequest = new UserCreateDTO();
        duplicateRequest.setUsername("newUser");
        duplicateRequest.setEmail("conflict@test.com");
        duplicateRequest.setPassword("secret");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateRequest)))
                .andExpect(status().isBadRequest())
                // Ellenőrizzük a YAML-ben definiált mezőket!
                .andExpect(jsonPath("$.code").value("EMAIL_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message").value("Ez az e-mail cím már foglalt."))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}