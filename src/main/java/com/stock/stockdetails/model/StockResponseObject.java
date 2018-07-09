package com.stock.stockdetails.model;

public class StockResponseObject {

    private String date ;
    private String closeRate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCloseRate() {
        return closeRate;
    }

    public void setCloseRate(String closeRate) {
        this.closeRate = closeRate;
    }
}
