package com.stockpricer.model;

import java.math.BigDecimal;

public class StockPrice {
	
	private int id;
	private String ip;
	private String name;
	private double cash;
	private double inventory_value;
	private double 	total_value;
	private double stock_price;
	
	
	
	public int getId() {
		return id;
	}
	
	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getInventoryValue() {
		return inventory_value;
	}

	public void setInventoryValue(double inventory_value) {
		this.inventory_value = inventory_value;
	}

	public double getTotalValue() {
		return total_value;
	}

	public void setTotalValue(double total_value) {
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
	
	public double getStockPrice() {
		return stock_price;
	}
	public void setStockPrice(double stock_price) {
		this.stock_price = stock_price;
	}
}
