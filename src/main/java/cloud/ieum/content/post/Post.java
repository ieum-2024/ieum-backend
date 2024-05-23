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
@Entity(name = "post_tb")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategory subCategory;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Long createdBy;
}
