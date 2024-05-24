package cloud.ieum.content.post;

import cloud.ieum.content.image.ImageService;
import cloud.ieum.content.subcategory.SubCategory;
import cloud.ieum.content.subcategory.SubCategoryService;
import cloud.ieum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    private final int PAGE_SIZE = 5;
    private final int PAGE_SIZE_PLUS_ONE = PAGE_SIZE + 1;

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

    public PostDetailDto getPostDetails(Integer postId) {
        Post post = postJpaRepository.findById(postId).get();
        String userName = userRepository.findById(post.getCreatedBy()).get().getName();

        return PostDetailDto.builder()
                .menteeId(post.getCreatedBy())
                .title(post.getTitle())
                .pubDate(post.getCreatedAt())
                .description(post.getContent())
                .nickname(userName)
                .images(imageService.getImgUrlsByPostId(postId))
                .build();
    }

    public PostsByInterestDto getPostsByInterest(Integer interestId, Integer cursor) {
        Integer realCursor = cursor != null ? cursor : Integer.MAX_VALUE;
        Pageable pageable = Pageable.ofSize(PAGE_SIZE_PLUS_ONE);
        List<Post> postList = postJpaRepository.findAllByInterestUsingCursor(interestId, realCursor, pageable);

        List<PostsByInterestDto.PostDto> postDtoList = new ArrayList<>();
        for (Post post : postList) {
            String userName = userRepository.findById(post.getCreatedBy()).get().getName();
            String thumbnail = imageService.getFirstImagUrlByPostId(post.getId());
            postDtoList.add(new PostsByInterestDto.PostDto(post.getId(), post.getTitle(), post.getCreatedAt(), post.getContent(), userName, thumbnail));
        }
        boolean hasNext = postDtoList.size() == PAGE_SIZE_PLUS_ONE ? true : false;
        Integer nextCursor = 0;

        if (hasNext) {
            postDtoList.remove(PAGE_SIZE_PLUS_ONE - 1);
            nextCursor = postList.get(PAGE_SIZE-1).getId();
        }
        PostsByInterestDto.CursorDto cursorDto = new PostsByInterestDto.CursorDto(nextCursor, hasNext);

        return new PostsByInterestDto(postDtoList, cursorDto);
    }


}
