package ezenweb.web.controller;

import ezenweb.web.domain.product.ProductDto;
import ezenweb.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @Slf4j @RequestMapping("/product")
public class ProductController {
    @Autowired private ProductService productService;
    @GetMapping("")     public List<ProductDto> get(){ return productService.get();}
    @PostMapping("")    public boolean post(@RequestBody ProductDto productDto){  return productService.post(productDto);}
    @PutMapping("")     public boolean put(@RequestBody ProductDto productDto){ return productService.put(productDto);}
    @DeleteMapping("")  public boolean delete( @RequestParam String pno ){  return productService.delete(pno);}
}
/*
{
  "pno" : 1,
  "pname":1,
  "pprice":1,
  "pcategory":1,
  "pcomment":1,
  "pmanufacturer":1,
  "pstate":1,
 "pstock":1
}

 */