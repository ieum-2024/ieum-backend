package cloud.ieum.content.post;

import cloud.ieum.content.subcategory.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "post_tb")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategory subCategory;

    @Column(length=1000)
    private String title;

    @Column(length=1000)
    private String content;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Integer createdBy;
}
