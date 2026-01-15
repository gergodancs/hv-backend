package com.example.hvbackend.mechanics;


import com.example.hvbackend.dto.MechanicCreateDTO;
import com.example.hvbackend.dto.MechanicDTO;
import com.example.hvbackend.exception.BusinessException;
import com.example.hvbackend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    private final MechanicMapper mechanicMapper;

    public List<MechanicDTO> findAllMechanics() {
        return mechanicRepository.findAll().stream()
                .map(mechanicMapper::toDto)
                .toList();
    }

    public MechanicDTO getMechanicById(Long id) {
        Mechanic mechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MECHANIC_NOT_FOUND));

        return mechanicMapper.toDto(mechanic);
    }

    public MechanicDTO createMechanic(MechanicCreateDTO mechanicDTO) {
        return mechanicMapper.toDto(mechanicRepository.save(mechanicMapper.toEntity(mechanicDTO)));
    }

    public MechanicDTO updateMechanic(Long id, MechanicDTO mechanicDTO) {
        Mechanic existingMechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MECHANIC_NOT_FOUND));

        mechanicMapper.updateEntityFromDto(mechanicDTO, existingMechanic);

        return mechanicMapper.toDto(mechanicRepository.save(mechanicMapper.toEntity(mechanicDTO)));
    }

    public void deleteMechanicById(Long id) {
        mechanicRepository.deleteById(id);
    }
}
