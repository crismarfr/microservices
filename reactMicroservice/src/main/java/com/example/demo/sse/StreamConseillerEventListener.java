package com.example.demo.sse;

import com.example.demo.sse.StreamConseillerEntry.StreamConseillerEventEntry;

public interface StreamConseillerEventListener {

	public void onPostMessage(StreamConseillerEventEntry msg);
}
