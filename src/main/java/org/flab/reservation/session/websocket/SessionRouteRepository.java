package org.flab.reservation.session.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SessionRouteRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public Optional<SessionRoute> findBy(String key) {
        String routeInfo = redisTemplate.opsForValue()
                .get(key);

        return Optional.ofNullable(routeInfo)
                .map(e -> {
                    try {
                        return objectMapper.readValue(e, SessionRoute.class);
                    } catch (JsonProcessingException ex) {
                        log.error("json parsing error!!", ex);
                        return null;
                    }
                });
    }

    void save(String key, SessionRoute route) throws JsonProcessingException {
        String value = objectMapper.writeValueAsString(route);
        redisTemplate.opsForValue()
                .set(key, value);
    }

    void remove(String key) {
        redisTemplate.unlink(key);
    }
}
