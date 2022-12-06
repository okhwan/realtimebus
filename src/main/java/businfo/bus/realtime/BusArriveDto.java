package businfo.bus.realtime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusArriveDto {
    private String routeNo; //버스 노선 번호
    private String routeTp; //일반 or 좌석버스
    private String vehicleTp; //저상버스 일반차량 (휠체어 탑승 가능 버스)
    private Long arriveCount; //몇 정거장 남았나
    private Long arriveTimeMin; //남은 시간 (분)
    private Long arriveTimeSec; //남은 시간 (초)

}
