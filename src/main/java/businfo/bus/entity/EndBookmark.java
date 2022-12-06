package businfo.bus.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "end_bookmark")
@Getter
@Setter
public class EndBookmark {
    //도착 정류장 저장 id(키)
    @Id
    @Column(name = "end_bookmark_id")
    @GeneratedValue
    private Long endBookMark;

    //회원 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberForm memberForm;

    //정류장 번호 (station_number_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private StationNumber stationId;

    public EndBookmark(MemberForm memberForm, StationNumber stationId, Long stationNumber, String stationName) {
        this.memberForm = memberForm;
        this.stationId = stationId;
        this.stationNumber = stationNumber;
        this.stationName = stationName;
    }

    @Column(name = "station_number")
    private Long stationNumber;

    @Column(name = "station_name")
    private String stationName;


    public EndBookmark() {

    }
}
