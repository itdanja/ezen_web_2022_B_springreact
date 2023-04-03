package ezenweb.example.day01;

public class Ex2 {
    public static void main(String[] args) {

        // 1. builder 패턴을 이용한 객체 생성
        LombokDto dto = LombokDto.builder()
                .mno(1) .mid("qwe") .mpassword("qwe")
                .phone("010-4444-444").mpoint(1000)
                .build();
        // 2. @ToString 이용한 메소드 사용
        System.out.println( "dto : " + dto.toString());

        // 3. Dao 이용한 db처리
        Dao dao = new Dao();
        // 3. db에 저장하기
        boolean result = dao.setmember( dto );
        System.out.println( "result : " + result );

        // 4. JPA 이용한 DB처리


    }
}
