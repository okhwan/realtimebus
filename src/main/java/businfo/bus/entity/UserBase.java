package businfo.bus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class UserBase {
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long userId;

    @Column(length = 15)
    private String name;

    @Column(name = "id",unique = true)
    private String id;

    private String pw;

    public UserBase(String name, String id, String pw, String email) {
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.email = email;
    }

    public UserBase(String id) {
        this.id = id;
    }

    @Column(unique = true)
    private String email;

    public UserBase() {

    }
}
