package com.netease.course.web.meta;

public class ShowProduct {
	int id,isBuy,isSell;
	String title,summary,detail,image;
	double price,buyPrice;
	long buyNum,saleNum;
	
	public ShowProduct(int id, int isBuy, int isSell, String title, String summary, String detail, String image,
			double price, double buyPrice, long buyNum, long saleNum) {
		super();
		this.id = id;
		this.isBuy = isBuy;
		this.isSell = isSell;
		this.title = title;
		this.summary = summary;
		this.detail = detail;
		this.image = image;
		this.price = price;
		this.buyPrice = buyPrice;
		this.buyNum = buyNum;
		this.saleNum = saleNum;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(long buyNum) {
		this.buyNum = buyNum;
	}

	public long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(long saleNum) {
		this.saleNum = saleNum;
	}	
	

}
