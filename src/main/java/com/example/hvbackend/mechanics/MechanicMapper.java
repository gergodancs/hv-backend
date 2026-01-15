package com.example.hvbackend.mechanics;

import com.example.hvbackend.dto.MechanicCreateDTO;
import com.example.hvbackend.dto.MechanicDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface MechanicMapper {

    MechanicDTO toDto(Mechanic mechanic);

    Mechanic toEntity(MechanicDTO mechanicDTO);

    @Mapping(target = "id", ignore = true)
    Mechanic toEntity(MechanicCreateDTO mechanicCreateDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(MechanicDTO mechanicDTO, @MappingTarget Mechanic existingMechanic);

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
