package ezenweb.web.service;

import ezenweb.web.domain.product.ProductDto;
import ezenweb.web.domain.product.ProductEntity;
import ezenweb.web.domain.product.ProductEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service @Slf4j
public class ProductService {
    @Autowired          private ProductEntityRepository productEntityRepository;
    @Transactional      public List<ProductDto> get(){

        log.info("get : " );

        List<ProductEntity> productEntityList = productEntityRepository.findAll();
        List<ProductDto> productDtoList = productEntityList.stream().map( o->  o.toOutputDto() ).collect(Collectors.toList());

        return productDtoList;

    }
    @Transactional      public boolean post( ProductDto productDto){

        log.info("post : "+productDto );
        String number = "";
        for ( int i = 0 ; i<3 ;i++ ){number+=new Random().nextInt(10);}
        String pno = LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyyMMddSSS") ) + number ;
        productDto.setId( pno );
        ProductEntity entity = productEntityRepository.save( productDto.toSaveEntity() );
        return true;
    }
    @Transactional      public boolean put( ProductDto productDto){
        log.info("put : "+productDto );


        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById( productDto.getId() );
        if( productEntityOptional.isPresent() ){
            ProductEntity productEntity = productEntityOptional.get();
            productEntity.setPcategory( productDto.getPcategory() );
            productEntity.setPcomment( productDto.getPcomment() );
            productEntity.setPprice( productDto.getPprice() );
            productEntity.setPstate( productDto.getPstate() );
            productEntity.setPname( productDto.getPname() );
            productEntity.setPmanufacturer( productDto.getPmanufacturer() );
            productEntity.setPstock( productDto.getPstock());
            return true;
        }



        return false;

    }
    @Transactional      public boolean delete(  String pno ){
        log.info("delete : "+pno );


        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById( pno );
        if( productEntityOptional.isPresent() ){
            productEntityRepository.delete( productEntityOptional.get() );
            return true;
        }

        return false;


    }
}
