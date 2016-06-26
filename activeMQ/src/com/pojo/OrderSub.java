package com.pojo;

public class OrderSub {
	private long orderId;
	private long bomItemId;
	private long bomQty;
	private double bomCosting;
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
	public double getBomCosting() {
		return bomCosting;
	}
	public void setBomCosting(double bomCosting) {
		this.bomCosting = bomCosting;
	}
	
	@Override
	public String toString() {
		return "OrderSub [orderId=" + orderId + ", bomItemId=" + bomItemId
				+ ", bomQty=" + bomQty + ", bomCosting=" + bomCosting + "]";
	}
	
	public String toValueString() {
		String value = "";
		value = orderId + ","
				+ bomItemId + ","
				+ bomQty + ","
				+ bomCosting;
		return value;
	}
}
