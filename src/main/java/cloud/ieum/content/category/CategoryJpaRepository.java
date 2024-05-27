package cloud.ieum.content.category;

import cloud.ieum.interest.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {
    List<Category> findAll();
}
