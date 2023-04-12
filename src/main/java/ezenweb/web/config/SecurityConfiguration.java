package ezenweb.web.config;

import ezenweb.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 빈에 등록 [ MVC 컴포넌트 ]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    // 인증[로그인] 관련 보안 담당 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() );
        // auth.userDetailsService( )    :    UserDetailsService 가 구현된 서비스 대입
        // .passwordEncoder(  new BCryptPasswordEncoder() ) 서비스 안에서 로그인 패스워드 검증시 사용된 암호화 객체 대입
    }

    // configure(HttpSecurity http) : http[URL] 관련 보안 담당 메소드
    @Override // 재정의 [ 코드 바꾸기 ]
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); // super : 부모 클래스 호출
        http
                .csrf() // 사이트 간 요청 위조 [ post,put http 사용 불가능 ]
                    .ignoringAntMatchers("/member/info") // 특정 매핑URL csrf 무시
                    .ignoringAntMatchers("/member/login")
                .and()//  기능 추가/구분 할때 사용되는 메소드
                    .formLogin()
                        .loginPage("/member/login") // 로그인 으로 사용될 페이지의 매핑 URL
                        .loginProcessingUrl("/member/login") // 로그인을 처리할 매핑 URL
                        .defaultSuccessUrl("/") // 로그인 성공했을때 이동할 매핑 URL
                        .failureUrl("/member/login")// 로그인 실패했을때 이동할 매핑 URL
                        .usernameParameter("memail") // 로그인시 사용될 계정 아이디 의 필드명
                        .passwordParameter("mpassword")// 로그인시 사용될 계정 패스워드 의 필드명
                .and()
                    .logout()
                        .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout") ) // 로그아웃 처리 를 요청할 매핑 URL
                        .logoutSuccessUrl("/")//로그아웃 성공했을때 이동할 매핑 URL
                        .invalidateHttpSession( true ); // 세션 초기화x
    }
}
