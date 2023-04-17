package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.*;

@Service @Slf4j
public class BoardService {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private BoardEntityRepository boardEntityRepository;
    @Autowired private MemberEntityRepository memberEntityRepository;

    // 1. 카테고리 등록
    @Transactional
    public boolean categoryWrite( BoardDto boardDto ){ log.info("s board dto : " + boardDto );
        CategoryEntity entity = categoryRepository.save( boardDto.toCategoryEntity() ); // 1. 입력받은 cname 을 Dto 에서 카테고리 entity 형변환 해서 save
        if( entity.getCno() >= 1 ){ return true; }  // 2. 만약에 생성된 엔티티의 pk가 1보다 크면 save 성공
        return false;
    }
    @Transactional
    public Map<Integer , String> categoryList(  ){
        List<CategoryEntity> entityList = categoryRepository.findAll();

        Map<Integer , String> map = new HashMap<>();
        entityList.forEach( (o)->{
            map.put( o.getCno() , o.getCname() );
        });

        return map;
    }

    // 2. 게시물 쓰기
    @Transactional
    public boolean write( BoardDto boardDto ){ log.info("s board dto : " + boardDto );
        // 1. 카테고리 엔티티 찾기
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById( boardDto.getCno() ); // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        if( !categoryEntityOptional.isPresent() ){ return false; }         // 2. 만액에 선택된 카테고리가 존재하지 않으면  리턴
        CategoryEntity categoryEntity = categoryEntityOptional.get();    // 3. 카테고리 엔티티 추출
        // 2.로그인된 회원의 엔티티 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 1. 인증된 인증 정보  찾기
        if( o.equals("anonymousUser") ){ return false;}
        MemberDto loginDto = (MemberDto)o;  // 2. 형변환
        MemberEntity memberEntity = memberEntityRepository.findByMemail( loginDto.getMemail() ); // 3. 회원엔티티 찾기
        // 3. 게시물 쓰기
        BoardEntity boardEntity = boardEntityRepository.save( boardDto.toBoardEntity() );
        if( boardEntity.getBno() < 1 ){ return  false; }
        // 4. 양방향 관계
        categoryEntity.getBoardEntityList().add( boardEntity ); // 1. 카테고리 엔티티에 생성된 게시물 등록
        boardEntity.setCategoryEntity( categoryEntity ); // 2. 생성된 게시물에 카테고리 엔티티 등록
        // 5. 양방향 관계
        boardEntity.setMemberEntity( memberEntity ); // 1. 생성된 게시물 엔티티에 로그인된 회원 등록
        memberEntity.getBoardEntityList().add(boardEntity);  // 2. 로그인된 회원 엔티티에 생성된 게시물 엔티티 등록

        return true;
    }
    @Transactional
    public ArrayList<BoardDto> list( int cno ){  log.info("c board dto : " + cno );


        List<BoardEntity> boardEntityList =  boardEntityRepository.findAll();
        ArrayList<BoardDto> list = new ArrayList<>();

        boardEntityList.forEach( (e) ->{
            list.add( e.toDto() );
        });

        return list;
    }

    // 3. 내가 쓴 게시물 출력
    public List<BoardDto> myboards( ){
        log.info("s myboards : " );

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 1. 인증된 인증 정보  찾기
        if( o.equals("anonymousUser") ){ return null;}
        MemberDto loginDto = (MemberDto)o;  // 2. 형변환
        MemberEntity memberEntity = memberEntityRepository.findByMemail( loginDto.getMemail() ); // 3. 회원엔티티 찾기

        ArrayList<BoardDto> list = new ArrayList<>();
        memberEntity.getBoardEntityList().forEach( (e) ->{
            list.add( e.toDto() );
        });


        return list;
    }

    public BoardDto view( int bno ){

        Optional<BoardEntity> optionalBoardEntity =  boardEntityRepository.findById(bno);

        if( optionalBoardEntity.isPresent() ){

            BoardEntity boardEntity =  optionalBoardEntity.get();
            return boardEntity.toDto();
        }

        return null;
    }

}
