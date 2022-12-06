package businfo.bus.Repository;

import businfo.bus.entity.RouteNumber;
import businfo.bus.entity.StationNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteNumberRepository extends JpaRepository<RouteNumber, Long> {
    RouteNumber findByRouteIdAndStationId(String routeId, StationNumber stationId);
    List<RouteNumber> findByRouteIdAndSequenceNumberGreaterThanAndUpDownEquals(String routeId, int sequenceNumber, int upDown);
    List<RouteNumber> findByRouteIdAndSequenceNumberLessThanAndUpDownEquals(String routeId, int sequenceNumber, int upDown);

}
