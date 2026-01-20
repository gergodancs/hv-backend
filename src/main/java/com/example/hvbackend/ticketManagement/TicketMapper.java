package com.example.hvbackend.ticketManagement;

import com.example.hvbackend.dto.TicketCreateDTO;
import com.example.hvbackend.dto.TicketDTO;
import com.example.hvbackend.ticketManagement.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "building.id", source = "buildingId") // Long -> Building.id
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true) // A PrePersist megoldja, vagy manuálisan beállítod
    @Mapping(target = "createdAt", ignore = true)
    Ticket toEntity(TicketCreateDTO ticketCreateDTO);

    @Mapping(target = "buildingId", source = "building.id")
    @Mapping(target = "mechanicId", source = "mechanic.id")
    TicketDTO toDto(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(TicketDTO ticketDTO, @MappingTarget Ticket existingTicket);

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
