package org.flab.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flab.reservation.handler.event.EventHandler;
import org.flab.reservation.handler.event.EventMessage;
import org.flab.reservation.session.websocket.SessionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventHandler eventHandler;
    private final SessionManager sessionManager;

    //TODO message 설계 및 처리
    @PostMapping("/api/v1/websocket/handle-event")
    public ResponseEntity<String> handle(@RequestBody EventMessage message) {
        //TODO extract key
        String key = "unknown";
        sessionManager.getOrEmpty(key)
                .ifPresentOrElse(
                        session -> {
                            //TODO send message using session
                        },
                        () -> eventHandler.send(key, message)
                );

        return ResponseEntity.ok("SUCCESS");
    }
}

