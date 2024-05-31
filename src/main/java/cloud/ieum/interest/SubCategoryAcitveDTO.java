package cloud.ieum.interest;

import cloud.ieum.content.subcategory.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubCategoryAcitveDTO {
    private Integer interestId;
    private String name;
    private Boolean isActive;

    public SubCategoryAcitveDTO(SubCategory subCategory, Boolean isActive) {
        this.interestId = subCategory.getId();
        this.name = subCategory.getName();
        this.isActive = isActive;
    }
}
