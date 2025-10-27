package br.com.digitaltwin.sensores.controller;

import br.com.digitaltwin.sensores.model.Reading;
import br.com.digitaltwin.sensores.repository.ReadingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReadingController {

    private final ReadingRepository repository;

    public ReadingController(ReadingRepository repository) {
        this.repository = repository;
    }

    // GET /api/readings  -> todas as leituras, ordenadas por timestampUtc
    @GetMapping("/readings")
    public List<Reading> getAll() {
        return repository.findAllByOrderByTimestampUtcAsc();
    }

    // GET /api/readings/{sensorId} -> leituras de um sensor, ordenadas por timestampUtc
    @GetMapping("/readings/{sensorId}")
    public List<Reading> getBySensor(@PathVariable String sensorId) {
        return repository.findBySensorIdOrderByTimestampUtcAsc(sensorId);
    }

    // POST /api/readings -> cria leitura (se timestampUtc vier null, seta agora em UTC)
    @PostMapping("/readings")
    public ResponseEntity<Reading> create(@RequestBody Reading reading) {
        if (reading.getTimestampUtc() == null) {
            reading.setTimestampUtc(LocalDateTime.now(ZoneOffset.UTC));
        }
        Reading saved = repository.save(reading);
        return ResponseEntity.created(URI.create("/api/readings/" + saved.getId())).body(saved);
    }
}
