package businfo.bus.Repository;

import businfo.bus.redis.RedisFindBusDto;
import org.springframework.data.repository.CrudRepository;


public interface RedisCacheRepository extends CrudRepository<RedisFindBusDto, String> {
}
