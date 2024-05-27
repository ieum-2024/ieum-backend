package cloud.ieum.oauth.DTO;


import cloud.ieum.interest.Interest;
import cloud.ieum.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDTO {
    private String accessToken;
    private String refreshToken;
    private Integer userId;

    public LoginDTO(String accessToken, String refreshToken, Integer userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}