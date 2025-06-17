package br.com.digitaltwin.sensores.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorId;
    private Double sensorValue; // Campo agora chama sensorValue
    private LocalDateTime timestamp;

    public Reading() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public Double getSensorValue() { return sensorValue; } // Corrigido aqui
    public void setSensorValue(Double sensorValue) { this.sensorValue = sensorValue; } // Corrigido aqui

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
