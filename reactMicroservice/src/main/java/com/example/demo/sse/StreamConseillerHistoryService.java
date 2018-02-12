package com.example.demo.sse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class StreamConseillerHistoryService {

	public Map<String,StreamConseillerEntry> streamConseillersLst = new HashMap<>();
	
	public StreamConseillerHistoryService() {
		StreamConseillerEntry defaultIdConseiller = addStreamConseiller("Default");
		defaultIdConseiller.addMsg(new Date(), "BOT", "server start");
	}
	
	public StreamConseillerEntry addStreamConseiller(String name) {
		StreamConseillerEntry res = streamConseillersLst.get(name);
		if (res == null) {
			res = new StreamConseillerEntry(name);
			streamConseillersLst.put(name, res);
		}
		return res;
	}
	
	public void removeStreamConseiller(String name) {
		streamConseillersLst.remove(name);
	}
	
	public StreamConseillerEntry getStreamConseiller(String name) {
		return streamConseillersLst.get(name);
	}
	
}
