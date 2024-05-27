package cloud.ieum.content.image;

import cloud.ieum.content.post.Post;
import cloud.ieum.content.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final S3Service s3Service;
    private final ImageJpaRepository imageJpaRepository;

    public List<String> upload(List<MultipartFile> images) throws IOException {
        List<String> imgUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            imgUrls.add(s3Service.uploadFile(image));
        }
        return imgUrls;
    }

    public void create(List<String> imgUrls, Post post) {
        List<Image> imageList = new ArrayList<>();
        for (String url : imgUrls) {
            Image image = Image.builder()
                    .post(post)
                    .imageUrl(url).build();
            imageList.add(image);
        }
        imageJpaRepository.saveAll(imageList);
    }

    public List<String> getImgUrlsByPostId(Integer postId) {
        return imageJpaRepository.findByPostId(postId).stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
    }

    public String getFirstImagUrlByPostId(Integer postId) {
        Optional<Image> image = imageJpaRepository.findFirstByPostId(postId);
        return image.isEmpty() ? "" : image.get().getImageUrl();
    }
}