package cloud.ieum.oauth.controller;

import cloud.ieum.oauth.LoginDTO;
import cloud.ieum.oauth.service.OAuthService;
import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.user.UserDTO;
import cloud.ieum.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserService userService;
    private final OAuthService oAuthService;

    // OAuth2 로그인 시 최초 로그인인 경우 회원가입 진행
    @PostMapping("/user/info")
    public ResponseEntity<Object> OAuthSignUp(@ModelAttribute UserDTO userDTO, @AuthenticationPrincipal PrincipalDetail user) {
        log.info("save user");
        userService.updateUser(userDTO, user.getId());
        return ResponseEntity.ok(null);
    }

    private String REST_API_KEY = "0079af8e66a2f897721d5419a0933b70";


    private String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";


    private String AUTHORIZE_URI = "https://kauth.kakao.com/oauth/authorize";


    public String TOKEN_URI = "https://kauth.kakao.com/oauth/token";


    //테스트용
    @RequestMapping("/login")
    public RedirectView kakaoLogin1(){
        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&client_id="+REST_API_KEY;
        return new RedirectView(uri);
    }
    //테스트용
    @RequestMapping("/login/go")
    public RedirectView kakaologin(@RequestParam("scope") String scope){
        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&client_id="+REST_API_KEY;
        if(!scope.isEmpty()) uri += "&scope="+scope;
        return new RedirectView(uri);
    }

    @RequestMapping(value = "/login/oauth2/code/kakao")
    public ResponseEntity<?> kakaoInfo(@RequestParam("code") String code){
        LoginDTO loginDTO = oAuthService.login(code);
        return ResponseEntity.ok(loginDTO);
    }








}