package cloud.ieum.content.post;

import cloud.ieum.oauth.DTO.SessionUser;
import cloud.ieum.oauth.annotation.LoginUser;
import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/content")
@RequiredArgsConstructor
@RestController
public class PostRestController {
    private final PostService postService;
    @PostMapping("")
    public ResponseEntity<?> postContent(@RequestPart("data") PostRequestDto postRequestDto,
                                         @RequestPart(value = "file", required = false) List<MultipartFile> images,
                                         @LoginUser SessionUser user) throws Exception {

        postService.create(user.getName(), postRequestDto, images);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/detail/{content_id}")
    public ResponseEntity<?> getContentDetails(@PathVariable Integer content_id) {
        PostDetailDto responseDto = postService.getPostDetails(content_id);
        return ResponseEntity.ok().body(ApiUtils.success(responseDto));
    }

    @GetMapping("/{interest_id}")
    public ResponseEntity<?> getContentsByInterest(@PathVariable Integer interest_id, @RequestParam(required = false) Integer cursor) {
        PostsByInterestDto responseDto = postService.getPostsByInterest(interest_id, cursor);
        return ResponseEntity.ok().body(ApiUtils.success(responseDto));
    }
}