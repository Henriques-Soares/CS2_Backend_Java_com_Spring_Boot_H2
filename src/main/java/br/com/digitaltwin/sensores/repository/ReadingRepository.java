package br.com.digitaltwin.sensores.repository;

import br.com.digitaltwin.sensores.model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingRepository extends JpaRepository<Reading, Long> {

    // Lista por sensor em ordem cronológica (útil para gráficos)
    List<Reading> findBySensorIdOrderByTimestampAsc(String sensorId);

    // Lista geral em ordem cronológica
    List<Reading> findAllByOrderByTimestampAsc();
}
