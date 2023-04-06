package ezenweb.example.day04.domain.dto;

import ezenweb.example.day04.domain.entity.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data // setter , getter , toString
@AllArgsConstructor @NoArgsConstructor // 풀생성자,빈생성자
@Builder // 빌더패턴
public class ProductDto {
    private int pno;
    private String pname;
    private String pcontent;
    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .pno( this.pno).pcontent( this.pcontent)
                .pname( this.pname).build();
    }
}
