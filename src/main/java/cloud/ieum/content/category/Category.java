package cloud.ieum.content.category;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "category_tb")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
}
