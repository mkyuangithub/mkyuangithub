package org.sky.seatademo.vo;

import java.io.Serializable;

public class SeataProductVO implements Serializable {
	private int stock = 0;
	private long productId = 0;
	private String productName = "";

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
