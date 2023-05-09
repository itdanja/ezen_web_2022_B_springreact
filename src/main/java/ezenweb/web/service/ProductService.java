package ezenweb.web.service;

import ezenweb.web.domain.product.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ProductService {       /* 주요기능과 DB처리 요청 역찰[ Transactional ] */
    @Transactional      public List<ProductDto> get(){ log.info("get : "); return null;  }
    @Transactional      public boolean post(ProductDto productDto){ log.info("post : " + productDto);  return false; }
    @Transactional      public boolean put(ProductDto productDto){ log.info("put : " + productDto);  return false; }
    @Transactional      public boolean delete(int id ){ log.info("delete : " + id);  return false; }
}
