package org.flab.reservation.session.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flab.reservation.exception.NotFoundSessionException;
import org.flab.reservation.utils.ThrowableRunnable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionManager {

    private final Map<String, WebSocketSession> sessionStorage = new HashMap<>();
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final SessionRouteRepository sessionRouteRepository;
    private final SessionRouteFactory sessionRouteFactory;

    public Optional<WebSocketSession> get(String key) {
        return Optional.ofNullable(sessionStorage.get(key));
    }

    public WebSocketSession getOrThrow(String key) {
        return getOrEmpty(key).orElseThrow(NotFoundSessionException::new);
    }

    public Optional<WebSocketSession> getOrEmpty(String key) {
        return Optional.ofNullable(sessionStorage.get(key));
    }

    public void save(String key, WebSocketSession newSession) throws Exception {
        lockAndThen(() -> {
            if (sessionStorage.containsKey(key)) {
                WebSocketSession oldSession = sessionStorage.get(key);
                oldSession.close();
            }

            sessionStorage.put(key, newSession);
            SessionRoute route = sessionRouteFactory.create();
            sessionRouteRepository.save(key, route);
        });
    }

    public void remove(String key) throws Exception {
        lockAndThen(() -> {
            WebSocketSession session = sessionStorage.remove(key);
            if (session != null) {
                session.close();
            }

            sessionRouteRepository.remove(key);
        });
    }

    private void lockAndThen(ThrowableRunnable block) throws Exception {
        reentrantLock.lock();
        block.run();
        reentrantLock.unlock();
    }
}
