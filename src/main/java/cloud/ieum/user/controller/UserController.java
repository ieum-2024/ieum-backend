package cloud.ieum.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UserController {
    //@GetMapping("/login")
    @GetMapping("/user/login/kakao")
    public String login(){

        log.info("로그인");
        return "oauthLogin";
    }

    //@GetMapping("/interest")
    @GetMapping("/interest/all")
    public String interest(){

        log.info("interest");
        return "interest";
    }



}
