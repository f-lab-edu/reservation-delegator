package org.flab.reservation.handler.websocket;

import lombok.extern.slf4j.Slf4j;
import org.flab.reservation.session.websocket.SessionManager;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class PushHandler extends AbstractWebSocketHandler {

    public PushHandler(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //TODO Client에게 Push해야할 메시지들을 처리
        super.handleTextMessage(session, message);
    }
}
