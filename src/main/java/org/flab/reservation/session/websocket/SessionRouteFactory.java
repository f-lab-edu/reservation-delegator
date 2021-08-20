package org.flab.reservation.session.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionRouteFactory {

    private final String host;
    private final int port;

    public SessionRouteFactory(@Value("${server.host}") String host,
                               @Value("${server.port}") int port) {
        this.host = host;
        this.port = port;
    }

    public SessionRoute create() {
        return new SessionRoute(host, port);
    }
}
