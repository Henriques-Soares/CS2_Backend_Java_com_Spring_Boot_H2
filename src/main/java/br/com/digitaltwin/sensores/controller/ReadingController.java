package br.com.digitaltwin.sensores.controller;

import br.com.digitaltwin.sensores.model.Reading;
import br.com.digitaltwin.sensores.repository.ReadingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/readings")
public class ReadingController {

    private final ReadingRepository repository;

    public ReadingController(ReadingRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Reading create(@RequestBody Reading reading) {
        return repository.save(reading);
    }

    @GetMapping
    public List<Reading> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{sensorId}")
    public List<Reading> bySensor(@PathVariable String sensorId) {
        return repository.findBySensorId(sensorId);
    }
}
