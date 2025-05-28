package com.carsmoviesinventory.app.Entities;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "FLORES_ENTITI")
@AllArgsConstructor
@NoArgsConstructor
public class FloresEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @JsonProperty("floresName")
    @Size(min = 3, max = 100, message = "Flores name must be between 3 and 100 characters")
    @Column(name = "flores_name", nullable = false, length = 100)
    private String floresName;

    @JsonProperty("floresColor")
    @Size(min = 3, max = 100, message = "Flores color must be between 3 and 100 characters")
    @Column(name = "flores_color", nullable = false, length = 100)
    private String floresColor;

    @JsonProperty("tamano")
    @Size(min = 3, max = 100, message = "Flores size must be between 3 and 100 characters")
    @Column(name = "tamano", nullable = false, length = 100)
    private String tamano;

    @PrePersist
    public void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "FloresEntities{" +
                "id=" + id +
                ", floresName='" + floresName + '\'' +
                ", floresColor='" + floresColor + '\'' +
                ", tamano='" + tamano + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getFloresName() {
        return floresName;
    }

    public void setFloresName(String floresName) {
        this.floresName = floresName;
    }

    public String getFloresColor() {
        return floresColor;
    }

    public void setFloresColor(String floresColor) {
        this.floresColor = floresColor;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }
}
