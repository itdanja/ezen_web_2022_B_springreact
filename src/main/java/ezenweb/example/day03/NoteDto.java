package ezenweb.example.day03;

import lombok.*;

// 롬복 사용
@Data   // getter , setter , toString
@AllArgsConstructor @NoArgsConstructor @Builder
public class NoteDto {
    private int nno;
    private String ncontents;
}
