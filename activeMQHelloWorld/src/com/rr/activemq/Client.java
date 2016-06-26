package com.rr.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.tgb.activemq.JMSProducer;

public class Client {
	//默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	
	public static void main(String args[]){
		//连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(Client.USERNAME, Client.PASSWORD, Client.BROKEURL);
		
        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为HelloWorld的消息队列
            destination = session.createQueue("request-response");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            
            Destination tempDest = session.createTemporaryQueue();
            MessageConsumer responseConsumer = session.createConsumer(tempDest);
            responseConsumer.setMessageListener(new ClientListener());
            
            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText("MyProtocolMessage");
            txtMessage.setJMSReplyTo(tempDest);
            
            String correlationId = Double.toString(Math.random()); 
            System.out.println(correlationId);
            txtMessage.setJMSCorrelationID(correlationId);  
            messageProducer.send(txtMessage); 
            System.out.println(txtMessage.getText());

            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
//            if(connection != null){
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
        }
	}
	
      
}
