package cloud.ieum.content.subcategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubCategoryService {
    private final SubCategoryJpaRepository subCategoryJpaRepository;
    public SubCategory getReferenceById(Integer subCategoryId) {
        return subCategoryJpaRepository.getReferenceById(subCategoryId);
    }
}
