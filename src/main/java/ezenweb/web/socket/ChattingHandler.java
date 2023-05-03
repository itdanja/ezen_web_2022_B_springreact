package ezenweb.web.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component // 빈 등록 [ 스프링 해당 클래스를 관리 = 제어역전 IOC ]
@Slf4j // 로그
public class ChattingHandler extends TextWebSocketHandler {
    // 0. ** 서버소켓에 접속한 세션 명단 저장
    private static List<WebSocketSession> 접속명단 = new ArrayList<>();

    @Override   // 1. 클라이언트가 서버소켓으로 부터 접속했을때
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished : " + session );
        접속명단.add( session ); // 클라이언트 세션이 들어왔을때 리스트에 저장 [ 왜???다른 세션과 통신하기 위해 ]
    }
    @Override   // 2. 클라이언트로 부터 메시지를 받았을때
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage : " + session );
        log.info("handleTextMessage : " + message );

        for( WebSocketSession 세션 : 접속명단 ){ 세션.sendMessage( message );  }

    }
    @Override   // 3. 클라이언트가 서버소켓으로 부터 나갔을때
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed : " + session );
        log.info("afterConnectionClosed : " + status );
        접속명단.remove( session ); // 클라이언트 세션이 나갔을때 리스트에 제거
    }
}
