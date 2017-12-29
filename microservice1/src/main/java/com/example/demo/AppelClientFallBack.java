package com.example.demo;



import org.springframework.stereotype.Component;

import com.example.demo.bean.Message;

@Component
public class AppelClientFallBack implements AppelClient {
		
	@Override
	public Message getMessage()
	{
		Message mess=new Message();
		mess.setMess("Status DOWN microservice2 !");
		return mess;
	}
}
