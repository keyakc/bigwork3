package com.netease.course.web.meta;

public class Trx {
	int id,productId,personId;
	double buyPrice,total;
	long buyNum,buyTime;	
	
	public Trx(int id, int productId, int personId, double buyPrice, double total, long buyNum, long buyTime) {
		this.id = id;
		this.productId = productId;
		this.personId = personId;
		this.buyPrice = buyPrice;
		this.total = total;
		this.buyNum = buyNum;
		this.buyTime = buyTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public long getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(long buyNum) {
		this.buyNum = buyNum;
	}
	public long getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}
	
	
	
}
