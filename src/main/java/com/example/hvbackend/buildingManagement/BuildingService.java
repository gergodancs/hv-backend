package com.example.hvbackend.buildingManagement;


import com.example.hvbackend.dto.BuildingCreateDTO;
import com.example.hvbackend.dto.BuildingDTO;
import com.example.hvbackend.exception.BusinessException;
import com.example.hvbackend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;


    public List<BuildingDTO> getAllBuildings() {
        return buildingRepository.findAll().stream()
                .map(buildingMapper::toDto)
                .toList();
    }

    public BuildingDTO getBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.BUILDING_NOT_FOUND));

        return buildingMapper.toDto(building);
    }

    public BuildingDTO updateBuilding(Long id, BuildingDTO buildingDTO) {
        Building existingBuilding = buildingRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.BUILDING_NOT_FOUND));

        buildingMapper.updateEntityFromDto(buildingDTO, existingBuilding);

        return buildingMapper.toDto(buildingRepository.save(existingBuilding));
    }

    public BuildingDTO createBuilding(BuildingCreateDTO buildingDTO) {
        return buildingMapper.toDto(buildingRepository.save(buildingMapper.toEntity(buildingDTO)));
    }

    public void deleteBuildingById(Long id) {
        buildingRepository.deleteById(id);
    }
}
