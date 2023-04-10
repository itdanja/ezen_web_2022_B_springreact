package ezenweb.web.domain.member;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor@Builder
public class MemberDto {
    private int mno; // 1. 회원번호
    private String memail; // 2. 회원아이디[ 이메일 ]
    private String mpassword; // 3. 회원비밀번호
    private String mname; // 4. 회원이름
    private String mphone; // 5. 회원전화번호
    private String mrole;// 6. 회원등급
    // 추가
    private LocalDateTime cdate;
    private LocalDateTime udate;
    // toEntity
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .mno( this.mno ) .memail( this.memail )
                .mname( this.mname ).mphone( this.mphone )
                .mpassword( this.mpassword )
                .build();
    }
}
