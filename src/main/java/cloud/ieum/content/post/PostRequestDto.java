package cloud.ieum.content.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostRequestDto {
    String title;
    Integer subCategory;
    String description;
}
