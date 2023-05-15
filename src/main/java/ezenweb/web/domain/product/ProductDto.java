package ezenweb.web.domain.product;

import ezenweb.web.domain.file.FileDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductDto {
    private String id;
    private String pname;
    private int pprice;
    private String pcategory;
    private String pcommnet;
    private String pmanufacturer;
    private byte pstate;
    private int pstock;
    // 관리자용
    private String cdate;
    private String udate;
    // 첨부파일 입력용
    private List<MultipartFile> pimgs ;
    // 첨부파일 출력용
    private List<FileDto> files = new ArrayList<>();

    // 1. 저장용 [ 관리자 페이지 ]
    public ProductEntity toSaveEntity(){
        return ProductEntity.builder()
                .id( this.id ).pname( this.pname )
                .pprice( this.pprice).pcategory( this.pcategory)
                .pcommnet( this.pcommnet ).pmanufacturer( this.pmanufacturer )
                .pstate( this.pstate ).pstock( this.pstock )
                .build();
    }
}
