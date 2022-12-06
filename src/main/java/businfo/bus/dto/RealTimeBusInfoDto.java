package businfo.bus.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.ComponentScan;

@Data
@ComponentScan
public class RealTimeBusInfoDto {
    private String Route;
    private String RouteTime;
    private String RouteStation;

    public RealTimeBusInfoDto() {
    }

    public RealTimeBusInfoDto(String route, String routeTime, String routeStation) {
        Route = route;
        RouteTime = routeTime;
        RouteStation = routeStation;
    }
}
