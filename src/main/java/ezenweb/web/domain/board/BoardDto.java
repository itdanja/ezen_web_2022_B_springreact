package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BoardDto {
    private int bno;
    private String btitle;
    private String bcontent;
    private int cno;
    private String cname;

    // Entity 변환 메소드
    // 1. toCategoryEntity
    public CategoryEntity toCategoryEntity(){
        return CategoryEntity.builder()
                .cname(this.cname)
                .build();
    }
    // 2. toBoardEntity
    public BoardEntity toBoardEntity(){
        return BoardEntity.builder()
                .btitle( this.btitle )
                .bcontent( this.bcontent )
                .build();
    }

}












