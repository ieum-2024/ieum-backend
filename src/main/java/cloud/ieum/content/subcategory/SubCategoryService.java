package cloud.ieum.content.subcategory;

import cloud.ieum.content.category.Category;
import cloud.ieum.interest.SubCategoryAcitveDTO;
import cloud.ieum.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubCategoryService {
    private final SubCategoryJpaRepository subCategoryJpaRepository;
    public SubCategory getReferenceById(Integer subCategoryId) {
        return subCategoryJpaRepository.getReferenceById(subCategoryId);
    }

    public List<SubCategory> getAllSubCategory(){
        return subCategoryJpaRepository.findAll();
    }

    public List<SubCategory> getAllByCategory(Integer categoryId){
        return subCategoryJpaRepository.findByCategoryId(categoryId);
    }


}
