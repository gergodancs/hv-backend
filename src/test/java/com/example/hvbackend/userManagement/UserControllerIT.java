package com.example.hvbackend.userManagement;

import com.example.hvbackend.dto.UserCreateDTO;
import com.example.hvbackend.dto.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper objectMapper; // JSON-ná alakításhoz

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
}