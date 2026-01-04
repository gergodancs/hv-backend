package com.example.hvbackend.userManagement;

import com.example.hvbackend.dto.UserCreateDTO;
import com.example.hvbackend.dto.UserDTO;
import com.example.hvbackend.userManagement.entity.User;
import com.example.hvbackend.userManagement.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Entitás -> DTO (Lekérdezéskor)
    // Itt a MapStruct látja: User.role (Entity) -> UserDTO.role (DTO)
    // Automatikusan meghívja az enum konvertert.
    UserDTO toDto(User user);

    // DTO -> Entitás (Frissítéskor)
    User toEntity(UserDTO userDto);

    // DTO -> Entitás (Létrehozáskor)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateDTO userCreateDto);

    // --- ENUM KONVERZIÓ ---
    // Mivel a két Enum neve megegyezik, de a csomagjuk nem,
    // a MapStruct-nak megmutatjuk, hogyan váltson köztük.
    // Ha az értékek (ADMIN, USER, stb.) ugyanazok, nem kell több annotáció.
    UserRole mapRole(com.example.hvbackend.dto.UserRole dtoRole);

    com.example.hvbackend.dto.UserRole mapRoleToDto(UserRole entityRole);

    // --- JSON NULLABLE SEGÉDEK ---
    default <T> T map(JsonNullable<T> value) {
        return value == null || !value.isPresent() ? null : value.get();
    }

    default <T> JsonNullable<T> mapToNullable(T value) {
        return JsonNullable.of(value);
    }
}