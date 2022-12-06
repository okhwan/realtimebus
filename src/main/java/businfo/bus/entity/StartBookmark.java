package businfo.bus.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "start_bookmark")
@Getter
@Setter
public class StartBookmark {
    //출발 정류장 저장 id(키)
    @Id
    @Column(name = "start_bookmark_id")
    @GeneratedValue
    private Long startBookmarkId;

    //회원 정보 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberForm memberForm;

    public StartBookmark(MemberForm memberForm, StationNumber stationId, Long stationNumber, String stationName) {
        this.memberForm = memberForm;
        this.stationId = stationId;
        this.stationNumber = stationNumber;
        this.stationName = stationName;
    }
//정류장 번호 (station_number_id)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private StationNumber stationId;

    @Column(name = "station_number")
    private Long stationNumber;
    @Column(name = "station_name")
    private String stationName;


    public StartBookmark() {

    }
}
