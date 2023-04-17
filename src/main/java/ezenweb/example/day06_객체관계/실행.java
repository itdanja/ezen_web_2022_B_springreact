package ezenweb.example.day06_객체관계;

public class 실행 {
    public static void main(String[] args) {

        // 1. 회원가입 [ 회원객체 ]
        Member 유재석 = new Member();
        유재석.번호 = 1; 유재석.아이디="qwe";

        // 2. 글쓰기  [ 글 객체 ]
        Board board1 = new Board();
        board1.번호 = 1; board1.내용 ="하하";

        // * 두 객체는 서로 연관성이 없다.
            // 글에 작성자 객체 추가
            // 작성자 추가
        board1.작성자 = 유재석;
            // 유재석 글 추가
        Board board2 = new Board();
        board2.번호 =2 ; board2.내용="카카"; board2.작성자 = 유재석;

        // ?? 게시물객체를 통해 member 객체 알수 있을까?
            // 1. 1번 게시물 작성자는?
        System.out.println( board1.작성자 );
            // 2. 2번 게시물 작성자??
        System.out.println( board2.작성자 );
        // 추가 ??? 회원객체를 통해 게시물 객체 알수 있을까??
            // 1. 유재석객체에 게시물1 대입
        유재석.게시물.add( board1 );
            // 2. 유재석객체에 게시물2 대입
        유재석.게시물.add( board2 );

        System.out.println( 유재석 );

    }
}
