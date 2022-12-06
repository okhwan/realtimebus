package businfo.bus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "route_number")
@Getter
@Setter
public class RouteNumber {
    @Id
    @GeneratedValue
    private Long routeSerialId;

    @Column(name = "route_id")
    private String routeId; //노선번호

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationNumber stationId; //정류장번호

    private Long stationNumber;

    private String stationName;

    private int sequenceNumber;

    private int upDown;





}
