package com.example.hvbackend.buildingManagement;


import com.example.hvbackend.dto.BuildingCreateDTO;
import com.example.hvbackend.dto.BuildingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    BuildingDTO toDto(Building building);

    // Fontos az ID ignorálása, ha DTO-ból csinálunk entitást,
    // hogy ne tudjanak véletlenül ID-t injektálni mentésnél
    @Mapping(target = "id", ignore = true)
    Building toEntity(BuildingDTO buildingDTO);

    @Mapping(target = "id", ignore = true)
    Building toEntity(BuildingCreateDTO buildingCreateDTO);

    // @MappingTarget jelzi, hogy a meglévőt módosítjuk
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(BuildingDTO buildingDTO, @MappingTarget Building existingBuilding);

    // Dátum konverziók
    default OffsetDateTime mapToOffset(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }

    default LocalDateTime mapToLocal(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toLocalDateTime();
    }

    // JSON NULLABLE kezelés
    // Ezzel a MapStruct tudni fogja, hogyan szedje ki az értéket a JsonNullable-ből
    default <T> T mapNullable(JsonNullable<T> value) {
        return value == null ? null : value.orElse(null);
    }
}
