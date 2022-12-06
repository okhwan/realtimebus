package businfo.bus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bus_serialnumber")
@Getter
@Setter
public class BusSerialNumber {
    @Id
    @Column(name = "bus_id")
    private Long busId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private RouteNumber routeNumber;

}
