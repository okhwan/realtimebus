package businfo.bus.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Id;

@Getter
@Setter
@RedisHash("session")
public class RedisSession {

    @Id
    private String id;
    private String session;

}
