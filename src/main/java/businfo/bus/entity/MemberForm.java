package businfo.bus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
public class MemberForm{
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long userId;

    @Column(length = 15)
    private String name;

    @Column(name = "member_id",unique = true)
    private String id;

    private String pw;

    private String phone;

    private String address;

    @Column(unique = true)
    private String email;

    public MemberForm(String name, String id, String pw, String phone, String address, String email) {
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public MemberForm(String id){
        this.id = id;
    }

    public MemberForm() {
        super();
    }
}
