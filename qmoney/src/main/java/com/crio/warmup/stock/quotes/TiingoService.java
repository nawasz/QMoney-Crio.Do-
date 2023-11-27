
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {

 private RestTemplate restTemplate;
  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }
  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException,StockQuoteServiceExceptions {
    // TODO Auto-generated method stub
    List<Candle> CandleList ;
  
      String uri = buildUri(symbol,from,to);
      try{
        String stocks  =   restTemplate.getForObject(uri, String.class);
        ObjectMapper objectMapper = getObjectMapper();
       TiingoCandle[] resurl = objectMapper.readValue(stocks,TiingoCandle[].class);
         
        if(resurl != null){  
         CandleList= Arrays.asList(resurl);
        }
        else{
         CandleList =Arrays.asList(new TiingoCandle[0]);
        }
      } catch(NullPointerException e){
        throw new StockQuoteServiceExceptions("Tiingo  returned invalid response");
      }
    }

    private String buildUri(String symbol, LocalDate from, LocalDate to) {
    String token ="10219a570b3176f7370876279e6428ea6ccf3e4a";
       String uriTemplate = "https://api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
            + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";
            String url = uriTemplate.replace("$APIKEY",token).replace("$SYMBOL",symbol)
                          .replace("$STARTDATE",from.toString()).replace("$ENDDATE",to.toString());
          
                          return url;
  }


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.

}
