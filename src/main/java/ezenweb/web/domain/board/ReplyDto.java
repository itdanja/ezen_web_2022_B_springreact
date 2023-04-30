package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data@NoArgsConstructor@AllArgsConstructor @Builder
public class ReplyDto {
    private int rno;
    private String rcontent;
    private String rdate;
    private int rindex;
    private int bno; // 댓글이 속해있는 게시물 번호
    private String mname;
    private int mno;

    // 대댓글
    @Builder.Default
    private List<ReplyDto> rereReplyDtoList = new ArrayList<>();

    // 저장용
    public ReplyEntity toEntity(){
        return ReplyEntity.builder()
                .rcontent( this.rcontent )
                .rindex( this.rindex )
                .build();
    }
}
