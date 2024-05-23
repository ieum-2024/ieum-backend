package cloud.ieum.content.post;

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
                                         @AuthenticationPrincipal PrincipalDetail user) throws Exception {

        postService.create(user.getUsername(), postRequestDto, images);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getContentDetails(@AuthenticationPrincipal PrincipalDetail user, @RequestParam Integer content_id) throws Exception {
        PostDetailDto responseDto = postService.getContentDetails(user.getUsername(), content_id);
        return ResponseEntity.ok().body(ApiUtils.success(responseDto));
    }
}
