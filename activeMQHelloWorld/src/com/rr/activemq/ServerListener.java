package com.rr.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ServerListener implements MessageListener {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址
	
	@Override
	public void onMessage(Message message) {
		 try {  
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ServerListener.USERNAME, ServerListener.PASSWORD, ServerListener.BROKEURL);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer replyProducer = session.createProducer(null);
            TextMessage response = session.createTextMessage();  
            if (message instanceof TextMessage) {  
                TextMessage txtMsg = (TextMessage) message;  
                String messageText = txtMsg.getText();  
                response.setText(messageText + "----reply");  
            }  
  
            //Set the correlation ID from the received message to be the correlation id of the response message  
            //this lets the client identify which message this is a response to if it has more than  
            //one outstanding message to the server 
            System.out.println(message.getJMSCorrelationID());
            response.setJMSCorrelationID(message.getJMSCorrelationID());  
  
            //Send the response to the Destination specified by the JMSReplyTo field of the received message,  
            //this is presumably a temporary queue created by the client 
            System.out.println(message.getJMSReplyTo().toString());
            replyProducer.send(message.getJMSReplyTo(), response);  
            System.out.println(response.getText());
            
        } catch (JMSException e) {  
            //Handle the exception appropriately  
        	System.out.print(e.getMessage());
        	e.printStackTrace();
        }  
		
	}

}
