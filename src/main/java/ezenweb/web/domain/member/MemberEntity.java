package ezenweb.web.domain.member;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.board.BoardEntity;
import ezenweb.web.domain.board.ReplyEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
@Entity@Table(name = "member")
public class MemberEntity extends BaseTime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int mno; // 1. 회원번호
    @Column private String memail; // 2. 회원아이디[ 이메일 ]
    @Column private String mpassword; // 3. 회원비밀번호
    @Column private String mname; // 4. 회원이름
    @Column private String mphone; // 5. 회원전화번호
    @Column private String mrole;// 6. 회원등급/권한 명

    // 게시물목록 = 내가 쓴 게시물
    @OneToMany(mappedBy = "memberEntity")
    @Builder.Default
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    // 댓글목록 = 내가 쓴 댓글
    @OneToMany(mappedBy = "memberEntity")
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();


    // toDto 출력용
    public MemberDto todto(){
        return MemberDto.builder()
                .mno( this.mno ) .memail( this.memail )
                .mname( this.mname ).mphone( this.mphone )
                .mpassword( this.mpassword )
                .cdate( this.cdate ).udate( this.udate )
                .build();
    }
}


