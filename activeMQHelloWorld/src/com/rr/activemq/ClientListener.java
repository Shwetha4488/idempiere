package com.rr.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ClientListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		String messageText = null;  
        try {  
            if (message instanceof TextMessage) {  
                TextMessage textMessage = (TextMessage) message;  
                messageText = textMessage.getText();  
                System.out.println("messageText = " + messageText);  
            }  
        } catch (JMSException e) {  
            //Handle the exception appropriately  
        }  
		
	}

}
