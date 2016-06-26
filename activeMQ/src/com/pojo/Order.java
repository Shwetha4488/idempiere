package com.pojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

public class Order {
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	
	private long orderId;
	private long scrap;
	private Date setupTime;
	private Date processTime;
	private Date expectDelDate;
	private Date planedStartDate;
	private Date endDate;
	private String bomInfo;
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getScrap() {
		return scrap;
	}
	public void setScrap(long scrap) {
		this.scrap = scrap;
	}
	public Date getSetupTime() {
		return setupTime;
	}
	public void setSetupTime(Date setupTime) {
		this.setupTime = setupTime;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	public Date getExpectDelDate() {
		return expectDelDate;
	}
	public void setExpectDelDate(Date expectDelDate) {
		this.expectDelDate = expectDelDate;
	}
	public Date getPlanedStartDate() {
		return planedStartDate;
	}
	public void setPlanedStartDate(Date planedStartDate) {
		this.planedStartDate = planedStartDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getBomInfo() {
		return bomInfo;
	}
	public void setBomInfo(String bomInfo) {
		this.bomInfo = bomInfo;
	}
	
	public List<OrderSub> getOrderSubs(){
		List<OrderSub> list = new ArrayList<OrderSub>();
		String bomInfo = this.getBomInfo();
		JSONArray jsonArray = JSONArray.fromObject(bomInfo);
		if (jsonArray != null){
			list = (List<OrderSub>)JSONArray.toCollection(jsonArray, OrderSub.class);
		}
		return list;
	}
	
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", scrap=" + scrap
				+ ", setupTime=" + setupTime + ", processTime=" + processTime
				+ ", expectDelDate=" + expectDelDate + ", planedStartDate="
				+ planedStartDate + ", endDate=" + endDate + ", bomInfo="
				+ bomInfo + "]";
	}
	public String toValueString(){
		String value = "";
		value = orderId + ","
				+ scrap + ","
				+ "'" +df.format(setupTime) + "'" + ","
				+ "'" +df.format(processTime) + "'" + ","
				+ "'" +df.format(expectDelDate) + "'" + ","
				+ "'" +df.format(planedStartDate) + "'" + ","
				+ "'" +df.format(endDate) + "'";
		return value;
	}
	
	
}
