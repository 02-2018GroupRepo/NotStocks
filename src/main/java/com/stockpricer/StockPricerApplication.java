package com.stockpricer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StockPricerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPricerApplication.class, args);
	}
}
