package com.activemq;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.util.ByteSequence;

import net.sf.json.JSONObject;

import com.dao.OrderDao;
import com.pojo.Order;

public class JMSListener implements MessageListener{
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	@Override
	public void onMessage(Message message) {
		String jsonString = new String();
		if (message instanceof ActiveMQBytesMessage){
			jsonString = new String (((ActiveMQBytesMessage) message).getContent().getData());
		}else if (message instanceof TextMessage){
			try {
				jsonString = ((TextMessage) message).getText();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
		if (jsonString != null){
			try {
				//转换为javaBean
				JSONObject jsonObject = JSONObject.fromObject(jsonString); 
				Order order = (Order) JSONObject.toBean(jsonObject, Order.class);
				order.setSetupTime(df.parse(jsonObject.getString("setupTime")));
				order.setProcessTime(df.parse(jsonObject.getString("processTime")));
				order.setExpectDelDate(df.parse(jsonObject.getString("expectDelDate")));
				order.setPlanedStartDate(df.parse(jsonObject.getString("planedStartDate")));
				order.setEndDate(df.parse(jsonObject.getString("endDate")));
				
				//javaBean持久化
				// 存入postgresql数据库
				OrderDao orderDao = new OrderDao();
				boolean result = true;
				result = orderDao.insertOrder(order);
				// 还需要存入redis
				result = orderDao.insertOrder_redis(order);
				if (result){ 
					System.out.println("insert success");
				}else{
					System.out.println("insert fail");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
