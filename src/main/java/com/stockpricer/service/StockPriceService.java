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

import dnl.utils.text.table.TextTable;
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
    	
//    	String tURL = "http://18.236.70.220:"+ ip + "/company";
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
//    	System.out.println("Name: " + supplier.getName());
//    	System.out.println(String.format("Prev Inventory Value: " + "%.2f", supplier.getInventoryValue()));
//    	System.out.println(String.format("Current Inventory Value: " + "%.2f", currentInventoryValue));
    	double currentTotalValue = currentCash + (0.7 * currentInventoryValue);
    	double percentChange = (currentTotalValue - prevTotalValue)/prevTotalValue;
    	
//    	System.out.println(String.format("Change in TotalValue: "  + "%.2f" + "%%", percentChange*100));
    	double stockChange = percentChange * prevStockPrice;
    	double newStockPrice = prevStockPrice + stockChange;
//    	System.out.println(String.format("Total value: " + "%.2f", currentTotalValue));
//    	System.out.println(String.format("Stock Price: " + "%.2f", newStockPrice));
    	int id = supplier.getId();
    	supplier.setPercent_change(percentChange * 100);
    	updateValues(id,currentTotalValue, currentCash, newStockPrice,currentInventoryValue);
    	return newStockPrice;
    }
    
    public void updateValues(int id, double total, double cash, double stockPrices ,double inventoryValue) {
    	stockPrice.updateValue(id, total, cash, stockPrices, inventoryValue);
    }
    
    
    @Scheduled(fixedRate=10000)
    public void updateStocks() {    	
    	StockPrice store = findById(1);
    	getStock(store);
//    	System.out.println("\n ================================== \n");
    	StockPrice vendorA = findById(2);
    	getStock(vendorA);
//    	System.out.println("\n ================================== \n");
//    	StockPrice vendorB = findById(3);
//    	getStock(vendorB);
//    	System.out.println("\n ================================== \n");
//    	StockPrice vendorC = findById(4);
//    	getStock(vendorC);
//    	System.out.println("\n ================================== \n");
    	StockPrice supplierA = findById(5);
    	getStock(supplierA);
//    	System.out.println("\n ================================== \n");
//    	StockPrice supplierB = findById(6);
//    	getStock(supplierB);
//    	System.out.println("\n ================================== \n");
//    	StockPrice supplierC = findById(7);
//    	getStock(supplierC);
//    	System.out.println("\n ================================== \n");
//    	
   	 	String[] columnNames = {"Name", "Stock Price", "Percent Change", "Total Value", "Cash", "Inventory Value"};
   	 	Object[][] data = {{"Store", String.format("%.2f", store.getStockPrice()), String.format("%.2f" + " %%", store.getPercent_change()), String.format("%.2f", store.getTotalValue()), String.format("%.2f", store.getCash()), String.format("%.2f", store.getInventoryValue())}, 
   	 			{"Vendor", String.format("%.2f", vendorA.getStockPrice()), String.format("%.2f" + " %%", vendorA.getPercent_change()), String.format("%.2f", vendorA.getTotalValue()), String.format("%.2f", vendorA.getCash()), String.format("%.2f", vendorA.getInventoryValue())}, 
   	 			{"Supplier", String.format("%.2f", supplierA.getStockPrice()), String.format("%.2f" + " %%", supplierA.getPercent_change()), String.format("%.2f", supplierA.getTotalValue()), String.format("%.2f", supplierA.getCash()), String.format("%.2f", supplierA.getInventoryValue())}};
   	
   	 	TextTable tt = new TextTable(columnNames, data);
   	 	tt.printTable();
   	 	System.out.println("");
//   	 {"Store", String.format("%.2f", store.getStockPrice())}, {"Vendor A", String.format("%.2f", vendorA.getStockPrice())}, {"Vendor B"}, {"Vendor C"}, {"Supplier A"}, {"Supplier B"}, {"Supplier C"}};
    }
}

