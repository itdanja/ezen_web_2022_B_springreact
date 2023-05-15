package ezenweb.web.domain.product;

import ezenweb.web.domain.file.FileDto;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder
@Entity @Table(name="productimg")
public class ProductImgEntity {
    @Id private String uuidFile; // 1. 이미지식별이름
    @Column private String originalFilename;// 2. 이미지이름
    // 3. 제품객체[ fk ]
    @ManyToOne // fk 필드 선언시
    @JoinColumn(name="id") // DB테이블에 표시될 FK 필드명
    @ToString.Exclude // 순환참조 방지 [ 양방향일때 필수 ]
    private ProductEntity productEntity;

    public FileDto toFileDto(){
        return FileDto.builder()
                .uuidFile( this.uuidFile )
                .originalFilename( this.originalFilename )
                .build();
    }

}
