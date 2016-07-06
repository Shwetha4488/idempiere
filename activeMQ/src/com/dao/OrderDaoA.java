package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoA {

	public static void main(String[] args) {
		JdbcDao.creatWebDBConnection();
		JdbcDao.creatERPDBConnection();
		JdbcDao.createRedisConnection();
		String orderId = "24";
		try {
			insertOrder(orderId);
			insertBom(orderId);
			insertOrder_redis(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getOrderBomSql(String orderId){
		String sql = "select * from project_bom_info where \"projectVersionId\" = (select \"projectId\" from order_project_details where \"id\" =";
		sql = sql + orderId;
		sql += ")";
		return sql;
	}
	
	public static String getOrderSql(String orderId){
		String sql = "select o.\"orderId\", o.\"date\", o.\"statusId\", a.* ";
		sql += "from order_details o, (select * from user_address where id = (select \"addressId\" from order_shippment where \"orderId\"=";
		sql += orderId;
		sql += ")) a ";
		sql += "where o.\"orderId\"=";
		sql += orderId;
		return sql;
	}
	
	public static ResultSet query(String sql) throws SQLException{
		ResultSet result = JdbcDao.statement.executeQuery(sql);
		return result;
	}
	
	public static void insertOrder(String orderId) throws SQLException{
		ResultSet orderSet;
		String orderSql = getOrderSql(orderId);
		orderSet = query(orderSql);
		while(orderSet.next()){
			String sql = "insert into \"adempiere\".\"swie_order\" ";
			sql += "(\"swie_order_id\", \"order_date\", \"status_id\", \"ad_client_id\", \"ad_org_id\", \"createdby\", \"updatedby\", \"user_id\", ";
			sql += "\"full_name\", \"region\", \"country\", \"city\", \"address1\", \"address2\", \"phone\", \"dialcode\", \"postal_code\")";
			sql += "values(";
			sql += orderSet.getString("orderId");
			sql += ", ";
			sql += "'";
			sql += orderSet.getString("date");
			sql += "'";
			sql += ", ";
			sql += orderSet.getString("statusId");
			sql += ", ";
			sql += "0, 0, 0, 0";
			sql += ", ";
			sql += orderSet.getString("userId");
			sql += ", ";
			sql += "'";
			sql += orderSet.getString("fullName");
			sql += "'";
			sql += " ,";
			sql += "'";
			sql += orderSet.getString("region");
			sql += "'";
			sql += " ,";
			sql += "'";
			sql += orderSet.getString("country");
			sql += "'";
			sql += " ,";
			sql += "'";
			sql += orderSet.getString("city");
			sql += "'";
			sql += " ,";
			sql += "'";
			sql += orderSet.getString("address1");
			sql += "'";
			sql += " ,";
			sql += "'";
			sql += orderSet.getString("address2");
			sql += "'";
			sql += " ,";
			sql += orderSet.getString("phone");
			sql += " ,";
			sql += orderSet.getString("dialcode");
			sql += " ,";
			sql += orderSet.getString("postalCode");
			sql += ")";
			
			System.out.println(sql);
			JdbcDao.statement1.execute(sql);
		}
	}
	
	public static void insertOrder_redis(String orderId) throws SQLException{
		ResultSet bomSet;
		String bomSql = getOrderBomSql(orderId);
		bomSet = query(bomSql);
		StringBuilder sb = new StringBuilder();
		sb.append("{\"orderId\":");
		sb.append(orderId);
		sb.append(",\"status\":");
		sb.append("0");
		sb.append(",\"materials\":");
		
		sb.append("[");
		while(bomSet.next()){
			sb.append("{");
			sb.append("\"materialId\":");
			sb.append(createMid(bomSet.getString("partNumber")));
			sb.append(",");
			sb.append("\"materialNum\":");
			sb.append(bomSet.getString("quantity"));
			sb.append("}");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		sb.append("}");
		
		JdbcDao.jedis.hset("order", String.valueOf(orderId), sb.toString());
	}
	
	public static int createMid(String partNumber){
		if ("3413.0331.22".equals(partNumber)){
			return 10001;
		}else if ("CL05A104MP5NNNC".equals(partNumber)){
			return 10002;
		}else if ("293359-1".equals(partNumber)){
			return 10003;
		}else{
			int a;
			do {
				a = (int) (Math.random()*10);
			}while(a<3);
			return 10000 + a;
		}
	}
	
	public static void insertBom(String orderId) throws SQLException{
		ResultSet bomSet;
		String bomSql = getOrderBomSql(orderId);
		bomSet = query(bomSql);
		while(bomSet.next()){
			String sql = "insert into \"adempiere\".\"swie_order_bom\" ";
			sql += "(\"swie_order_bom_id\", \"swie_order_id\", \"ad_client_id\", \"ad_org_id\", \"createdby\", \"updatedby\", \"project_version_id\", \"user_id\", \"edited_date\", ";
			sql += "\"footprint\", \"designator\", \"part_number\", \"manufacturer\", \"manufacturer_part_num\", \"desciption\", \"rohs_status\", \"operating_temperature\", \"standard_package\", ";
			sql += "\"price_break\", \"price\", \"quantity\", \"has_errors\", \"error_desc\", \"image\" )values(";
			sql += (bomSet.getString("id") + orderId);
			sql += ", ";
			sql += orderId;
			sql += ", ";
			sql += "0, 0, 0, 0, ";
			sql += bomSet.getString("projectVersionId");
			sql += ", ";
			sql += bomSet.getString("userId");
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("editedDate");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("footprint");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("designator");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("partNumber");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("manufacturer");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("manufacturerPartNum");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("desciption");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("rohsStatus");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("operatingTemperature");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("standardPackage");
			sql += "'";
			sql += ", ";
			sql += bomSet.getString("priceBreak");
			sql += ", ";
			sql += bomSet.getString("price");
			sql += ", ";
			sql += bomSet.getString("quantity");
			sql += ", ";
			sql += "'"; 
			sql += (bomSet.getString("hasErrors")==null ? 'f' : bomSet.getString("hasErrors"));
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("errorDesc");
			sql += "'";
			sql += ", ";
			sql += "'";
			sql += bomSet.getString("image");
			sql += "'";
			sql += ")";
			
			System.out.println(sql);
			JdbcDao.statement1.execute(sql);
		}
	}
	
	public static void updateStatus(String orderId) throws SQLException{
		String status_id = "4";
		String sql = "update \"adempiere\".\"swie_order\" set \"status_id\"=";
		sql += status_id;
		sql += " where \"swie_order_id\" = ";
		sql += orderId;
		JdbcDao.statement1.execute(sql);
		updateStatus_web(orderId);
	}
	
	public static void updateStatus_web(String orderId) throws SQLException{
		String status_id = "4";
		String sql = "update \"order_details\" set \"statusId\"=";
		sql += status_id;
		sql += " where \"orderId\" = ";
		sql += orderId;
		JdbcDao.statement.execute(sql);
	}
}
