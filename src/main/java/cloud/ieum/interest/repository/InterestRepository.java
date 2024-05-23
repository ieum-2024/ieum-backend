package cloud.ieum.interest.repository;

import cloud.ieum.interest.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    List<Interest> findAllByUserId(Long userId);

    Optional<Interest> findByUserIdAndCategoryId(Long userId, Long categoryId);
}
