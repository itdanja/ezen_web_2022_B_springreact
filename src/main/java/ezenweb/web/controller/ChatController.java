package ezenweb.web.controller;

import ezenweb.web.domain.file.FileDto;
import ezenweb.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private FileService fileService;
    @PostMapping("/fileupload") // chat 관련 첨부파일 업로드
    public FileDto fileupload(@RequestParam("attachFile") MultipartFile multipartFile ){
        FileDto result = fileService.fileupload( multipartFile );
        return result;
    }
    @GetMapping("/filedownload") // char 관련 첨부파일 다운로드
    public void filedownload( @RequestParam("uuidFile") String uuidFile ){
        fileService.filedownload( uuidFile );
    }

}
