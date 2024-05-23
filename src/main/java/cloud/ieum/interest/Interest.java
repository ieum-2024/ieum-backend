package cloud.ieum.interest;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "interest_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "categoryId")
    private Long categoryId;

}
