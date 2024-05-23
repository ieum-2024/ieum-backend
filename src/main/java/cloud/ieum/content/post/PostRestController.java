package cloud.ieum.content.post;

import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
}