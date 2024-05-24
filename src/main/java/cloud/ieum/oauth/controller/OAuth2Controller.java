package cloud.ieum.oauth.controller;

import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.user.UserDTO;
import cloud.ieum.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserService userService;
    // OAuth2 로그인 시 최초 로그인인 경우 회원가입 진행
    @PostMapping("/user/info")
    //public String OAuthSignUp(@ModelAttribute UserDTO userDTO, @AuthenticationPrincipal PrincipalDetail user) {
    public ResponseEntity<Object> OAuthSignUp(@ModelAttribute UserDTO userDTO, @AuthenticationPrincipal PrincipalDetail user) {
        log.info("save user");
        userService.updateUser(userDTO, user.getId());
        return ResponseEntity.ok(null);
        //return "redirect:/interest/public";
    }

}