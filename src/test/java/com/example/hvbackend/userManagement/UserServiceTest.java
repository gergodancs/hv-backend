package com.example.hvbackend.userManagement;

import com.example.hvbackend.dto.UserCreateDTO;
import com.example.hvbackend.dto.UserDTO;
import com.example.hvbackend.dto.UserRole;
import com.example.hvbackend.userManagement.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createNewUser_ShouldReturnSavedUserDTO() {
        // 1. ARRANGE (Adatok előkészítése)
        UserCreateDTO inputDto = createUserDTO("teszt_elek", "teszt@teszt.com", UserRole.USER, "teszt123");

        User entityBeforeSave = new User();
        entityBeforeSave.setUsername("teszt_elek");
        entityBeforeSave.setPassword("teszt123");

        User savedEntity = User.builder()
                .id(1L)
                .username("teszt_elek")
                .role(com.example.hvbackend.userManagement.entity.UserRole.USER)
                .build();

        UserDTO outputDto = new UserDTO();
        outputDto.setId(1L);
        outputDto.setUsername("teszt_elek");

        // Mock viselkedések beállítása
        when(userMapper.toEntity(any(UserCreateDTO.class))).thenReturn(entityBeforeSave);
        when(userRepository.save(any(User.class))).thenReturn(savedEntity);
        when(userMapper.toDto(any(User.class))).thenReturn(outputDto);

        // 2. ACT (A tényleges hívás)
        UserDTO result = userService.createNewUser(inputDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
// Ellenőrizzük, hogy a jelszót NEM sima szövegként mentettük el
        assertThat(capturedUser.getPassword()).isNotEqualTo("teszt123");

        // 3. ASSERT (Ellenőrzés)
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("teszt_elek");

        // Ellenőrizzük, hogy mindenki lefutott-e egyszer
        verify(userRepository, times(1)).save(any());
    }

    UserCreateDTO createUserDTO(String name, String email, UserRole role, String password){

        return new UserCreateDTO()
                .username(name)
                .email(email)
                .role(role)
                .password(password);
    }
}
