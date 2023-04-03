package ezenweb.example.day01;

import lombok.Builder;

import javax.persistence.*; // !!!!!:

@Entity // 엔티티 = DB 테이블 매핑 = 테이블 = 객체
@Table(name="testmember2") // 테이블 이름 정하기 = 생략시 클래스명으로 테이블명 만들어짐
@Builder
public class Testmember2 {
    // DB 테이블 필드 선언
    @Id // PK 필드 선언
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto key
    private int mno;
    @Column
    private String mid;
    @Column
    private String mpassword;
    @Column
    private long mpoint;
    @Column
    private String mphone;
}