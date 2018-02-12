package com.example.demo.sse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.example.demo.sse.StreamConseillerEntry.StreamConseillerEventEntry;

/**
 * example of docs/tutorials:
 * https://golb.hplar.ch/p/Server-Sent-Events-with-Spring
 *
 */
public class Spring4StreamConseillerSSE implements StreamConseillerEventListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(Spring4StreamConseillerSSE.class);
	
	protected final StreamConseillerEntry streamConseillerEntry;

	private List<SseEmitter> emitters = new ArrayList<>();

	public Spring4StreamConseillerSSE(StreamConseillerEntry IdConseillerEntry) {
		this.streamConseillerEntry = IdConseillerEntry;
	}

	@Override
	public void onPostMessage(StreamConseillerEventEntry msg) {
		if (emitters.isEmpty()) {
			return;
		}
		SseEventBuilder evtBuilder = SseEmitter.event()
				.id(Integer.toString(msg.id))
				.name("event")
				.data(msg);
		for(SseEmitter emitter : emitters) {
			try {
				emitter.send(evtBuilder);
			} catch (IOException ex) {
				LOG.error("Failed to send msg to emitter", ex);
			}
		}
	}
	
	public SseEmitter subscribe(String lastEventIdText) {
		SseEmitter emitter = new SseEmitter();
        Integer lastId = lastEventIdText != null? Integer.parseInt(lastEventIdText) : null;

        // TODO should lock:   listMessage() .... add() subscribe !
		List<StreamConseillerEventEntry> replayMsgs = (lastId != null)?  
				streamConseillerEntry.listMessagesSinceLastId(lastId) : streamConseillerEntry.listMessages();
		for(StreamConseillerEventEntry msg : replayMsgs) {
			if (lastId != null && msg.id <= lastId) {
				continue;
			}
			try {
				emitter.send(msg);
			} catch (IOException ex) {
				LOG.error("Failed to re-send msg to emitter, ex:", ex.getMessage() + " => complete with error ... remove,disconnect");
				emitter.completeWithError(ex);
			}
		}
		
        emitters.add(emitter);
        
        emitter.onCompletion(() -> {
        	LOG.info("onCompletion -> remove emitter");
        	emitters.remove(emitter);
        });

        emitter.onTimeout(() -> {
        	LOG.info("onTimeout -> remove emitter");
        	emitters.remove(emitter);
        });

        return emitter;
	}

}
