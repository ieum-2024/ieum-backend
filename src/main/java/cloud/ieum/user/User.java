package cloud.ieum.user;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "name")
    private String name;
    //socialId로 유저 구분 가능
    @Column(name = "socialId")
    private String socialId;
    //최초 로그인인지 아닌지 알아보기 위한 용도
    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;
    //멘토인지 멘티인지
    @Column(name = "type")
    private String type;
    public void authorizeUser(){
        this.role = Role.USER;
    }

    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }

}
