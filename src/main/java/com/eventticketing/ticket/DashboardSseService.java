package com.eventticketing.ticket;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DashboardSseService {
    

   

        
        private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

        public SseEmitter subscribe() {

            
            SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

            
            emitters.add(emitter);

            
            emitter.onCompletion(() -> emitters.remove(emitter));
            emitter.onTimeout(() -> emitters.remove(emitter));
            emitter.onError(error -> emitters.remove(emitter));

            return emitter;
        }

        public void sendCheckInUpdate(Long checkedInCount) {

            
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(
                            SseEmitter.event()
                                    .name("check-in-update")
                                    .data(new CheckInStatsResponse(checkedInCount))
                    );
                } catch (Exception e) {
                    emitters.remove(emitter);
                }
            }
        }
    }


