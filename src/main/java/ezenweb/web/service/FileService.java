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

    // 버킷 이름 동적 할당
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 버킷 주소 동적 할당
    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;

    public FileService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }


    public void fileDelete(String fileUrl)  {
        log.info(fileUrl.replace(defaultUrl,""));
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileUrl.replace(defaultUrl,"")));
    }


    private void uploadOnS3(final String findName, final File file) {
        // AWS S3 전송 객체 생성
        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        // 요청 객체 생성
        final PutObjectRequest request = new PutObjectRequest(bucket, findName, file);
        // 업로드 시도
        final Upload upload =  transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            log.error(amazonClientException.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }



    // * 첨부파일이 저장 될 경로 [ 1. 배포 전 2.배포 후 ]
    // 1. 업로드 , 다운로드
    // String path = "C:\\java\\"; // spring서버 로컬드라이브[c] 접근 가능

    // 2. 업로드 , 다운로드 , 리액트 리소스 접근
    // JS[react] 로컬드라이브[c] 접근 불가능--> 리액트서버 에 업로드
    // springboot + react 통합
    // react build --> spring resources --> spring build --> 프로젝트내 build
    //String path = "C:\\Users\\504t\\Desktop\\team\\ezen_web_2022_B_springreact\\build\\resources\\main\\static\\static\\media\\";
    //String path ="/home/ec2-user/app/ezen_web_2022_B_springreact/build/resources/main/static/static/media/";


    String url;
    public FileDto fileupload( MultipartFile multipartFile ){

        log.info("File upload : " + multipartFile);
        log.info("File upload : " + multipartFile.getOriginalFilename() ); // 실제 첨부파일 파일명
        log.info("File upload : " + multipartFile.getName() );              // input name
        log.info("File upload : " + multipartFile.getContentType() );       // 첨부파일 확장자
        log.info("File upload : " + multipartFile.getSize() );              // 99 995 바이트
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

                multipartFile.transferTo(file);

                // S3 파일 업로드
                uploadOnS3(fileName, file);

                url = defaultUrl + fileName;

                file.delete();

                log.info( url );

            }catch ( Exception e ) { log.info("file upload fail : " + e ); }
            // 4. 반환
            return FileDto.builder()
                    .originalFilename( multipartFile.getOriginalFilename() )
                    .uuidFile( url )
                    .sizeKb( multipartFile.getSize()/1024 + "kb" )
                    .build();

        }
        return null;
    }

    @Autowired
    private HttpServletResponse response; // 응답 객체

    public void filedownload( String uuidFile ){ // spring 다운로드 관한 API 없음


        String pathFile =  uuidFile; // 경로+uuid파일명 : 실제 파일이 존재하는 위치
        try {
            // 1. 다운로드 형식 구성
            response.setHeader(  "Content-Disposition", // 헤더 구성 [ 브라우저 다운로드 형식 ]
                    "attchment;filename = " + URLEncoder.encode(   uuidFile.replace(defaultUrl,"").split("_")[1], "UTF-8") // 다운로드시 표시될 이름
            );

            S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, uuidFile.replace(defaultUrl,"") ));
            S3ObjectInputStream objectInputStream = o.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(objectInputStream);


            BufferedOutputStream fout = new BufferedOutputStream( response.getOutputStream() );
            fout.write( bytes ); // 입력스트림에서 읽어온 바이트 배열을 내보내기
            fout.flush(); // 스트림 메모리 초기화
             fout.close(); // 스트림 닫기

        }catch(Exception e){ log.info("file download fail : "+e );}
    }

}
