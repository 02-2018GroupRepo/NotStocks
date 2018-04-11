package com.stockpricer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
		    }
		    calculateStock(cashAndInventory);
	    return cashAndInventory; 
    }
    
    public double calculateStock(ArrayList<Double> cashAndInventory) {
    	double prevStockPrice = supplier.getStockPrice();
    	double prevTotalValue = supplier.getTotalValue();
    	double currentCash = cashAndInventory.get(0);
    	double currentInventoryValue = cashAndInventory.get(1);
    	System.out.println("Name: " + supplier.getName());
    	System.out.println("Prev Inventory Value: " + supplier.getInventoryValue());
    	System.out.println("Current Inventory Value: " + currentInventoryValue);
    	double currentTotalValue = currentCash + (0.7 * currentInventoryValue);
    	double percentChange = (currentTotalValue - prevTotalValue)/prevTotalValue;
    	System.out.println("Change in TotalValue: " + (percentChange*100) +" %");
    	double stockChange = percentChange * prevStockPrice;
    	double newStockPrice = prevStockPrice + stockChange;
    	System.out.println("Total value: " + currentTotalValue);
    	System.out.println("Stock Price: " + newStockPrice);
    	int id = supplier.getId();
    	updateValues(id,currentTotalValue,newStockPrice,currentInventoryValue);
    	return newStockPrice;
    }
    
    public void updateValues(int id, double total, double stockPrices ,double inventoryValue) {
    	stockPrice.updateValue(id, total, stockPrices, inventoryValue);
    }
    
    
    @Scheduled(fixedRate=10000)
    public void updateStocks() {
    	StockPrice vendorA = findById(2);
    	getStock(vendorA);
    	System.out.println("\n ================================== \n");
    	StockPrice supplierC = findById(7);
    	getStock(supplierC);
    	System.out.println("\n ================================== \n");
    	StockPrice supplierA = findById(5);
    	getStock(supplierA);
    	System.out.println("\n ================================== \n");
    }
}

