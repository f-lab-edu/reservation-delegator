package org.flab.reservation.handler.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flab.reservation.session.websocket.SessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractWebSocketHandler extends TextWebSocketHandler {

    private final SessionManager sessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("try connect session...");
        try {
            //TODO extract key
            String key = "unknown";
            sessionManager.save(key, session);
        } catch (Exception e) {
            //TODO send error message
            session.close();
            log.error("fail connect session...", e);
            return;
        }

        log.debug("complete connect session...");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("try close session...");
        try {
            //TODO extract key
            String key = "unknown";
            sessionManager.remove(key);
        } catch (Exception e) {
            log.error("fail close session...");
            session.close();
            return;
        }

        log.debug("complete close session...");
    }
}
