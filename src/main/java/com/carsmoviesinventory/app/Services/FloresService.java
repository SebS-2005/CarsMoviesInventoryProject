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
        return ResponseEntity.ok(Collections.singletonMap("Flor", Flor.get()));
    }


    public ResponseEntity<?> getFloresByName(String FloresName, Pageable pageable) {
        Page<FloresEntities> Flores = FloresRepository.findAllByFloresNameContaining(Florname, pageable);
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

    public ResponseEntity<?> addMovie(FloresEntities FlorToAdd) {
        Page<FloresEntities> Flor = FloresRepository.findAllByFloresNameContaining(
                movieToAdd.getFloresName(),
                Pageable.unpaged());
        if (movie.getTotalElements() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Movie already exists with %d coincidences.", movie.getTotalElements())), HttpStatus.CONFLICT);
        } else {
            CarsMoviesEntity savedMovie = carsMoviesRepository.save(movieToAdd);
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Added Movie with ID %s", savedMovie.getId())), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> updateMovie(UUID id, CarsMoviesEntity movieToUpdate) {
        Optional<CarsMoviesEntity> movie = carsMoviesRepository.findById(id);
        if (movie.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Movie with ID %s not found.", id)), HttpStatus.NOT_FOUND);
        }
        CarsMoviesEntity existingMovie = movie.get();

        existingMovie.setCarMovieName(movieToUpdate.getCarMovieName());
        existingMovie.setCarMovieYear(movieToUpdate.getCarMovieYear());
        existingMovie.setDuration(movieToUpdate.getDuration());

        carsMoviesRepository.save(existingMovie);

        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Updated Movie with ID %s", existingMovie.getId())));
    }

    public ResponseEntity<?> deleteMovie(UUID id) {
        Optional<CarsMoviesEntity> movie = carsMoviesRepository.findById(id);
        if (movie.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Movie with ID %s doesn't exist.", id)),HttpStatus.NOT_FOUND);
        }
        carsMoviesRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Deleted Movie with ID %s", id)));
    }

}