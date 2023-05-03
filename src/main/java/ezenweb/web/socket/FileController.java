package ezenweb.web.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

@RestController
public class FileController {

    String path ="C:\\Users\\504t\\Desktop\\ezen_web_2022_B_springreact\\src\\main\\resources\\static\\static\\media\\";

    @PostMapping("/chat/fileupload")
    public String file( @RequestParam("attachFile")  MultipartFile multipartFile ){

        if( !multipartFile.getOriginalFilename().equals("") ){
            File uploadfile = new File(path + multipartFile.getOriginalFilename() );
            try{  multipartFile.transferTo(uploadfile);
                return multipartFile.getOriginalFilename();
            }catch ( Exception e ){}
        }
        return null;
    }
    @Autowired
    private HttpServletRequest request; // 요청 객체 선언
    @Autowired
    private HttpServletResponse response; // 응답 객체 선언

    // 0. 첨부파일 다운로드
    @GetMapping("/chat/filedownload")
    public void filedownload(  @RequestParam("filename") String filename ){
        String filepath = path+filename;
        try {  // 2. 헤더 구성 [ HTTP 해서 지원하는 다운로드형식 메소드 [ response ]
            response.setHeader( // 응답
                    "Content-Disposition", // 다운로드 형식 [ 브라우저 마다 다름 ]
                    "attachment;filename=" + URLEncoder.encode(filename, "UTF-8")); // 다운로드에 표시될 파일명
            File file = new File(filepath); // 해당 경로의 파일 객체화
            // 3. 다운로드 스트림 [ ]
            BufferedInputStream fin = new BufferedInputStream( new FileInputStream(file)  ); // 1. 입력 스트림 객체 선언
            byte[] bytes = new byte[ (int)file.length() ];  // 2. 파일의 길이만큼 배열 선언
            fin.read( bytes );      // * 스트림 읽기 [ 대상 : new FileInputStream(file) ] // 3. 파일의 길이만큼 읽어와서 바이트를 배열에 저장
            BufferedOutputStream fout = new BufferedOutputStream( response.getOutputStream() ); // 4. 출력 스트림 객체 선언
            fout.write( bytes );    // * 스트림 내보내기   [ response.getOutputStream() ]  // 5. 응답하기 [ 배열 내보내기]
            fout.flush(); fout.close(); fin.close();  // 6. 버퍼 초기화 혹은 스트림 닫기
        }catch(Exception e){ System.out.println(e);  }
    }



}
