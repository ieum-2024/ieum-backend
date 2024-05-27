package cloud.ieum.content.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageJpaRepository extends JpaRepository<Image, Integer> {
    List<Image> findByPostId(Integer postId);
    Optional<Image> findFirstByPostId(Integer postId);
}