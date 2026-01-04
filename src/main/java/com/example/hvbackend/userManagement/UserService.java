package com.example.hvbackend.userManagement;

import com.example.hvbackend.dto.UserCreateDTO;
import com.example.hvbackend.dto.UserDTO;
import com.example.hvbackend.userManagement.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDTO createNewUser(UserCreateDTO createDTO) {
        User user = userMapper.toEntity(createDTO);

        // Itt történik a varázslat
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}

