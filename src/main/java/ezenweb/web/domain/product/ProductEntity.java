package ezenweb.web.domain.product;

import ezenweb.web.domain.BaseTime;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Entity @Table(name = "product")
public class ProductEntity extends BaseTime {
    @Id private String pno;     // 제품번호 // 오토키가 아닌 직접 설정해주기[오늘날짜+]
    @Column private String pname;   // 제품명
    @Column private int pprice;     // 제품가격
    @Column private String pcategory; // 제품카테고리 / 상위 테이블 없이 진행
    @Column private String pcomment; // 제품설명
    @Column private String pmanufacturer; // 제조사
    @Column @ColumnDefault("0")private byte pstate; // 제품 상태 [ 0:판매중 / 1:판매중지 / 2: 재고없음 ]
    @Column @ColumnDefault("0")private byte pstock; // 제품 재고/수량

    // 1. 출력용 [ entity -> dto ]
    public ProductDto toOutputDto(){
        return ProductDto.builder()
                .id( this.pno)
                .pname( this.pname)
                .pprice( this.pprice)
                .pcategory(this.pcategory)
                .pcomment(this.pcomment)
                .pmanufacturer( this.pmanufacturer)
                .pstate(this.pstate)
                .pstock(this.pstock)
                .build();
    }

}
/*
@Cascade: CascadeType을 설정합니다. PERSIST, MERGE, REMOVE, REFRESH, DETACH 중 하나 또는 여러 개를 설정할 수 있습니다.

 */
