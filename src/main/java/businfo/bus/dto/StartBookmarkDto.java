package businfo.bus.dto;

import lombok.Data;

@Data
public class StartBookmarkDto {
    private Long startBookmarkId;

    private Long memberId;

    private Long stationId;
}
