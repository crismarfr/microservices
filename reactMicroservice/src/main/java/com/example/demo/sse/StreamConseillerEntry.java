package com.example.demo.sse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.sse.StreamConseillerEntry.StreamConseillerEventEntry;

import reactor.core.publisher.Flux;

public class StreamConseillerEntry {

	public static class StreamConseillerEventEntry {
		// @JsonIgnore // redundant with SSE lines "id: .."
		public final int id;
		
		public final Date date;
		public final String idClient;
		public final String idConseiller; // redundant
		public final String msg;
		
		public StreamConseillerEventEntry(int id, Date date, String idClient, String idConseiller, String msg) {
			this.id = id;
			this.date = date;
			this.idClient = idClient;
			this.idConseiller = idConseiller;
			this.msg = msg;
		}

		@Override
		public String toString() {
			return "StreamMessage[" + date + ", idClient=" + idClient + ", room=" + idConseiller + " : " + msg + "]";
		}
		
	}
	
	public final String name;
	
	private int idGenerator = 1;
	private List<StreamConseillerEventEntry> messages = new ArrayList<>(); // TODO CopyOnWriteArrayList ..

	public List<StreamConseillerEventEntry> getMessages() {
		return messages;
	}

	public void setMessages(List<StreamConseillerEventEntry> messages) {
		this.messages = messages;
	}

	private int maxRecentHistory = 20;
	
	private List<StreamConseillerEventListener> listeners = new ArrayList<>();
	private Spring4StreamConseillerSSE spring4SSE;
	private Spring5StreamConseillerSSE spring5SSE;
	
	// ------------------------------------------------------------------------
	
	public StreamConseillerEntry(String name) {
		this.name = name;
		this.spring4SSE = new Spring4StreamConseillerSSE(this);
		listeners.add(spring4SSE);
		this.spring5SSE = new Spring5StreamConseillerSSE(this);
		listeners.add(spring5SSE);
	}

	// ------------------------------------------------------------------------

	public void addMsg(Date date, String idClient, String msg) {
		int id = idGenerator++;
		StreamConseillerEventEntry msgEntry = new StreamConseillerEventEntry(id, date, idClient, name, msg);
		messages.add(msgEntry);
		if (messages.size() > maxRecentHistory) {
			messages.remove(0);
		}
		
		for(StreamConseillerEventListener listener : listeners) {
			listener.onPostMessage(msgEntry);
		}
	}
	
	public void removeMessagesClient(String idClient) {
				
		List<StreamConseillerEventEntry> s = new ArrayList<>();
		Iterator it=messages.iterator();
		while(it.hasNext())
		{
			StreamConseillerEventEntry entry=(StreamConseillerEventEntry) it.next();
			if(entry!=null && entry.idClient.equals(idClient))
				s.add(entry);
		}
		messages.removeAll(s);
	}
	
	public List<StreamConseillerEventEntry> listMessagesSince(Date since) {
		return messages.stream()
				.filter(x -> since == null || x.date.after(since))
				.collect(Collectors.toList());
	}

	public List<StreamConseillerEventEntry> listMessagesSinceLastId(int lastId) {
		return messages.stream()
				.filter(x -> x.id > lastId)
				.collect(Collectors.toList());
	}

	public List<StreamConseillerEventEntry> listMessages() {
		return new ArrayList<>(messages);
	}

	public SseEmitter subscribeSpring4(String lastEventId) {
		return spring4SSE.subscribe(lastEventId);
	}

	public Flux<ServerSentEvent<StreamConseillerEventEntry>> subscribeSpring5(String lastEventId) {
		return spring5SSE.subscribe(lastEventId);
	}

}