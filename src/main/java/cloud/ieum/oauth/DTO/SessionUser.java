package cloud.ieum.oauth.DTO;

import cloud.ieum.user.Role;
import cloud.ieum.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class SessionUser implements Serializable {
    private String name;
    private String socialId;
    private Role role;
    private Integer id;
    public SessionUser(User user) {
        this.name = user.getName();
        this.socialId = user.getSocialId();
        this.role = user.getRole();
        this.id = user.getId();
    }


}

