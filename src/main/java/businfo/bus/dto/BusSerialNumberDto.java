package businfo.bus.dto;

import businfo.bus.entity.RouteNumber;
import lombok.Data;

@Data
public class BusSerialNumberDto {
    private Long busId;

    private RouteNumber routeNumber;

}
