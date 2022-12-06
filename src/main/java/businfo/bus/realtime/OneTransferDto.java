package businfo.bus.realtime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OneTransferDto {
    private BusArriveDto startBusArriveDto;
    private BusArriveDto endBusArriveDto;
    private String transferStationId;
    private String transferStationName;
    private Long transferStationNumber;



}
