package businfo.bus.Repository;

import businfo.bus.entity.StationNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationNumberRepository extends JpaRepository<StationNumber, Long> {

    List<StationNumber> findByStationNameLike(String stationName);

    @Query(value = "SELECT station_name FROM station_number", nativeQuery = true)
    List<String> selectStationName();

    @Query(value = "SELECT station_id FROM station_number", nativeQuery = true)
    List<Long> selectStationNumber();

    StationNumber findByStationNumber(Long stationNumber);

    StationNumber findByStationId(Long stationId);


}
