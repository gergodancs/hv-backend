package com.example.hvbackend.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // A belső kód most már megegyezik az Enum névvel, vagy annak kisbetűs változatával
    USER_NOT_FOUND("USER_NOT_FOUND", "A kért felhasználó nem található."),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Ez az e-mail cím már foglalt."),
    BUILDING_NOT_FOUND("BUILDING_NOT_FOUND", "A kért épület nem található.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}