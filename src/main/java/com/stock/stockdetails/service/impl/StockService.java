package com.stock.stockdetails.service.impl;

import com.opencsv.CSVReader;
import com.stock.stockdetails.model.StockResponseObject;
import com.stock.stockdetails.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.*;


@Service
public class StockService implements IStockService {

    private static  final Logger logger= LoggerFactory.getLogger(StockService.class);

    @Override
    public StockResponseObject getCloseRateOverTime(String day, String month, String year) throws IOException {

        logger.info("In GetCLoseOverTime method Entry");
            Map<String, Map<String, Map<String,  String>>> stockYearMap = parseStockCsv();
            StockResponseObject stock= new StockResponseObject();
            String closeRate = null;

            if (day != null && month != null && year != null) {

                if(stockYearMap!=null && stockYearMap.size()>0) {
                    Map<String, Map<String, String>> monthMap = stockYearMap.get(year);
                    if(monthMap!=null && monthMap.size()>0) {
                        Map<String, String> dayMap = monthMap.get(month);
                        if(dayMap!=null && dayMap.size()>0) {
                            closeRate = dayMap.get(day);
                        }else{
                            return stock=null;
                        }
                    }else{
                        return stock=null;
                    }
                }else{
                    return stock=null;
                }
                String monthName= getMonth(Integer.parseInt(month));
                stock.setDate(day + "-"+ monthName + "-" + year);
                stock.setCloseRate(closeRate);

            } else if (day == null && month != null && year != null) {

                Double finalCloseRate=0.0D;

                if(stockYearMap!=null && stockYearMap.size()>0) {
                    Map<String, Map<String, String>> monthMap = stockYearMap.get(year);
                    if(monthMap!=null && monthMap.size()>0) {
                        Map<String,  String> dayMap=monthMap.get(month);
                        for (Map.Entry entry:dayMap.entrySet()) {
                            finalCloseRate=finalCloseRate+ Double.parseDouble(entry.getValue().toString());
                        }
                    }else{
                        return stock=null;
                    }
                }else{
                    return stock=null;
                }
                String monthName= getMonth(Integer.parseInt(month));
                stock.setDate(monthName+"-"+year);
                stock.setCloseRate(finalCloseRate.toString());

            } else if (day == null && month == null && year != null) {
                Double finalCloseRate = 0.0D;
                if(stockYearMap!=null && stockYearMap.size()>0) {
                    Map<String, Map<String, String>> monthMap = stockYearMap.get(year);
                    if(monthMap!=null && monthMap.size()>0) {
                        for (String key : monthMap.keySet()) {
                            Map<String, String> dayMap = monthMap.get(key);
                            for (Map.Entry entry : dayMap.entrySet()) {
                                finalCloseRate = finalCloseRate + Double.parseDouble(entry.getValue().toString());
                            }
                        }
                    }else{
                        stock=null;
                    }
                } else{
                    stock=null;
                }

                stock.setDate(year);
                stock.setCloseRate(finalCloseRate.toString());
            } else {
                    stock=null;
            }
            logger.info("In GetCLoseOverTime method Exit");
            return stock ;
    }

    @Override
    public List<StockResponseObject> getAverageCloseRateOverPeriod(String day, String month, String year) throws IOException{

        logger.info("In getAverageCloseRateOverPeriod Entry method");
        Map<String, Map<String, Map<String,  String>>> stockYearMap = parseStockCsv();
        StockResponseObject stock= new StockResponseObject();
        List<StockResponseObject> stockList = new ArrayList<>();

         if(day!=null && month!=null && year!=null){
             String closeRate=null;
             if(stockYearMap!=null && stockYearMap.size()>0) {
                 Map<String, Map<String, String>> monthMap = stockYearMap.get(year);
                 if(monthMap!=null && monthMap.size()>0) {
                     Map<String, String> dayMap = monthMap.get(month);
                     if(dayMap!=null && dayMap.size()>0) {
                         closeRate = dayMap.get(day);
                     }else{
                         return stockList=null;
                     }
                 }else{
                     return stockList=null;
                 }
             }else{
                 return stockList=null;
             }
            String monthName= getMonth(Integer.parseInt(month));
            stock.setDate(day + "-"+ monthName + "-" + year);
            stock.setCloseRate(closeRate);
            stockList.add(stock);
         }
         else if(day==null && month!=null && year!=null) {
             String finalCloseRate=null;
             if(stockYearMap!=null && stockYearMap.size()>0) {
                 Map<String, Map<String, String>> monthMap = stockYearMap.get(year);
                 if(monthMap!=null && monthMap.size()>0) {
                     Map<String,  String> dayMap=monthMap.get(month);
                     String monthName= getMonth(Integer.parseInt(month));
                         for (Map.Entry entry:dayMap.entrySet()) {
                             StockResponseObject stockResponseObject= new StockResponseObject();
                             stockResponseObject.setDate(entry.getKey()+"-"+monthName+"-"+year);
                             stockResponseObject.setCloseRate(entry.getValue().toString());
                             stockList.add(stockResponseObject);
                         }

                 }else{
                     return stockList=null;
                 }
             }else{
                 return stockList=null;
             }




         }else if(day==null && month==null && year!=null ){

             if(stockYearMap!=null && stockYearMap.size()>0) {
                 Map<String, Map<String, String>> monthMap = stockYearMap.get(year);
                 if (monthMap != null && monthMap.size() > 0) {


                     for (String key : monthMap.keySet()) {
                         Map<String, String> dayMap = monthMap.get(key);
                         String monthName = getMonth(Integer.parseInt(key));
                         Double finalCloseRate = 0.0D;
                         StockResponseObject stockResponseObject = new StockResponseObject();
                         stockResponseObject.setDate(monthName + "-" + year);
                         for (Map.Entry entry : dayMap.entrySet()) {
                             finalCloseRate = finalCloseRate + Double.parseDouble(entry.getValue().toString());
                             finalCloseRate = finalCloseRate / dayMap.size();
                             stockResponseObject.setCloseRate(finalCloseRate.toString());
                         }
                         stockList.add(stockResponseObject);
                     }
                 } else {
                    stockList=null;
                 }
             }else{
                 stockList=null;
             }
         }else{
             stockList=null;
         }
        logger.info("In GetAverageCloseRateOverTime Exit Method");
         return stockList;

    }



    public Map<String, Map<String, Map<String,  String>>> parseStockCsv() throws IOException {

        logger.info("In ParseStockCsv Method Entry");
        String csvFile= ResourceUtils.getFile("classpath:F.csv").getAbsolutePath();
        Map<String,  String> dayMap = new HashMap<>();
        Map<String, Map<String,  String>> monthMap = new HashMap<>();
        Map<String, Map<String, Map<String,  String>>> yearMap = new HashMap<>();

        try(CSVReader csvReader = new CSVReader(new FileReader(csvFile), ',', '\'', 1)) {

            String[] row = null;
            List<String[]> content=csvReader.readAll();
            for (Object object : content)
            {
                row = (String[]) object;
                logger.info("Date=" + row[0] + " , CloseRate = " + row[4]);
                String[] dateArray=  row[0].split("/");

                if (yearMap.containsKey(dateArray[2])) {
                    monthMap = yearMap.get(dateArray[2]);
                    if (monthMap.containsKey(dateArray[0])) {
                        dayMap = monthMap.get(dateArray[0]);
                        dayMap.put(dateArray[1],row[4]);
                    } else {
                        dayMap = new HashMap<>();
                        dayMap.put(dateArray[1],row[4]);
                    }
                    monthMap.put(dateArray[0], dayMap);
                    yearMap.put(dateArray[2], monthMap);
                } else {
                    monthMap = new HashMap<>();
                    dayMap = new HashMap<>();
                    dayMap.put(dateArray[1],row[4]);
                    monthMap.put(dateArray[0], dayMap);
                    yearMap.put(dateArray[2], monthMap);
                }
            }
        }
        logger.info("In ParseStockCsv Method Exit");
        return yearMap;
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    public ResponseEntity validateMonthAndDay(String month, String day){

        ResponseEntity responseEntity = null;

        if (day!=null && !day.isEmpty()){
            if((Integer.parseInt(day))<= 31 && (Integer.parseInt(day))>0){
            }
            else {
                responseEntity = new ResponseEntity<Object>(new Exception("Invalid Request"), HttpStatus.EXPECTATION_FAILED);
            }
        }
        if (month!=null && !month.isEmpty()){
            if((Integer.parseInt(month))<= 12 && (Integer.parseInt(month))>0){
            }
            else {
                responseEntity = new ResponseEntity<Object>(new Exception("Invalid Request"), HttpStatus.EXPECTATION_FAILED);
            }
        }
        return responseEntity;
    }

}
