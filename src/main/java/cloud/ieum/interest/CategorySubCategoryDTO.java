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
    private String main_topic;
    private List<SubCategoryAcitveDTO> sub_topic;

    public CategorySubCategoryDTO(Category main_topic, List<SubCategoryAcitveDTO> sub_topic) {
        this.main_topic = main_topic.getName();
        this.sub_topic = sub_topic;
    }



}
