package com.example.hvbackend.userManagement;

import com.example.hvbackend.dto.UserCreateDTO;
import com.example.hvbackend.dto.UserDTO;
import com.example.hvbackend.exception.BusinessException;
import com.example.hvbackend.exception.ErrorCode;
import com.example.hvbackend.userManagement.entity.User;
import jakarta.persistence.EntityNotFoundException;
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

        if (userRepository.existsByEmail(createDTO.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.existsByUsername(createDTO.getUsername())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = userMapper.toEntity(createDTO);
        // Itt történik a varázslat
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public void changeUserEnabledStatus(Long id, boolean newStatus) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.setEnabled(newStatus);
        userRepository.save(user);
    }
}

