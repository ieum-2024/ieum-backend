package cloud.ieum.interest.controller;

import cloud.ieum.content.category.Category;
import cloud.ieum.content.category.CategoryJpaRepository;
import cloud.ieum.content.subcategory.SubCategory;
import cloud.ieum.content.subcategory.SubCategoryService;
import cloud.ieum.interest.CategorySubCategoryDTO;
import cloud.ieum.interest.Interest;
import cloud.ieum.interest.SubCategoryAcitveDTO;
import cloud.ieum.interest.SubCategoryDTO;
import cloud.ieum.interest.service.InterestService;
import cloud.ieum.oauth.DTO.SessionUser;
import cloud.ieum.oauth.annotation.LoginUser;
import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class InterestController {
    private final InterestService interestService;
    private final SubCategoryService subCategoryService;
    private final CategoryJpaRepository categoryJpaRepository;

    @GetMapping("/interest/all")
    public ResponseEntity<?> interestAll(){
        List<SubCategory> allSubCategory = subCategoryService.getAllSubCategory();
        List<SubCategoryDTO> responseValue = new ArrayList<>();
        for (SubCategory subCategory : allSubCategory) {
            responseValue.add(new SubCategoryDTO(subCategory));
        }
        return ResponseEntity.ok().body(ApiUtils.success(responseValue));
    }

    @GetMapping("/interest/public")
    public ResponseEntity<?> interestPublic(@AuthenticationPrincipal PrincipalDetail user){

        List<Category> all = categoryJpaRepository.findAll();
        List<CategorySubCategoryDTO> responseValue = new ArrayList<>();

        for (Category category : all) {
            List<SubCategory> allByCategory = subCategoryService.getAllByCategory(category.getId());
            List<SubCategoryAcitveDTO> subCategoryAcitveDTOList = new ArrayList<>();
            for (SubCategory subCategory : allByCategory) {
                subCategoryAcitveDTOList.add(new SubCategoryAcitveDTO(subCategory, interestService.isActiveInterest(subCategory.getId(), user.getId())));
            }
            responseValue.add(new CategorySubCategoryDTO(category, subCategoryAcitveDTOList));
        }
        return ResponseEntity.ok().body(ApiUtils.success(responseValue));
    }
    @GetMapping("/interest/private")
    public ResponseEntity<?> interestPrivate(@AuthenticationPrincipal PrincipalDetail user){

        List<Interest> userInterest = interestService.findUserInterest(user.getId());
        log.info("interest private");
        List<SubCategoryDTO> responseValue = new ArrayList<>();
        for (Interest interest : userInterest) {
            responseValue.add(new SubCategoryDTO(subCategoryService.getReferenceById(interest.getCategoryId())));
        }
        return ResponseEntity.ok().body(ApiUtils.success(responseValue));
    }
    @GetMapping("/interest/create/{interest_id}")
    public String getEmpty(){
        return "empty";
    }

    @PostMapping("/interest/create/{interest_id}")
    public ResponseEntity<?> addInterest(@PathVariable Integer interest_id, @AuthenticationPrincipal PrincipalDetail user){
        interestService.saveInterest(interest_id, user.getId());
        log.info("interest create");
        log.info(user.getId().toString());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/interest/delete/{interest_id}")
    public ResponseEntity<?> removeInterest(@PathVariable Integer interest_id, @AuthenticationPrincipal PrincipalDetail user){
        interestService.removeInterest(interest_id, user.getId());
        log.info("interest delete");
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

}
