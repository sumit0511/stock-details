package com.stock.stockdetails.controller;

import com.stock.stockdetails.model.StockResponseObject;
import com.stock.stockdetails.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/stock")
public class StockDetailsController {

    private static  final Logger logger= LoggerFactory.getLogger(StockDetailsController.class);

    @Autowired
    private IStockService iStockService;

    @RequestMapping(value = "/closeRateOverTime" ,method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> closeRateOverTime(
            @RequestParam(value = "day", required = false) String day ,
            @RequestParam(value = "month",   required = false) String month,
            @RequestParam(value = "year", required = false) String year) throws IOException {
        logger.info("In CLoseRateOverTime Entry point");

        ResponseEntity responseEntity = null;

        StockResponseObject stock=iStockService.getCloseRateOverTime(day,month,year);
        if (stock != null) {
            responseEntity = new ResponseEntity<Object>(stock, HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity<Object>("Invalid Input", HttpStatus.EXPECTATION_FAILED);
        }
        logger.info("In CLoseRateOverTime Exit Point");
        return responseEntity;
    }

    @RequestMapping(value = "/averageCloseRateOverPeriod" ,method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> averageCloseRateOverPeriod(
            @RequestParam(value = "day", required = false) String day ,
            @RequestParam(value = "month",   required = false) String month,
            @RequestParam(value = "year", required = false) String year) throws IOException {

        logger.info("In AverageCloseRateOverPeriod Entry Point");

        ResponseEntity responseEntity = null;

        List<StockResponseObject> stockResponseObjectList=iStockService.getAverageCloseRateOverPeriod(day,month,year);
        if (stockResponseObjectList != null) {
            responseEntity = new ResponseEntity<Object>(stockResponseObjectList, HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity<Object>("Invalid Input Request", HttpStatus.EXPECTATION_FAILED);
        }
        logger.info("In AverageCloseRateOverPeriod Exit Point");
        return responseEntity;


    }

}
