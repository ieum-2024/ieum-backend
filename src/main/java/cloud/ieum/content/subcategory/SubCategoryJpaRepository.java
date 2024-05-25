package cloud.ieum.content.subcategory;

import cloud.ieum.content.category.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryJpaRepository extends JpaRepository<SubCategory, Integer> {
    List<SubCategory> findByCategoryId(Integer categoryId);
}

