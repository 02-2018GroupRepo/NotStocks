package com.stockpricer.model;

import java.math.BigDecimal;

public class StockPrice {
	
	private int id;
	private String ip;
	private String name;
	private BigDecimal cash;
	private BigDecimal inventory_value;
	private BigDecimal 	total_value;
	private BigDecimal stock_price;
	
	
	
	public int getId() {
		return id;
	}
	
	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getInventory_value() {
		return inventory_value;
	}

	public void setInventory_value(BigDecimal inventory_value) {
		this.inventory_value = inventory_value;
	}

	public BigDecimal getTotal_value() {
		return total_value;
	}

	public void setTotal_value(BigDecimal total_value) {
		this.total_value = total_value;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public BigDecimal getStock_price() {
		return stock_price;
	}
	public void setStock_price(BigDecimal stock_price) {
		this.stock_price = stock_price;
	}
	
	
	

}
