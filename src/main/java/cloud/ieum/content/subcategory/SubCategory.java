package cloud.ieum.content.subcategory;

import cloud.ieum.content.category.Category;
import jakarta.persistence.*;

@Entity(name = "subcategory_tb")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Column
    private String name;
}
