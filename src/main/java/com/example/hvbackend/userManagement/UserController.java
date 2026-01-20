package com.example.hvbackend.userManagement;

import com.example.hvbackend.api.UsersApi;
import com.example.hvbackend.dto.UserCreateDTO;
import com.example.hvbackend.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserCreateDTO userCreateDTO) {
        UserDTO savedUser = userService.createNewUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
