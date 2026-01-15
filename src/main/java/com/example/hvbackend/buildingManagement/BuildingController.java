package com.example.hvbackend.buildingManagement;

import com.example.hvbackend.api.BuildingsApi;
import com.example.hvbackend.dto.BuildingCreateDTO;
import com.example.hvbackend.dto.BuildingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuildingController implements BuildingsApi {

    private final BuildingService buildingService;

    @Override
    public ResponseEntity<List<BuildingDTO>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

    @Override
    public ResponseEntity<BuildingDTO> createBuilding(BuildingCreateDTO buildingDTO) {
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED)
                .body(buildingService.createBuilding(buildingDTO));
    }

    @Override
    public ResponseEntity<BuildingDTO> updateBuildingById(Long id, BuildingDTO buildingDTO) {
        return ResponseEntity.ok(buildingService.updateBuilding(id, buildingDTO));
    }

    @Override
    public ResponseEntity<BuildingDTO> getBuildingById(Long id) {
        return ResponseEntity.ok(buildingService.getBuildingById(id));
    }

    @Override
    public ResponseEntity<Void> deleteBuildingById(Long id) {
        buildingService.deleteBuildingById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
