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
    private int bno; // 댓글이 속해있는 게시물 번호
    private int rindex; // 부모 댓글번호

    private int mno;
    private String mname;

    // 답글 리스트
    @Builder.Default // 빌더 이용한 객체 생성시 현재 필드 정보 기본값으로 사용
    private List<ReplyDto> rereplyDtoList = new ArrayList<>();
    // 저장용
    public ReplyEntity toEntity(){
        return ReplyEntity.builder()
                .rcontent( this.rcontent )
                .rindex( this.rindex )
                .build();
    }
}
