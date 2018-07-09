package com.stock.stockdetails;

import com.stock.stockdetails.service.impl.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockDetailsApplication {

	private static  final Logger logger= LoggerFactory.getLogger(StockDetailsApplication.class);

	public static void main(String[] args) {
		logger.info("StockDetails Main Method Entry Point");
		SpringApplication.run(StockDetailsApplication.class, args);
		logger.info("StockDetails Main Method Exit Point");
	}
}
