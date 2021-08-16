package org.flab.reservation.handler.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flab.reservation.session.websocket.SessionRoute;
import org.flab.reservation.session.websocket.SessionRouteRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventHandler {

    private final SessionRouteRepository routeRepository;
    private final RestTemplate restTemplate;

    public boolean send(String key, EventMessage message) {
        Optional<SessionRoute> routeOpt = routeRepository.findBy(key);

        if (routeOpt.isPresent()) {
            SessionRoute route = routeOpt.get();
            String url = route.getHost() + ":" + route.getPort();
            restTemplate.postForEntity(url + "/handle-event", message, String.class);

            return true;
        }

        return false;
    }
}
