package ezenweb.web.socket;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder
public class FileDto {

    private String originalFilename;
    private String fileName;
    private String path;
    private String size;
    private String contentType;

}
