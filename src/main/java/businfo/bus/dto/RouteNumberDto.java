package businfo.bus.dto;

import businfo.bus.entity.StationNumber;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

@Data
public class RouteNumberDto {

    private String routeId;
    private String nodeNm;
    private int nodeNo;
    private int nodeOrd;
    private boolean upDownCd;



}
