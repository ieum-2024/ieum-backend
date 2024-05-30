package cloud.ieum.user.controller;

import cloud.ieum.oauth.DTO.KakaoUserInfo;
import cloud.ieum.oauth.annotation.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserController {

    //테스트
    @GetMapping("/login/kakao")
    public String login(){

        log.info("로그인");
        return "oauthLogin";
    }



    //테스트
    @GetMapping("/user/info")
    public String signup(@RequestParam String socialId, Model model){
        model.addAttribute("socialId", socialId);

        log.info("signup");

        return "signup";
    }





}
