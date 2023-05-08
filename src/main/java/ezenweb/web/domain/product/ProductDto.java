package ezenweb.web.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Id;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductDto {
    private String id;     // 제품번호 // 오토키가 아닌 직접 설정해주기[오늘날짜+]
    private String pname;   // 제품명
    private int pprice;     // 제품가격
    private String pcategory; // 제품카테고리 / 상위 테이블 없이 진행
    private String pcomment; // 제품설명
    private String pmanufacturer; // 제조사
    private byte pstate; // 제품 상태 [ 0:판매중 / 1:판매중지 / 2: 재고없음 ]
    private byte pstock; // 제품 재고/수량

    // 1. 저장용 [ dto -> entity ]
    public ProductEntity toSaveEntity(){
        return ProductEntity.builder()
                .pno( this.id)
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













