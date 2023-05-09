package ezenweb.web.domain.file;

import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class FileDto {
    private int id;
    private String originalFilename; // 실제 순수 파일명;
    private String uuidFile; // 식별이 포함된 파일명 ;
    private String sizeKb; // 용량 kb;
}
