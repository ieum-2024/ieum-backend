package cloud.ieum.interest;

import cloud.ieum.content.category.Category;
import cloud.ieum.content.subcategory.SubCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CategorySubCategoryDTO {
    private String mainTopic;
    private List<SubCategoryAcitveDTO> subTopic;

    public CategorySubCategoryDTO(Category mainTopic, List<SubCategoryAcitveDTO> subTopic) {
        this.mainTopic = mainTopic.getName();
        this.subTopic = subTopic;
    }



}
