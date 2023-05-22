package ezenweb.web.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    // * 첨부파일이 저장 될 경로 [ 1. 배포 전 2.배포 후 ]
    // 1. 업로드 , 다운로드
    // String path = "C:\\java\\"; // spring서버 로컬드라이브[c] 접근 가능

    // 2. 업로드 , 다운로드 , 리액트 리소스 접근
    // JS[react] 로컬드라이브[c] 접근 불가능--> 리액트서버 에 업로드
    // springboot + react 통합
    // react build --> spring resources --> spring build --> 프로젝트내 build
    //String path = "C:\\Users\\504t\\Desktop\\team\\ezen_web_2022_B_springreact\\build\\resources\\main\\static\\static\\media\\";
    //String path ="/home/ec2-user/app/ezen_web_2022_B_springreact/build/resources/main/static/static/media/";

    // 3. AWS S3 리소드 업로드 , 다운로드
    // 업로드[바이트복사해서 이동하는 개념 ]
    // 1. S3 객체 생성
    @Autowired // 그레이들에 implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE'
    private AmazonS3Client amazonS3Client;

    // **** application.properties 에 버킷 설정값 가져와서 변수에 저장 [ 서비스에 보완에 관련된 코드 숨기기 ]
    @Value("${cloud.aws.s3.bucket}") // lombok 아님..
    private String bucket ; // application.properties 에 설정한 버킷명 가져오기
    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;// application.properties 에 설정한 버킷 저장 경로

    // 2. S3 업로드 함수 선언
    public void uploadS3( String fileName , File file ) {
        // 1. aws s3 전송 객체 생성
        TransferManager transferManager = new TransferManager( amazonS3Client );
        // 2. 요청 객체
        // PutObjectRequest putObjectRequest = new PutObjectRequest("버킷명" , 파일명 , 업로드할 파일객체 );
        PutObjectRequest putObjectRequest = new PutObjectRequest( bucket , fileName , file );
        // 3. 업로드 객체 생성[대기]
        Upload upload = transferManager.upload( putObjectRequest );
        // 4. 업로드 객체 업로드 실행
        try {   upload.waitForCompletion();}
        catch (InterruptedException e) {  throw new RuntimeException(e);  }
    }

    // 3. s3 리소스 삭제 함수 선언
    public void deleteS3( String uuidFile ){
        // 1. 삭제 요청 객체 생성
        // DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest( bucket , 파일명[url제거] );
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest( bucket , uuidFile.replace( defaultUrl , "" ) );
        // 2. 삭제 요청
        amazonS3Client.deleteObject( deleteObjectRequest );
    }

    String s3url;
    public FileDto fileupload( MultipartFile multipartFile ){
        // 1. 첨부파일 존재하는지 확인
        if( !multipartFile.getOriginalFilename().equals("") ){ // 첨부파일이 존재하면
            // * 만약에 다른 이미지인데 파일이 동일하면 중복발생[ 식별 불가 ] : UUID + 파일명
            String fileName =
                    UUID.randomUUID().toString() +"_"+
                            multipartFile.getOriginalFilename().replaceAll("_","-");
            // 2.  경로 + UUID파일명  조합해서 file 클래스의 객체 생성 [ 왜?? 파일?? transferTo ]
            File file = new File( System.getProperty("user.dir") + fileName );
            // 3. 업로드 // multipartFile.transferTo( 저장할 File 클래스의 객체 );

            try {
                multipartFile.transferTo(file);  // 로컬 파일 업로드

                uploadS3( fileName , file );   // *** S3 클라우드 업로드
                s3url = defaultUrl + fileName; // 업로드 된 s3리소스 경로
                file.delete();  // 로컬 파일 삭제

            }catch ( Exception e ) { log.info("file upload fail : " + e ); }
            // 4. 반환
            return FileDto.builder()
                    .originalFilename( multipartFile.getOriginalFilename() )
                    .uuidFile( s3url )
                    .sizeKb( multipartFile.getSize()/1024 + "kb" )
                    .build();
        }
        return null;
    }

    @Autowired
    private HttpServletResponse response; // 응답 객체

    public void filedownload( String uuidFile ){ // spring 다운로드 관한 API 없음
        // String pathFile = path + uuidFile; // 경로+uuid파일명 : 실제 파일이 존재하는 위치
        // 리소드 전체 경로 :  https://0522springreact.s3.ap-northeast-2.amazonaws.com/51583b88-5186-43e5-b1fe-3491c981ab83_춘식이.png
        String uuidFileName = uuidFile.replace( defaultUrl , "");
        // 경로 제거후 파일명 추출  :  51583b88-5186-43e5-b1fe-3491c981ab83_춘식이.png
        String fileName = uuidFileName.split("_")[1];
        // uuid 제거 순수파일명 추출  : 춘식이.png

        try {
            // 1. 다운로드 형식 구성
            response.setHeader(  "Content-Disposition", // 헤더 구성 [ 브라우저 다운로드 형식 ]
                    "attchment;filename = " + URLEncoder.encode( fileName , "UTF-8") // 다운로드시 표시될 이름
            );

                // 11111. 로컬 일 경우
//            //2. 다운로드 스트림 구성
//            File file = new File( pathFile ); // 다운로드할 파일의 경로에서 파일객체화
//            // 3. 입력 스트림 [  서버가 먼저 다운로드 할 파일의 바이트 읽어오기 = 대상 : 클라이언트가 요청한 파일 ]
//            BufferedInputStream fin = new BufferedInputStream( new FileInputStream(file) );
//            byte[] bytes = new byte[ (int) file.length() ]; // 파일의 길이[용량=바이트단위] 만큼 바이트 배열 선언
//            fin.read( bytes ); // 읽어온 바이트들을 bytes배열 저장

                // 222222. s3 일경우  [ 버킷에서 해당 리소드객체를 바이트로 가져오기
            // 1. 불러오기할 버킷의 리소드 호출
            // GetObjectRequest getObjectRequest = new GetObjectRequest( 버킷명 , 파일명(경로제외) );
            GetObjectRequest getObjectRequest = new GetObjectRequest( bucket , uuidFileName );
            // 2. 요청된 리소스 객체를 생성
            S3Object s3Object = amazonS3Client.getObject( getObjectRequest );
            // 3. s3 스트림 객체 생성
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
            // 4. s3 스트림을 이용해서 읽어온 바이트를 배열 저장 [ IOUtils :amazon ]
            byte[] bytes = IOUtils.toByteArray( objectInputStream);

            // 4. 출력 스트림 [ 서버가 읽어온 바이트를 출력할 스트림  = 대상 : response = 현재 서비스 요청한 클라이언트에게 ]
            BufferedOutputStream fout = new BufferedOutputStream( response.getOutputStream() );
            fout.write( bytes ); // 입력스트림에서 읽어온 바이트 배열을 내보내기
            fout.flush(); // 스트림 메모리 초기화
            fout.close(); // 스트림 닫기
            //fin.close();
        }catch(Exception e){ log.info("file download fail : "+e );}
    }

}
