package cloud.ieum.interest;

import cloud.ieum.content.subcategory.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubCategoryDTO {
    private Integer interestId;
    private String name;

    public SubCategoryDTO(SubCategory subCategory) {
        this.interestId = subCategory.getId();
        this.name = subCategory.getName();
    }


}
