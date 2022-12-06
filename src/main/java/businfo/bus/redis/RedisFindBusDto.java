package businfo.bus.redis;

import businfo.bus.realtime.BusArriveDto;
import businfo.bus.realtime.OneTransferDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.List;

@Getter
@Setter
@RedisHash("findBus")
public class RedisFindBusDto {
    @Id
    private String startStation;
    private List<BusArriveDto> noTransferList;
    private List<OneTransferDto> oneTransferList;

    private String endStation;

    @TimeToLive
    private Long expired;

    public RedisFindBusDto(List<BusArriveDto> noTransferList, List<OneTransferDto> oneTransferList,String startStation,  String endStation, Long expired) {
        this.startStation = startStation;
        this.noTransferList = noTransferList;
        this.oneTransferList = oneTransferList;
        this.endStation = endStation;
        this.expired = expired;
    }
}
