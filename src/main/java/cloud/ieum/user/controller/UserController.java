package cloud.ieum.user.controller;

import cloud.ieum.oauth.service.OAuthService;
import cloud.ieum.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserController {

    //테스트용
    @RequestMapping("/user/login/kakao")
    public String login(){
        return "oauthLogin";
    }


    //테스트용
    @GetMapping("/user/info")
    public String signup(@RequestParam String socialId, Model model){
        model.addAttribute("socialId", socialId);
        log.info("signup");

        return "signup";
    }



}
