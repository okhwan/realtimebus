package businfo.bus.dto;

import lombok.Data;

@Data
public class MemberFormDto {
    private Long userId;
    private String name;
    private String id;
    private String pw;
    private String phone;
    private String email;
    private String address;

}
