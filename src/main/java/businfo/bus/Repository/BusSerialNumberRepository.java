package businfo.bus.Repository;

import businfo.bus.entity.BusSerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusSerialNumberRepository extends JpaRepository<BusSerialNumber, Long> {
}
