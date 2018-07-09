package com.stock.stockdetails.service;

import com.stock.stockdetails.model.StockResponseObject;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface IStockService {

   public StockResponseObject getCloseRateOverTime(String day, String month, String year) throws IOException;
   public List<StockResponseObject> getAverageCloseRateOverPeriod(String day, String month, String year) throws IOException;
   public Map<String, Map<String, Map<String,  String>>> parseStockCsv() throws IOException;
   public ResponseEntity validateMonthAndDay(String month, String day);


}