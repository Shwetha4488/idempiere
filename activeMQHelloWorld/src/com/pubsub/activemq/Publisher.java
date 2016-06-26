package com.pubsub.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.tgb.activemq.JMSProducer;

public class Publisher {
	//默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    
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
	
	public Publisher() throws JMSException { 
		connectionFactory = new ActiveMQConnectionFactory(Publisher.USERNAME, Publisher.PASSWORD, Publisher.BROKEURL);  
        connection = connectionFactory.createConnection();  
        try {  
        connection.start();  
        } catch (JMSException jmse) {  
            connection.close();  
            throw jmse;  
        }  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        messageProducer = session.createProducer(null);  
    } 
	 
	
	
	
}
