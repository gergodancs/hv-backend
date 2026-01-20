package com.example.hvbackend.mechanics;

import com.example.hvbackend.api.MechanicApi;
import com.example.hvbackend.dto.MechanicCreateDTO;
import com.example.hvbackend.dto.MechanicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MechanicController implements MechanicApi {

    private final MechanicService mechanicService;

    @Override
    public ResponseEntity<List<MechanicDTO>> getAllMechanics() {
        return ResponseEntity.ok(mechanicService.findAllMechanics());
    }

    @Override
    public ResponseEntity<MechanicDTO> getMechanicById(Long id) {
        return ResponseEntity.ok(mechanicService.getMechanicById(id));
    }

    @Override
    public ResponseEntity<MechanicDTO> updateMechanic(Long id, MechanicDTO mechanicDTO) {
        return ResponseEntity.ok(mechanicService.updateMechanic(id, mechanicDTO));
    }

    @Override
    public ResponseEntity<MechanicDTO> createMechanic(MechanicCreateDTO mechanicDTO) {
        return ResponseEntity.ok(mechanicService.createMechanic(mechanicDTO));
    }

    @Override
    public ResponseEntity<Void> deleteMechanicById(Long id) {
        mechanicService.deleteMechanicById(id);
        return ResponseEntity.noContent().build();
    }

}
