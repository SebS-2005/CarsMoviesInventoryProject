package com.carsmoviesinventory.app.Controllers;

import com.carsmoviesinventory.app.Entities.FloresEntities;
import com.carsmoviesinventory.app.Services.FloresService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/Flores")
@Validated
public class FloresController{

    private final FloresService FloresService;

    public FloresController(FloresService FloresService) {
        this.FloresService = FloresService;
    }

    @GetMapping
    public ResponseEntity<?> getAllFlores(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "FloresName,asc") String[] sort) {
        try {
                Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
                return FloresService.getAllFlores(pageable);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid sorting direction. Use 'asc' or 'desc'.");
            }
        }

    private Sort.Order parseSort(String[] sort) {
        if (sort.length < 2) {
            throw new IllegalArgumentException("Sort parameter must have both field and direction (e.g., 'FloresColor,desc').");
        }

        String property = sort[0];
        String direction = sort[1].toLowerCase();

        List<String> validDirections = Arrays.asList("asc", "desc");
        if (!validDirections.contains(direction)) {
            throw new IllegalArgumentException("Invalid sort direction. Use 'asc' or 'desc'.");
        }

        return new Sort.Order(Sort.Direction.fromString(direction), property);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getFloresById (@PathVariable UUID id){
        return FloresService.getFloresById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getFloresByName(
            @RequestParam String FloresName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "FloresName,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return FloresService.getFloresByName(FloresName, pageable);
    }

    @PostMapping
    public ResponseEntity<?> insertFlor(@Valid @RequestBody FloresEntities FloresEntities){
        return FloresService.addFlor(FloresEntities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlor(@PathVariable UUID id, @Valid @RequestBody FloresEntities FloresEntities){
        return FloresService.updateFlor(id,FloresEntities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlor(@PathVariable UUID id){
        return FloresService.deleteFlor(id);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

}
