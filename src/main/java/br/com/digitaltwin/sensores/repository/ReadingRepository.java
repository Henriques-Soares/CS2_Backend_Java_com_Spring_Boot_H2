package br.com.digitaltwin.sensores.repository;

import br.com.digitaltwin.sensores.model.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading, Long> {

    // LISTA TUDO ordenando por timestampUtc (corrige o nome antigo 'timestamp')
    List<Reading> findAllByOrderByTimestampUtcAsc();

    // Lista por sensorId ordenando por timestampUtc (corrige o nome antigo)
    List<Reading> findBySensorIdOrderByTimestampUtcAsc(String sensorId);

    // Última leitura de um sensor (útil em dashboards)
    Optional<Reading> findTopBySensorIdOrderByTimestampUtcDesc(String sensorId);

    // Exemplo equivalente via JPQL (opcional)
    @Query("SELECT r FROM Reading r WHERE r.sensorId = :sensorId ORDER BY r.timestampUtc ASC")
    List<Reading> findAllAsc(String sensorId);
}
