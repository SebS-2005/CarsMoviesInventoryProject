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

    private final FloresRepository FloresRepository;

    public FloresService(FloresRepository FloresRepository) {
        this.FloresRepository = FloresRepository;
    }

    public ResponseEntity<?> getAllFlores(Pageable pageable) {
        Page<FloresEntities> Flores = FloresRepository.findAll(pageable);
        return getResponseEntity(Flores);
    }

    public ResponseEntity<?> getFloresById(UUID id) {
        Optional<FloresEntities> Flores = FloresRepository.findById(id);
        if (Flores.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Status", String.format("Flor with ID %s not found.", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(Collections.singletonMap("Flor", Flores.get()));
    }


    public ResponseEntity<?> getFloresByName(String FloresName, Pageable pageable) {
        Page<FloresEntities> Flores = FloresRepository.findAllByFloresNameContaining(FloresName, pageable);
        return getResponseEntity(Flores);
    }

    private ResponseEntity<?> getResponseEntity(Page<FloresEntities> Flores) {
        Map<String, Object> response = new HashMap<>();
        response.put("TotalElements", Flores.getTotalElements());
        response.put("TotalPages", Flores.getTotalPages());
        response.put("CurrentPage", Flores.getNumber());
        response.put("NumberOfElements", Flores.getNumberOfElements());
        response.put("Flores", Flores.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addFlor(FloresEntities FlorToAdd) {
        Page<FloresEntities> Flor = FloresRepository.findAllByFloresNameContaining(
                FlorToAdd.getFloresName(),
                Pageable.unpaged());
        if (Flor.getTotalElements() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Flor already exists with %d coincidences.", Flor.getTotalElements())), HttpStatus.CONFLICT);
        } else {
            FloresEntities savedFlor = FloresRepository.save(FlorToAdd);
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Added Flor with ID %s", savedFlor.getId())), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> updateFlor(UUID id, FloresEntities FlorToUpdate) {
        Optional<FloresEntities> Flor = FloresRepository.findById(id);
        if (Flor.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Flor with ID %s not found.", id)), HttpStatus.NOT_FOUND);
        }
        FloresEntities existingFlor = Flor.get();

        existingFlor.setFloresName(FlorToUpdate.getFloresName());
        existingFlor.setFloresColor(FlorToUpdate.getFloresColor());
        existingFlor.setTamano(FlorToUpdate.getTamano());

        FloresRepository.save(existingFlor);

        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Updated Flor with ID %s", existingFlor.getId())));
    }

    public ResponseEntity<?> deleteFlor(UUID id) {
        Optional<FloresEntities> Flor = FloresRepository.findById(id);
        if (Flor.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Flor with ID %s doesn't exist.", id)),HttpStatus.NOT_FOUND);
        }
        FloresRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Deleted Flor with ID %s", id)));
    }

}