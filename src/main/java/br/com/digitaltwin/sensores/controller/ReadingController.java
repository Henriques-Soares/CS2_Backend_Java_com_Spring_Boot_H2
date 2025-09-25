package br.com.digitaltwin.sensores.controller;

import br.com.digitaltwin.sensores.model.Reading;
import br.com.digitaltwin.sensores.repository.ReadingRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/readings")
public class ReadingController {

    private final ReadingRepository repository;

    public ReadingController(ReadingRepository repository) {
        this.repository = repository;
    }

    // GET /api/readings -> todas as leituras ordenadas (ASC) por timestamp
    @GetMapping
    public List<Reading> listAll() {
        return repository.findAllByOrderByTimestampAsc();
    }

    // GET /api/readings/sensor/{sensorId} -> leituras do sensor ordenadas (ASC)
    @GetMapping("/sensor/{sensorId}")
    public List<Reading> listBySensor(@PathVariable String sensorId) {
        return repository.findBySensorIdOrderByTimestampAsc(sensorId);
    }

    // POST /api/readings
    // Body esperado: { "sensorId": "S-001", "value": 23.5, "timestamp": "2025-09-24T21:33:00" (opcional) }
    @PostMapping
    public Reading create(@RequestBody Reading reading) {
        if (reading.getTimestamp() == null) {
            reading.setTimestamp(LocalDateTime.now());
        }
        return repository.save(reading);
    }
}
