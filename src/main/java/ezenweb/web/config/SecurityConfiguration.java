package ezenweb.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 스프링 빈에 등록 [ MVC 컴포넌트 ]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    // configure(HttpSecurity http) : http[URL] 관련 보안 담당 메소드
    @Override // 재정의 [ 코드 바꾸기 ]
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); // super : 부모 클래스 호출
        http
                .csrf() // 사이트 간 요청 위조 [ post,put http 사용 불가능 ]
                    .ignoringAntMatchers("/member/info") // 특정 매핑URL csrf 무시
                    .ignoringAntMatchers("/member/login");
    }
}
