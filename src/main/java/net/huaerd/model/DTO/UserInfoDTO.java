package net.huaerd.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserInfoDTO {
    private long id;
    private String username;
    private String email;
    private String sex;
    private String avatar;
    private LocalDateTime register_time;
}
