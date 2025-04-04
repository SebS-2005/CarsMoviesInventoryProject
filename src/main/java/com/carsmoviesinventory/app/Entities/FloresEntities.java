package com.carsmoviesinventory.app.Entities;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FloresEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("FloresName")
    @NotBlank(message = "Flores names is required")
    @Size(min = 3, max = 100, message = "Flores name must be between 3 and 100 characters")
    private String FloresName;

    @JsonProperty("FloresColor")
    @NotBlank(message = "Color is required")
    @Size(min = 3, max = 100, message = "Flores Color must be between 3 and 100 characters")
    private String FloresColor;

    @JsonProperty("Tamano")
    @NotNull(message = "Tamano is required")
    @Size(min = 3, max = 100, message = "Flores size no")
    private String Tamano;

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
                ", FloresName='" + FloresName + '\'' +
                ", FloresColor='" + FloresColor + '\'' +
                ", Tamano=" + Tamano +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getFloresName() {
        return FloresName;
    }

    public void setFloresName(String FloresName) {
        this.FloresName = FloresName;
    }

    public String getFloresColor() {
        return FloresColor;
    }

    public void setFloresColor(String FloresColor) {
        this.FloresColor = FloresColor;
    }

    public String getTamano() {
        return Tamano;
    }

    public void setTamano(Integer Tamano) {
        this.Tamano = Tamano;
    }

}
