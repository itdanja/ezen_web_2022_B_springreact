package ezenweb.web.domain.todo;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {
    private int id; // Todo 식별번호
    private String title; // Todo 내용
    private boolean done; // 체크여부
}
