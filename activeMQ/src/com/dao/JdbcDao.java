package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JdbcDao {
	static Connection connection = null;
	static Statement statement = null;
	
	static Connection connection1 = null;
	static Statement statement1 = null;
	
	static JedisPool pool = null; 
	static Jedis jedis = null;
	
	public static void creatWebDBConnection(){
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
                                "jdbc:postgresql://192.168.199.235:5432/circuitive-development", "postgres",
                                "postgres");
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
	
	public static void creatERPDBConnection(){
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
                connection1 = DriverManager.getConnection(
                                "jdbc:postgresql://127.0.0.1:5432/idempiere", "adempiere",
                                "adempiere");
                statement1 = connection1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
		JedisPoolConfig config = new JedisPoolConfig();
		String ADDR = "192.168.199.10";
		int PORT = 6379;
		String AUTH = "123456";
		int TIMEOUT = 10000;
		config.setMaxActive(1024);
		config.setMaxIdle(200);
		config.setMaxWait(10000);
		config.setTestOnBorrow(true);
		pool = new JedisPool(config, ADDR, PORT, TIMEOUT);
		
		
//		pool = new JedisPool("127.0.0.1", 6379); 
		
		try {
			jedis = pool.getResource();
		} catch (Exception e) {
			System.out.println("redis连接错误");
			e.printStackTrace();
		}
	}
	
}
