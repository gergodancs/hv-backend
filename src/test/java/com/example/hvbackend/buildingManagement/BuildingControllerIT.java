package com.example.hvbackend.buildingManagement;


import com.example.hvbackend.dto.BuildingCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
public class BuildingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BuildingRepository buildingRepository;

    @BeforeEach
    void setup(){
        buildingRepository.deleteAll();
    }

    @Test
    void createBuilding_ShouldReturnCreatedAndSavedBuilding() throws Exception {

        BuildingCreateDTO request = new BuildingCreateDTO()
                .name("Test Building")
                .address("Test Address")
                .buildingType(BuildingCreateDTO.BuildingTypeEnum.INDUSTRIAL)
                .city("Wien")
                .postCode(1140L);

        mockMvc.perform(post("/api/buildings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Building"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.buildingType").value("INDUSTRIAL"))
                .andExpect(jsonPath("$.city").value("Wien"))
                .andExpect(jsonPath("$.postCode").value(1140L));

        assert buildingRepository.count() == 1;
        assert buildingRepository.findAll().get(0).getName().equals("Test Building");
    }

}
