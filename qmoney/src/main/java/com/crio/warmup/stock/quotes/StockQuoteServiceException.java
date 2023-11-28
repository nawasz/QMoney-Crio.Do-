package com.crio.warmup.stock.quotes;

public class StockQuoteServiceException extends NullPointerException {
  public StockQuoteServiceException(String message){
    super(message);
  }
}
