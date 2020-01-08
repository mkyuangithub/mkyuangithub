package org.sky.vo;

import java.io.Serializable;

public class ProductVO implements Serializable {

	private int stock = 0;

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getProductName() {
		return productName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	private int productId = 0;
	private String productName = "";
}
