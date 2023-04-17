package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "reply")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReplyEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;
    @Column
    private String rcontent;
    // 게시물fk
    @ManyToOne@JoinColumn(name = "bno")@ToString.Exclude
    private BoardEntity boardEntity;
    // 작성자fk
    @ManyToOne@JoinColumn(name="mno")@ToString.Exclude
    private MemberEntity memberEntity;


}
