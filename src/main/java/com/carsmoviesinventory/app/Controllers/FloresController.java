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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/flores")
@Validated
public class FloresController{
    private final FloresService floresService;
    public FloresController(FloresService floresService) {
        this.floresService = floresService;
    }
    @GetMapping
    public ResponseEntity<?> getAllFlores(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "floresName,asc") String[] sort) {
        try {
                Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
                return floresService.getAllFlores(pageable);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid sorting direction. Use 'asc' or 'desc'.");
            }
        }
    private List<Sort.Order> parseSort(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        
        if (sort.length % 2 == 0) {
            for (int i = 0; i < sort.length; i += 2) {
                String property = sort[i];
                String direction = sort[i+1].toLowerCase();
                
                if (!Arrays.asList("asc", "desc").contains(direction)) {
                    throw new IllegalArgumentException("Invalid sort direction. Use 'asc' or 'desc'.");
                }
                
                orders.add(new Sort.Order(Sort.Direction.fromString(direction), property));
            }
        } else if (sort.length == 1) {
            orders.add(new Sort.Order(Sort.Direction.ASC, sort[0]));
        } else {
            throw new IllegalArgumentException("Sort parameter must have property-direction pairs.");
        }
        
        return orders;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getFloresById (@PathVariable UUID id){
        return floresService.getFloresById(id);
    }
    @GetMapping("/search")
    public ResponseEntity<?> getFloresByName(
            @RequestParam String FloresName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "floresName,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return floresService.getFloresByName(FloresName, pageable);
    }
    @PostMapping
    public ResponseEntity<?> insertFlor(@Valid @RequestBody FloresEntities floresEntities){
        return floresService.addFlor(floresEntities);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlor(@PathVariable UUID id, @Valid @RequestBody FloresEntities floresEntities){
        return floresService.updateFlor(id,floresEntities);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlor(@PathVariable UUID id){
        return floresService.deleteFlor(id);
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}