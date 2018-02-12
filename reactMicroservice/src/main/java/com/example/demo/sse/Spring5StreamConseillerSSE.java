package com.example.demo.sse;

import org.springframework.http.codec.ServerSentEvent;

import com.example.demo.sse.StreamConseillerEntry.StreamConseillerEventEntry;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

/**
 * example doc/tutorials:
 * https://github.com/spring-projects/spring-framework/blob/master/src/docs/asciidoc/web/web-flux.adoc
 *
 */
public class Spring5StreamConseillerSSE implements StreamConseillerEventListener  {

	protected final StreamConseillerEntry streamConseillerEntry;

	private ReplayProcessor<ServerSentEvent<StreamConseillerEventEntry>> replayProcessor;
	
	private int idGenerator = 1;
	
	public Spring5StreamConseillerSSE(StreamConseillerEntry idConseillerEntry) {
		this.streamConseillerEntry = idConseillerEntry;
		this.replayProcessor = ReplayProcessor.<ServerSentEvent<StreamConseillerEventEntry>>create(0);
	}

	@Override
	public void onPostMessage(StreamConseillerEventEntry msg) {
		ServerSentEvent<StreamConseillerEventEntry> event = ServerSentEvent.builder(msg)
				.event("event")
				.id(generateNewId()).build();
		replayProcessor.onNext(event);
	}

	private String generateNewId() {
		return Integer.toString(idGenerator++);
	}

	public Flux<ServerSentEvent<StreamConseillerEventEntry>> subscribe(String lastEventId) {
		Integer lastId = (lastEventId != null)? Integer.parseInt(lastEventId) : null;
		return replayProcessor
				.filter(x -> lastId == null || x.data().id > lastId)
				// .log()
				;
	}

}
