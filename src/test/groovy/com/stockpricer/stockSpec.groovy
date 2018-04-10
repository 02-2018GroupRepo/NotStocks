package com.stockpricer

import com.stockpricer.dao.StockPriceDao
import com.stockpricer.model.StockPrice
import com.stockpricer.service.StockPriceService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class stockSpec extends Specification {
	//@Autowired
	//StockPriceService stock;
	def	"get operating cash"(){
		 given: "a StockPriceService"
		 StockPriceService stock = new StockPriceService();
		 and :"StockPrice Dao"
		 StockPriceDao stockpriceDao = Stub(StockPriceDao.class);
		 and: "StockPrice model"
		
		StockPrice stockPrice = stock.findByName("Store");
		 when: "i send a request to Store"
	 
		 then:"i want to get current operating cash"
	}

}
