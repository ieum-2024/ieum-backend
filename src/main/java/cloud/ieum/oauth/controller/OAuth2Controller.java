package cloud.ieum.oauth.controller;

import cloud.ieum.oauth.DTO.KakaoUserInfo;
import cloud.ieum.oauth.DTO.LoginDTO;
import cloud.ieum.oauth.DTO.SessionUser;
import cloud.ieum.oauth.annotation.LoginUser;
import cloud.ieum.oauth.service.OAuthService;
import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.user.UserDTO;
import cloud.ieum.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserService userService;
    private final OAuthService oAuthService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String REST_API_KEY;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String AUTHORIZE_URI;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    public String TOKEN_URI;


    // OAuth2 로그인 시 최초 로그인인 경우 회원가입 진행
    @PostMapping("/user/info")
    public ResponseEntity<Object> OAuthSignUp(@ModelAttribute UserDTO userDTO, @LoginUser SessionUser user) {
        //SessionUser user = (SessionUser) httpSession.getAttribute("loginUser");
        log.info("save user");
        log.info(user.getSocialId());
        userService.updateUser(userDTO, user.getSocialId());
        return ResponseEntity.ok(null);
    }

    /*//테스트용
    @RequestMapping("/login")
    public RedirectView kakaoLogin1(){
        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&client_id="+REST_API_KEY;
        return new RedirectView(uri);
    }
*/
    @PostMapping(value = "/user/login/kakao")
    //public ResponseEntity<LoginDTO> kakao(@RequestPart(value = "code") String code){
    public ResponseEntity<LoginDTO> kakao(@RequestBody HashMap<String, String> map){
        String code = map.get("code");
        LoginDTO loginDTO = oAuthService.login(code);

        return ResponseEntity.ok(loginDTO);
    }

/*
    //테스트용
    @GetMapping(value = "/user/login")
    public String kakao_(String code){
        return code;
    }

*/








}