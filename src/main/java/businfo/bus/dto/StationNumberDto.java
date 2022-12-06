package businfo.bus.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class StationNumberDto {
    private Long stationId;

    private String stationName;

    private Double xLocation;

    private Double yLocation;
}
