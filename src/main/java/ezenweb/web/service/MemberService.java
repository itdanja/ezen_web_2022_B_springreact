package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service // 서비스 레이어
@Slf4j
public class MemberService implements UserDetailsService {



    @Autowired
    private MemberEntityRepository memberEntityRepository;
    @Autowired private HttpServletRequest request; // 요청 객체

    // 1. 회원가입
    @Transactional
    public boolean write( MemberDto memberDto ){
        // 스프링 시큐리티에서 제공하는 암호화[ 사람이 이해하기 어렵고 컴퓨터는 이해할수 있는 단어 ] 사용하기
            // DB 내에서 패스워드 감추기 , 정보가 이동하면 패스워드 노출 방지
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // 인코더 : 특정 형식으로변경  // 디코더 : 원본으로 되돌리기
            log.info("비크립트 암호화 사용 : " + passwordEncoder.encode( "1234") );
        // 입력받은[DTO] 패스워드 암호화 해서 다시 DTO에 저장하자.
        memberDto.setMpassword( passwordEncoder.encode( memberDto.getMpassword() ) );

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
        if( entity == null ){ return null; }
        // 3. 검증후 세션에 저장할 DTO 반환
        MemberDto dto = entity.todto();
        log.info( "dto:" + dto);
        return dto;
    }
    // 2. [세션에 존재하는 ] 회원정보
    @Transactional
    public MemberDto info(  ){
        // 1. 시큐리티 인증[로그인] 된 UserDetails객체[세션]로 관리 했을때 [ Spring ]
            // SecurityContextHolder : 시큐리티 정보 저장소
            // SecurityContextHolder.getContext()  : 시큐리티 저장된 정보 호출
            // SecurityContextHolder.getContext().getAuthentication();  // 인증 전체 정보 호출
            // log.info(" Auth : " + SecurityContextHolder.getContext().getAuthentication() );
            // log.info(" Auth : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 인증된 회원의 정보 호출
        if( o == null ){ return null; }

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
