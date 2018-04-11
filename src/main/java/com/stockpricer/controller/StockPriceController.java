package com.stockpricer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockpricer.model.StockPrice;
import com.stockpricer.service.StockPriceService;

@RestController
public class StockPriceController {
		@Autowired
		StockPriceService stockPriceService;
		
		@RequestMapping("/stock/{id}")
	    public StockPrice getStockId(@PathVariable("id") int id) {
			StockPrice supplier= stockPriceService.findById(id);
			stockPriceService.getStock(supplier);
			return supplier;
		
	    }
		
		@RequestMapping(method=RequestMethod.GET)
	    public StockPrice getStockName(@RequestParam(value="name") String name) {
			return stockPriceService.findByName(name);
	    }
}
