package br.com.digitaltwin.sensores.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "readings")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id", nullable = false, length = 100)
    private String sensorId;

    @Column(nullable = false, length = 50)
    private String metric;

    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal value;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(name = "timestamp_utc", nullable = false)
    private LocalDateTime timestampUtc;

    // ---------------- GETTERS e SETTERS ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    @JsonProperty("timestamp")
    public LocalDateTime getTimestampUtc() { return timestampUtc; }
    public void setTimestampUtc(LocalDateTime timestampUtc) { this.timestampUtc = timestampUtc; }

    // Expor valor numérico como Double no JSON (sem alterar o tipo no banco)
    @JsonProperty("value")
    public Double getValueAsDouble() {
        return value != null ? value.doubleValue() : null;
    }

    @JsonProperty("value")
    public void setValueFromDouble(Double v) {
        this.value = (v != null) ? BigDecimal.valueOf(v) : null;
    }
}
