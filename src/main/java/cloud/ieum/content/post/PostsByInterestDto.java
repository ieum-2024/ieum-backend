package cloud.ieum.content.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostsByInterestDto {
    List<PostDto> contentList;
    CursorDto cursor;

    @Getter
    @AllArgsConstructor
    public static class PostDto {
        Integer contentId;
        String title;
        LocalDateTime pubDate;
        String description;
        String nickname;
        String thumbnail;
    }

    @Getter
    @AllArgsConstructor
    public static class CursorDto {
        Integer nextCursor;
        boolean hasNext;
    }
}
