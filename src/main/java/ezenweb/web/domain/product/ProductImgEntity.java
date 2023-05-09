package ezenweb.web.domain.product;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder @Entity @Table(name="productimg")
public class ProductImgEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int pimgno;

    private String originalFilename;

    private String uuidFile;

    @ManyToOne
    @JoinColumn(name="id")
    @ToString.Exclude
    private ProductEntity productEntity;
}
