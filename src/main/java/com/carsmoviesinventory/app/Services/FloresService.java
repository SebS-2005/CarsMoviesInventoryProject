package com.carsmoviesinventory.app.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carsmoviesinventory.app.Repositories.FloresRepository;
import com.carsmoviesinventory.app.Entities.FloresEntities;

import java.util.*;

@Service
public class FloresService{

    private final FloresRepository floresRepository;

    public FloresService(FloresRepository floresRepository) {
        this.floresRepository = floresRepository;
    }

    public ResponseEntity<?> getAllFlores(Pageable pageable) {
        Page<FloresEntities> flores = floresRepository.findAll(pageable);
        return getResponseEntity(flores);
    }

    public ResponseEntity<?> getFloresById(UUID id) {
        Optional<FloresEntities> flores = floresRepository.findById(id);
        if (flores.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Status", String.format("Flor with ID %s not found.", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(Collections.singletonMap("Flor", flores.get()));
    }


    public ResponseEntity<?> getFloresByName(String FloresName, Pageable pageable) {
        Page<FloresEntities> flores = floresRepository.findAllByFloresNameContaining(FloresName, pageable);
        return getResponseEntity(flores);
    }

    private ResponseEntity<?> getResponseEntity(Page<FloresEntities> flores) {
        Map<String, Object> response = new HashMap<>();
        response.put("TotalElements", flores.getTotalElements());
        response.put("TotalPages", flores.getTotalPages());
        response.put("CurrentPage", flores.getNumber());
        response.put("NumberOfElements", flores.getNumberOfElements());
        response.put("Flores", flores.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addFlor(FloresEntities florToAdd) {
        Page<FloresEntities> flor = floresRepository.findAllByFloresNameContaining(
                florToAdd.getFloresName(),
                Pageable.unpaged());
        if (flor.getTotalElements() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Flor already exists with %d coincidences.", flor.getTotalElements())), HttpStatus.CONFLICT);
        } else {
            FloresEntities savedFlor = floresRepository.save(florToAdd);
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Added Flor with ID %s", savedFlor.getId())), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> updateFlor(UUID id, FloresEntities florToUpdate) {
        Optional<FloresEntities> flor = floresRepository.findById(id);
        if (flor.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Flor with ID %s not found.", id)), HttpStatus.NOT_FOUND);
        }
        FloresEntities existingFlor = flor.get();

        existingFlor.setFloresName(florToUpdate.getFloresName());
        existingFlor.setFloresColor(florToUpdate.getFloresColor());
        existingFlor.setTamano(florToUpdate.getTamano());

        floresRepository.save(existingFlor);

        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Updated Flor with ID %s", existingFlor.getId())));
    }

    public ResponseEntity<?> deleteFlor(UUID id) {
        Optional<FloresEntities> flor = floresRepository.findById(id);
        if (flor.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Flor with ID %s doesn't exist.", id)),HttpStatus.NOT_FOUND);
        }
        floresRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Deleted Flor with ID %s", id)));
    }
}