package com.example.hvbackend.buildingManagement;


import com.example.hvbackend.dto.BuildingCreateDTO;
import com.example.hvbackend.dto.BuildingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    BuildingDTO toDto(Building building);

    Building toEntity(BuildingDTO buildingDTO);

    @Mapping(target = "id", ignore = true)
    Building toEntity(BuildingCreateDTO buildingCreateDTO);

    // LocalDateTime -> OffsetDateTime (DTO-ba kifelé)
    default OffsetDateTime mapToOffset(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }

    // OffsetDateTime -> LocalDateTime (Entitásba befelé)
    default LocalDateTime mapToLocal(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toLocalDateTime();
    }

    // --- JSON NULLABLE SEGÉDEK ---
    default <T> T map(JsonNullable<T> value) {
        return value == null || !value.isPresent() ? null : value.get();
    }

    default <T> JsonNullable<T> mapToNullable(T value) {
        return JsonNullable.of(value);
    }

}
