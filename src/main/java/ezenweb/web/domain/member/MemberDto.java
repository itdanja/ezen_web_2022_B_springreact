package ezenweb.web.domain.member;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

// 시큐리티[UserDetails] + 소셜회원[OAuth2User]
@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor@Builder
public class MemberDto implements UserDetails , OAuth2User {

    private int mno; // 1. 회원번호
    private String memail; // 2. 회원아이디[ 이메일 ]
    private String mpassword; // 3. 회원비밀번호
    private String mname; // 4. 회원이름
    private String mphone; // 5. 회원전화번호
    private String mrole;// 6. 회원등급 [ 가입용 ]
    private Set<GrantedAuthority> 권한목록; //7.[ 인증용 ]
    private Map<String, Object> 소셜회원정보; // 8. oauth2 인증 회원정보

    // 추가
    private LocalDateTime cdate;
    private LocalDateTime udate;
    // toEntity
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .mno( this.mno ) .memail( this.memail )
                .mname( this.mname ).mphone( this.mphone )
                .mpassword( this.mpassword ).mrole(this.getMrole())
                .build();
    }
    // ----------------------  UserDetails ---------------------
    @Override // 인증된 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.권한목록;
    }

    @Override // 패스워드 반환
    public String getPassword() {
        return this.mpassword;
    }

    @Override // 계정 반환
    public String getUsername() {
        return this.memail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { return true;   }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // --------------------------------- OAuth2User --------------------//ㅌ
    @Override // Oauth2 회원정보
    public Map<String, Object> getAttributes() {  return this.소셜회원정보; }

    @Override // Oauth2 아이디
    public String getName() { return this.memail; }
}
