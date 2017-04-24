package com.netease.course.web.meta;

public class ListBuy {
	int id;
	String title,image;
	double buyPrice,total;
	long buyTime,buyNum;
	
	public ListBuy(int id, String title, String image, double buyPrice, double total, long buyTime, long buyNum) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.buyPrice = buyPrice;
		this.total = total;
		this.buyTime = buyTime;
		this.buyNum = buyNum;
	}
	
	

}
