package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

// UserDetailsService : 일반유저 서비스 구현 ---> loadUserByUsername 구현
// OAuth2UserService<OAuth2UserRequest , OAuth2User>  : oauth2 유저 서비스 구현 ---> loadUser 구현

@Service // 서비스 레이어
@Slf4j
public class MemberService implements UserDetailsService , OAuth2UserService<OAuth2UserRequest , OAuth2User> {

    @Override // 토큰 결과 [ JSON { 필드명 : 값 , 필드명 : 값  } VS Map { 키 = 값 , 키 = 값 , 키 = 값 } ]
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 인증[로그인] 결과 토큰 확인
        log.info( "토큰 결과 정보 : " +userRequest );
        // 2. 전달받은 토큰을 이용한 회원정보 요청
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();    log.info( "서비스 정보 : " +oAuth2UserService.loadUser( userRequest ) );
        OAuth2User oAuth2User = oAuth2UserService.loadUser( userRequest );   log.info("회원정보 : " + oAuth2User.getAttributes() );
        // 3. 클라이언트ID 식별 [ 응답된 JSON 구조 다르기 때문에 클라이언트ID별(구글VS카카오VS네이버) 로 처리  ]
        String registrationId = userRequest.getClientRegistration().getRegistrationId();    log.info("클라이언트ID : " + registrationId );

        String email = null;
        String name = null;
        // oAuth2User.getAttributes()  map< String , Object >구조
        if( registrationId.equals("kakao") ) { // 만약에 카카오 회원이면
            // 카카오 Attributes = {id=206798674 , kakao_account={profile_nickname_needs_agreement=false, profile={nickname=김현수} , email=itdanja@kakao.com} }
            Map<String , Object >  kakao_account = (Map<String , Object>)oAuth2User.getAttributes().get("kakao_account");  log.info( "카카오 회원정보 "+ kakao_account );
            Map<String , Object > profile = (Map<String , Object>) kakao_account.get("profile");     log.info( "카카오 프로필 "+ profile );

            email = (String)kakao_account.get("email");
            name = (String)profile.get("nickname");

        }else if( registrationId.equals("naver")){ // 만약에 네이버 회원이면
            // 네이버 Attributes {resultcode=00, message=success, response={id=Hq9vZhky2c775-RmPtIeB95Rz2dnBbYgKTJPAHSsvDQ, nickname=아이티단자, email=kgs2072@naver.com}}
            Map<String , Object> response = (Map<String , Object>)oAuth2User.getAttributes().get("response");

            email = (String) response.get("email");
            name = (String) response.get("nickname");

        }else if( registrationId.equals("google")){ // 만약에 구글 회원이면
            // 구글 Attributes = {sub=114044778334166488538, name=아이티단자, given_name=단자,email=kgs2072@naver.com}
            email =  (String)oAuth2User.getAttributes().get( "email" );  log.info(" google name : " + email ); // 구글의 이메일 호출
            name =  (String)oAuth2User.getAttributes().get( "name" );    log.info(" google email : " + name );  // 구글의 이름 호출
        }

        // 인가 객체 [ OAuth2User----> MemberDto 통합Dto( 일반+oauth) ]
        MemberDto memberDto = new MemberDto();
        memberDto.set소셜회원정보( oAuth2User.getAttributes() );
        memberDto.setMemail( email );
        memberDto.setMname( name );
            Set<GrantedAuthority> 권한목록 = new HashSet<>();
            SimpleGrantedAuthority 권한 = new SimpleGrantedAuthority("ROLE_user");
            권한목록.add( 권한 );
        memberDto.set권한목록( 권한목록 );
        // 1. DB 저장하기 전에 해당 이메일로 된 이메일 존재하는지 검사
        MemberEntity entity = memberEntityRepository.findByMemail( email );
        if( entity == null ){ // 첫방문
            // DB 처리 [ 첫 방문시에만 db등록  , 두번째 방문시 부터는 db수정  ]
            memberDto.setMrole("oauthuser"); // DB에 저장할 권한명
            memberEntityRepository.save( memberDto.toEntity() );
        }else{// 두번째 방문 이상 수정 처리
            entity.setMname( name );
        }
        memberDto.setMno( entity.getMno() ); // 위에 생성된 혹은 검색된 엔티티의 회원번호
        return memberDto;
    }

    @Autowired
    private MemberEntityRepository memberEntityRepository;
    @Autowired private HttpServletRequest request; // 요청 객체

    // 1. 일반 회원가입 [ 본 애플리케이션에서 가입한 회원  ]
    @Transactional
    public boolean write( MemberDto memberDto ){
        // 스프링 시큐리티에서 제공하는 암호화[ 사람이 이해하기 어렵고 컴퓨터는 이해할수 있는 단어 ] 사용하기
            // DB 내에서 패스워드 감추기 , 정보가 이동하면 패스워드 노출 방지
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // 인코더 : 특정 형식으로변경  // 디코더 : 원본으로 되돌리기
            log.info("비크립트 암호화 사용 : " + passwordEncoder.encode( "1234") );
        // 입력받은[DTO] 패스워드 암호화 해서 다시 DTO에 저장하자.
        memberDto.setMpassword( passwordEncoder.encode( memberDto.getMpassword() ) );

        // 등급/권한 부여
        memberDto.setMrole("user");

        MemberEntity entity = memberEntityRepository.save( memberDto.toEntity() );
        if( entity.getMno() > 0 ){ return  true;}
        return false;
    }
    // **** 로그인 [ 시큐리티 사용 했을때 ]
        // 시큐리티는 API [ 누군가 미리 만들어진 메소드 안에서 커스터마이징[ 수정 ]

    // *2 로그인 [ 시큐리티 사용 하기 전 ]
    /*
    @Transactional
    public boolean login( MemberDto memberDto  ){
        // 1. 입력받은 이메일 로 엔티티 찾기
        MemberEntity entity = memberEntityRepository.findByMemail( memberDto.getMemail() );  log.info( "entity : " + entity );
         // 2. 찾은 엔티티 안에는 암호화된 패스워드
            // 엔티티안에 있는 패스워드[암호화된상태] 와 입력받은 패스워드[안된상태] 와 비교
        if( new BCryptPasswordEncoder().matches(  memberDto.getMpassword() , entity.getMpassword() )  ) {
           // 세션 사용 : 메소드 밖 필드에 @Autowired private HttpServletRequest request;
            request.getSession().setAttribute("login", entity.getMemail() );
            return true;
        }
        *//*
        // 2. 입력받은 이메일과 패스워드가 동일한지
        Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
        log.info("result : " + result  );
        if( result.isPresent()){
            request.getSession().setAttribute("login", result.get().getMno() );
            return  true;
        }

        *//*
        *//*
        // 3.
        boolean result = memberEntityRepository.existsByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
            log.info( "result : " + result );
        if( result == true ){ request.getSession().setAttribute("login", memberDto.getMemail()); return true; }
        *//*
        return false;
    }
    */

    // 3. 회원수정
    @Transactional
    public boolean update(  MemberDto memberDto ){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( memberDto.getMno() );
        if( entityOptional.isPresent() ){
            MemberEntity entity = entityOptional.get();
            entity.setMname( memberDto.getMname());  entity.setMphone( memberDto.getMphone() );
            entity.setMrole( memberDto.getMrole() ); entity.setMpassword( memberDto.getMpassword());
            return true;
        }
        return false;
    }
    // 4. 회원탈퇴
    @Transactional
    public boolean delete( int mno ){
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( mno );
        if( entityOptional.isPresent()){
            MemberEntity entity = entityOptional.get();
            memberEntityRepository.delete( entity );
            return true;
        }
        return false;
    }

    // 5. 아이디 중복 확인
    public boolean idcheck( String memail ){
        return memberEntityRepository.existsByMemail( memail  );
    }


    /*
    // 2. [ 세션에 존재하는 정보 제거 ] 로그아웃
    @Transactional
    public boolean logout(){ request.getSession().setAttribute("login",null); return true; }
     */


    // [ 스프링 시큐리티 적용했을때 사용되는 로그인 메소드 ]
    @Override
    public UserDetails loadUserByUsername(String memail ) throws UsernameNotFoundException {
        // 1. UserDetailsService 인터페이스 구현
        // 2. loadUserByUsername() 메소드 : 아이디 검증
            // 패스워드 검증 [ 시큐리티 자동 ]
        MemberEntity entity = memberEntityRepository.findByMemail(memail);
        if( entity == null ){ throw new UsernameNotFoundException("해당 계정의회원이 없습니다."); }
        // 3. 검증후 세션에 저장할 DTO 반환
        MemberDto dto = entity.todto();
            // Dto 권한(여러개) 넣어주기
        // 1. 권한목록 만들기
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
        // 2. 권한객체 만들기 [ DB 존재하는 권한명( ROLE_!!!! )으로  ]
            // 권한 없을경우 : ROLE_ANONYMOUS  / 권한 있을경우 ROLE_권한명 : ROLE_admin , ROLE_user
        SimpleGrantedAuthority 권한명1 = new SimpleGrantedAuthority( "ROLE_"+entity.getMrole() );
        // 3. 만든 권한객체를 권한목록[컬렉션]에  추가
        권한목록.add( 권한명1 );
        // 4. UserDetails 에 권한목록 대입
        dto.set권한목록( 권한목록 );

        log.info( "dto:" + dto);
        return dto; // UserDetails : 원본데이터의 검증할 계정 , 패스워드 포함
    }
    // 2. [세션에 존재하는 ] 회원정보
    @Transactional
    public MemberDto info(  ){
        // 1. 시큐리티 인증[로그인] 된 UserDetails객체[세션]로 관리 했을때 [ Spring ]
            // SecurityContextHolder : 시큐리티 정보 저장소
            // SecurityContextHolder.getContext()  : 시큐리티 저장된 정보 호출
            // SecurityContextHolder.getContext().getAuthentication();  // 인증 전체 정보 호출
             log.info(" Auth : " + SecurityContextHolder.getContext().getAuthentication() );
             log.info(" Auth : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 인증된 회원의 정보 호출
        if( o.equals("anonymousUser") ){ return null; }
            // [ Principal ]  // 인증  실패시 : anonymousUser   // 인증  성공시 : 회원정보[Dto]
        // 2. 인증된 객체내 회원정보[ Principal ] 타입 변환
        return (MemberDto)o; //  Object ---> dto
        /*
        // 2. 일반 세션으로 로그인 정보를 관리 했을때 [ JSP ]
        String memail = (String)request.getSession().getAttribute("login");
        if( memail != null ){
            MemberEntity entity = memberEntityRepository.findByMemail( memail );
            return entity.todto();
        }
        return null;
         */
    }


}
