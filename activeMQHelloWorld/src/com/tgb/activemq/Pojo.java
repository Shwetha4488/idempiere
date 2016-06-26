package com.tgb.activemq;

import java.util.Date;

public class Pojo {
	private long orderId;
	private long bomItemId;
	private long bomQty;
	private long scrap;
	private double bomCosting;
	private Date setupTime;
	private Date processTime;
	private Date expectDelDate;
	private Date planedStartDate;
	private Date endDate;
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getBomItemId() {
		return bomItemId;
	}
	public void setBomItemId(long bomItemId) {
		this.bomItemId = bomItemId;
	}
	public long getBomQty() {
		return bomQty;
	}
	public void setBomQty(long bomQty) {
		this.bomQty = bomQty;
	}
	public long getScrap() {
		return scrap;
	}
	public void setScrap(long scrap) {
		this.scrap = scrap;
	}
	public double getBomCosting() {
		return bomCosting;
	}
	public void setBomCosting(double bomCosting) {
		this.bomCosting = bomCosting;
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
	
}
