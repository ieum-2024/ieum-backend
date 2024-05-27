package cloud.ieum.content.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailDto {
    private Integer menteeId;
    private String title;
    private LocalDateTime pubDate;
    private String description;
    private String nickname;
    private List<String> images;
}
