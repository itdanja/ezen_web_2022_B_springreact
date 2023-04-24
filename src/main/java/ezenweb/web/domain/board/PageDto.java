package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@NoArgsConstructor@AllArgsConstructor @Builder
public class PageDto {
    private int totalpages;
    private Long totalRows;
    private List<BoardDto> boards;
    private int cno;
    private int page;
    private String key;
    private String keyword;
}
