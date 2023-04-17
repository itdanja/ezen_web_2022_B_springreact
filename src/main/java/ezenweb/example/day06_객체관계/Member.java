package ezenweb.example.day06_객체관계;

import lombok.ToString;

import java.util.ArrayList;


public class Member {
    public int 번호;
    public String 아이디;
    public ArrayList<Board> 게시물 = new ArrayList<Board>();
}
