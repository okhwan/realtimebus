package businfo.bus.mongodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class MongoLogger{
    private final MongoTemplate mongoTemplate;


    protected void writeInfo(String msg) {
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("LEVEL", "TRACE");
        logMap.put("DATETIME", new Date());
        logMap.put("REQUESTER", log.getName());
        logMap.put("MESSAGE", msg);

        mongoTemplate.insert(logMap, "logMap");
    }
}
