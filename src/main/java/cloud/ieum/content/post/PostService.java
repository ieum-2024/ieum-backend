package cloud.ieum.content.post;

import cloud.ieum.content.image.ImageService;
import cloud.ieum.content.subcategory.SubCategory;
import cloud.ieum.content.subcategory.SubCategoryService;
import cloud.ieum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final SubCategoryService subCategoryService;
    private final ImageService imageService;
    private final PostJpaRepository postJpaRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(String userName, PostRequestDto postRequestDto, List<MultipartFile> images) throws Exception {
        // subCategory 프록시 객체
        SubCategory subCategory = subCategoryService.getReferenceById(postRequestDto.getSubCategory());

        // s3 업로드 url
        List<String> imgUrls = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            imgUrls = imageService.upload(images);
        }

        Long userId = userRepository.findByName(userName).get().getId();
        
        Post post = Post.builder()
                .title(postRequestDto.title)
                .content(postRequestDto.description)
                .subCategory(subCategory)
                .createdAt(LocalDateTime.now())
                .createdBy(userId)
                .build();
        postJpaRepository.save(post);

        if (!imgUrls.isEmpty()) {
            imageService.create(imgUrls, post);
        }
    }

    public PostDetailDto getContentDetails(String userName, Integer postId) {
        Post post = postJpaRepository.findById(postId).get();

        return PostDetailDto.builder()
                .menteeId(post.getCreatedBy())
                .title(post.getTitle())
                .pubDate(post.getCreatedAt())
                .description(post.getContent())
                .nickname(userName)
                .images(imageService.getImgUrlsByPostId(postId))
                .build();
    }
}
