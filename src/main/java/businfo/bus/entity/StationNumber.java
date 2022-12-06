package businfo.bus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "station_number")
@Getter
@Setter
public class StationNumber {
    @Id
    @Column(name = "station_id")
    private String stationId;

    private Long stationNumber;

    private String stationName;

    private Double longitude;

    private Double latitude;

}
