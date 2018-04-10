package com.stockpricer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.stockpricer.dao.StockPriceDao;
import com.stockpricer.model.StockPrice;
@Component
public class StockPriceService {
	@Autowired
	private StockPriceDao stockPrice;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public StockPrice findByName(String name) {
		return stockPrice.findByName(name);
	}
	
	public StockPrice findById(int id) {
		return stockPrice.findById(id);
	}
	
    public void getStock(String id){
        String tURL = "http://192.168.88." + id + ":8080/company";
        UriComponentsBuilder builder = UriComponentsBuilder.
                fromUriString(tURL);
        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        System.out.println(response);
    }

}
