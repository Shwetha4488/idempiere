package com.tgb.activemq;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;


public class Listener implements MessageListener {  
    public void onMessage(Message message) {  
        try {  
        	
        	TextMessage tm = (TextMessage) message;
        	System.out.print(tm.getText());
        	
        	String jsonString = tm.getText();
        	JSONObject jsonObject = JSONObject.fromObject(jsonString);
        	System.out.print(jsonObject.toString());
        	
        	
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
} 
