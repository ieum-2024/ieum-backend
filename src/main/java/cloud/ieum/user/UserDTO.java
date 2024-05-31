package cloud.ieum.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {

    private String nickname;
    private String type;

    public UserDTO(String nickname, String type) {
        this.nickname = nickname;
        this.type = type;
    }
}
