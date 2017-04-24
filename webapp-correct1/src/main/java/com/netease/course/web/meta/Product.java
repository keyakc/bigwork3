package com.netease.course.web.meta;

public class Product {
	int id,isBuy,isSell;
	double price;
	String title,image,summary,detail;
	long saleNum;
	
	public Product(int id, int isBuy, int isSell, double price, String title, String image, String summary,
			String detail,long saleNum) {
		this.id = id;
		this.isBuy = isBuy;
		this.isSell = isSell;
		this.price = price;
		this.title = title;
		this.image = image;
		this.summary = summary;
		this.detail = detail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
	}
	public int getIsSell() {
		return isSell;
	}
	public void setIsSell(int isSell) {
		this.isSell = isSell;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}		
	
	public long getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(long saleNum) {
		this.saleNum=saleNum;
	}
}
