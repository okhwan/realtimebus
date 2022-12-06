package businfo.bus.mongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LogService {
    private final MongoTemplate mongoTemplate;
    public void mongoInsert(String userIp, String message, String object){
        BusLog alarmLog = new BusLog(userIp, message, object, LocalDateTime.now());
        mongoTemplate.insert(alarmLog);
    }
}
