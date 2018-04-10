package com.stockpricer.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.stockpricer.model.StockPrice;
@Component
public class StockPriceDao{
	
	private final String STOCKPRICE = "SELECT * FROM stocks";
	private final String STOCK_ID = STOCKPRICE + " where id = ?";
	private final String STOCK_NAME = STOCKPRICE + " where name = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public StockPrice findByName(String name) {
		System.out.println("yoo");
		
		return jdbcTemplate.queryForObject(STOCK_NAME, new Object[] {name}, new BeanPropertyRowMapper<>(StockPrice.class));
	}
	
	public StockPrice findById(int id) {
		return jdbcTemplate.queryForObject(STOCK_ID, new Object[] {id}, new BeanPropertyRowMapper<>(StockPrice.class));
	}
	
}
