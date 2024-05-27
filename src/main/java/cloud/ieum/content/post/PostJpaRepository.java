package cloud.ieum.content.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT p FROM Post p " +
            "WHERE p.id < :cursor AND p.subCategory.id = :interest " +
            "ORDER by p.id DESC")
    List<Post> findAllByInterestUsingCursor(@Param("interest") Integer interest, @Param("cursor")Integer cursor, Pageable pageable);
}