package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PageDto {
    private long totalCount;//1.전체 게시물수
    private int totalPage;//2.전체 페이지수
    private List<BoardDto> boardDtoList; //3.현재 페이지의 게시물 dto들
    private int page; //4.현재 페이지번호
    private int cno; //5.현재 카테고리번호
}
