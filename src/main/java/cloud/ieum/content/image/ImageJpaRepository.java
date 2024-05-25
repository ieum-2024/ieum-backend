package cloud.ieum.content.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageJpaRepository extends JpaRepository<Image, Integer> {
    List<Image> findImagesByPostId(Integer postId);
}
