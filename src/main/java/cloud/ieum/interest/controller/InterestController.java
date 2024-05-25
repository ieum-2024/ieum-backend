package cloud.ieum.interest.controller;

import cloud.ieum.interest.Interest;
import cloud.ieum.interest.service.InterestService;
import cloud.ieum.user.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class InterestController {
    private final InterestService interestService;

    @GetMapping("/interest/all")
    public String interestAll(){

        return null;
    }

    @GetMapping("/interest/public")
    public String interestPublic(){

        log.info("interest public");
        return "interest";
    }
    @GetMapping("/interest/private")
    public ResponseEntity<List<Interest>> interestPrivate(@AuthenticationPrincipal PrincipalDetail user){
        List<Interest> userInterest = interestService.findUserInterest(user.getId());
        log.info("interest private");
        return ResponseEntity.ok()
                .body(userInterest);
    }
    @GetMapping("/interest/create/{interest_id}")
    public String getEmpty(){
        return "empty";
    }
    @PostMapping("/interest/create/{interest_id}")
    public ResponseEntity<?> addInterest(@PathVariable long interest_id, @AuthenticationPrincipal PrincipalDetail user){
        interestService.saveInterest(interest_id, user.getId());
        log.info("interest create");
        return ResponseEntity.ok(null);
    }

    @PostMapping("/interest/delete/{interest_id}")
    public ResponseEntity<?> removeInterest(@PathVariable long interest_id, @AuthenticationPrincipal PrincipalDetail user){
        interestService.removeInterest(interest_id, user.getId());
        log.info("interest delete");
        return ResponseEntity.ok(null);
    }

}
