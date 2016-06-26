package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import net.sf.json.JSONArray;

import com.pojo.Order;
import com.pojo.OrderSub;

public class OrderDao {
	static Connection connection = null;
	static Statement statement = null;
	
	static JedisPool pool = null; 
	static Jedis jedis = null;
	
	public static void creatConnection(){
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");
		try {
		        Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
		        System.out.println("Where is your PostgreSQL JDBC Driver? "
		                        + "Include in your library path!");
		        e.printStackTrace();
		        return;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");

        try {
                connection = DriverManager.getConnection(
                                "jdbc:postgresql://192.168.199.10:5432/activemq", "activemq",
                                "activemq");
                statement = connection.createStatement();
        } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return;
        }
        if (connection != null) {
	            System.out.println("You made it, take control your database now!");
	    } else {
	            System.out.println("Failed to make connection!");
	    }
	}
	
	public static void createRedisConnection(){
		pool = new JedisPool("127.0.0.1", 6379); 
		try {
			jedis = pool.getResource();
		} catch (Exception e) {
			System.out.println("redis连接错误");
			e.printStackTrace();
		}
	}
	
	public boolean insertOrder(Order order){
		String sql = "";
		Boolean result = true;
		
		if (order == null) return result;
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append("order_main");
		sb.append("(\"orderId\", \"scrap\", \"setupTime\", \"processTime\", \"expectDelDate\", \"planedStartDate\", \"endDate\") ");
		sb.append("values ");
		sb.append("(");
		sb.append(order.toValueString());
		sb.append(");");
		
		sql = sb.toString();
		
		System.out.println(sql);
		try {
			statement.execute(sql);
			insertOrderSubs(order);
		//	insertOrder_redis(order);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	public void insertOrderSubs(Order order) throws SQLException{
		if (order == null) return;
		String bomInfo = order.getBomInfo();
		JSONArray jsonArray = JSONArray.fromObject(bomInfo);
		if (jsonArray == null) return;
		List list = (List)JSONArray.toCollection(jsonArray, OrderSub.class);
		String sql = "";
		Iterator it = list.iterator();  
        while(it.hasNext()){ 
        	StringBuilder sb = new StringBuilder();
        	OrderSub s = (OrderSub)it.next(); 
        	s.setOrderId(order.getOrderId());
        	sb.append("insert into \"activemq\".\"order_sub\" (\"orderId\", \"bomItemId\", \"bomQty\", \"bomCosting\") values (");
        	sb.append(s.toValueString());
        	sb.append(")");
        	sql = sb.toString();
        	statement.execute(sql);
        }
	}
	
	public boolean insertOrder_redis(Order order){
		boolean result = true;
		
		try {
			//生成json串
			List<OrderSub> subs = order.getOrderSubs(); 
			StringBuilder sb = new StringBuilder();
			sb.append("{\"orderId\":");
			sb.append(order.getOrderId());
			sb.append(",\"status\":");
			sb.append("0");
			sb.append(",\"materials\":");
			
			sb.append("[");
			for (int i=0; i<subs.size(); i++){
				sb.append("{");
				sb.append("\"materialId\":");
				sb.append(subs.get(i).getBomItemId());
				sb.append(",");
				sb.append("\"materialNum\":");
				sb.append(subs.get(i).getBomQty());
				sb.append("}");
				if (i != (subs.size()-1)){
					sb.append(",");
				}
			}
			sb.append("]");
			sb.append("}");
			
//		System.out.print(sb.toString());
			//{"orderId":orderId, "status":0, "materials":[{"materialId":"materialId", "materialNum":materialNum}, {"materialId":"materialId", "materialNum":materialNum}]};
//		Map<String, String> map = new HashMap<String, String>();
//		map.put(String.valueOf(order.getOrderId()), sb.toString());
//		jedis.hmset("order", map);
			
			jedis.hset("order", String.valueOf(order.getOrderId()), sb.toString());
			System.out.print(jedis.hget("order", String.valueOf(order.getOrderId())));
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}
	
}


