package businfo.bus.mongodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Document("logdata")
public class BusLog {
    private String userIp;
    private String message;
    private String object;
    private LocalDateTime time;

}
