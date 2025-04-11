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
    private UUID id;

    @JsonProperty("floresName")
    @NotBlank(message = "Flores names is required")
    @Size(min = 3, max = 100, message = "Flores name must be between 3 and 100 characters")
    private String floresName;

    @JsonProperty("floresColor")
    @NotBlank(message = "Color is required")
    @Size(min = 3, max = 100, message = "Flores Color must be between 3 and 100 characters")
    private String floresColor;

    @JsonProperty("tamano")
    @NotNull(message = "Tamano is required")
    @Size(min = 3, max = 100, message = "Flores size no")
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

    public void setFloresName(String FloresName) {
        this.floresName = FloresName;
    }

    public String getFloresColor() {
        return floresColor;
    }

    public void setFloresColor(String FloresColor) {
        this.floresColor = FloresColor;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String Tamano) {
        this.tamano = Tamano;
    }

}
