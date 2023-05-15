package ezenweb.web.domain.product;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.file.FileDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
@Entity@Table(name="product")
public class ProductEntity extends BaseTime {
    @Id private String id;                                  // 제품번호 [ JPA는 1개 이상 ID=PK 필요! ] / 오토키x -> 오늘날짜/밀리초
    @Column( nullable = false)  private String pname;       // 제품명 [ JPA로 DB 필드 선언시 _ : 제외  ]
    @Column( nullable = false)  private int pprice;         // 제품가격
    @Column( nullable = false)  private String pcategory;   // 제품카테고리
    @Column( nullable = true ,columnDefinition = "TEXT" ) private String pcommnet; // 제품설명
    @Column( nullable = false , length = 100 ) private String pmanufacturer; // 제조사
    @ColumnDefault("0")@Column( nullable = false) private byte pstate; // 제품상태 [ 0 : 판매중 , 1 : 판매중지 , 2 : 재고없음 ]
    @ColumnDefault("0")@Column( nullable = false) private int pstock; // 제품 재고/수량
    // 제품이미지 [ 1 : 다 ] 연관관계 [* 추후 ]
    // pk 필드 선언시 //  mappedBy="참조할필드명" // 제약조건 : pk객체가 삭제되면 fk객체의 제약조건
    @OneToMany( mappedBy = "productEntity" , cascade = CascadeType.REMOVE)
    @ToString.Exclude @Builder.Default
    private List<ProductImgEntity> productImgEntityList = new ArrayList<>();
    // 구매내역 [ 1 : 다 ] 연관관계 [ *추후 ]

    // 1. 출력용 [ 관리자보는 입장 - 관리자 페이지에서 출력용  ]
    public ProductDto toAdminDto(){
        return ProductDto.builder()
                .id( this.id ).pname( this.pname )
                .pprice( this.pprice).pcategory( this.pcategory)
                .pcommnet( this.pcommnet ).pmanufacturer( this.pmanufacturer )
                .pstate( this.pstate ).pstock( this.pstock )
                .cdate( this.cdate.toString() )
                .udate( this.udate.toString() )
                .build();
    }
    // 2. 출력용 [ 사용자보는 입장 - 메인 페이지에서 출력용 ]
    public ProductDto toMainDto(){
        List<FileDto> list =
            this.getProductImgEntityList().stream().map(

                    imgEntity -> imgEntity.toFileDto()

            ).collect(Collectors.toList() );

        return ProductDto.builder()
                .id( this.id ).pname( this.pname )
                .pprice( this.pprice ).pcategory(this.pcategory)
                .pmanufacturer( this.pmanufacturer )
                .files( list )
                .build();
    }
}
/*
제약조건 : pk객체가 삭제되면 fk객체의 제약조건
	cascade = CascadeType.ALL 		: pk객체 삭제/수정 시 fk객체 모두 삭제/수정
	cascade = CascadeType.MERGE 	: pk객체 수정시 fk객체도 모두 수정
	cascade = CascadeType.REMOVE 	: pk객체 삭제시 fk객체 모두 삭제
	    vs @OnDelete(action = OnDeleteAction.CASCADE)
	cascade = CascadeType.DETACH 	: pk객체 삭제/수정에 변경사항 fk에 반영 안함

 */
