package cloud.ieum.content.subcategory;

import cloud.ieum.content.category.Category;
import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "subcategory_tb")
@Getter
public class SubCategory {
    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Column
    private String name;
}
