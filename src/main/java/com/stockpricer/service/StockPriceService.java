package com.stockpricer.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockpricer.dao.StockPriceDao;
import com.stockpricer.model.StockPrice;
@Component
public class StockPriceService {
	@Autowired
	private StockPriceDao stockPrice;
	
	@Autowired
	private RestTemplate restTemplate;
	private String response ="";
	private StockPrice supplier = new StockPrice();
	
	public StockPrice findByName(String name) {
		return stockPrice.findByName(name);
	}
	
	public StockPrice findById(int id) {
		return stockPrice.findById(id);
	}
	
    public void getStock(StockPrice supplier){
    	String ip = supplier.getIp();
        String tURL = "http://192.168.88." + ip + ":8080/company";
        UriComponentsBuilder builder = UriComponentsBuilder.
                fromUriString(tURL);
        this.supplier = supplier;
         response = restTemplate.getForObject(builder.toUriString(), String.class);
//       System.out.println(response);
         convertCashAndValue() ;
    }
    
    public ArrayList<Double> convertCashAndValue() {
	    ArrayList<Double> cashAndInventory = new ArrayList<Double>();
	    ObjectMapper mapper = new ObjectMapper();
	    try {
		         cashAndInventory = mapper.readValue(response,new TypeReference<ArrayList<Double>>() {});
		        
		    } catch (JsonParseException e) {
		    
		        e.printStackTrace();
		    } catch (JsonMappingException e) {
		        
		        e.printStackTrace();
		    } catch (IOException e) {
		    
		        e.printStackTrace();
		    }    System.out.println( "cash on hand  "+cashAndInventory.get(0));
		    calculateStock(cashAndInventory);
	    return cashAndInventory; 
    }
    
    public double calculateStock(ArrayList<Double> cashAndInventory) {
    	double prevStockPrice = supplier.getStockPrice();
    	double prevTotalValue = supplier.getTotalValue();
    	double currentCash = cashAndInventory.get(0);
    	double currentInventoryValue = cashAndInventory.get(1);
    	System.out.println("Prev Inventory Value: " + supplier.getInventoryValue());
    	System.out.println("Current inventory Value: " + currentInventoryValue);
    	double currentTotalValue = currentCash + (0.7 * currentInventoryValue);
    	double percentChange = (currentTotalValue - prevTotalValue)/prevTotalValue;
    	System.out.println("Change in TotalValue: " + (percentChange*100) +"%");
    	double stockChange = percentChange * prevStockPrice;
    	System.out.println("Stock Change: " + stockChange);
    	double newStockPrice = prevStockPrice + stockChange;
    	System.out.println("Prev Total: " + prevTotalValue);
    	System.out.println("Total value: " + currentTotalValue);
    	System.out.println("new stock price: " + newStockPrice);
    	int id = supplier.getId();
    	updateValues(id,currentTotalValue,newStockPrice,currentInventoryValue);
    	return newStockPrice;
    }
    
    public void updateValues(int id, double total, double stockPrices ,double inventoryValue) {
    	stockPrice.updateValue(id, total, stockPrices, inventoryValue);
    }
    
    
}

