
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TiingoService implements StockQuotesService {

  public static final String TOKEN="46903f5f94e1bc20668ec874e214dcce5e74e375";

 private RestTemplate restTemplate;

  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public static String getToken() {
    return "46903f5f94e1bc20668ec874e214dcce5e74e375";
  
 }

 private static ObjectMapper getObjectMapper() {
  ObjectMapper objectMapper = new ObjectMapper();
  objectMapper.registerModule(new JavaTimeModule());
  return objectMapper;
}

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws StockQuoteServiceException {
        String url=buildUri(symbol, from, to);
        List<Candle> output;
        String candles=restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper=getObjectMapper();
        TiingoCandle[] tiingoCandles;
        try {
          tiingoCandles = objectMapper.readValue(candles, TiingoCandle[].class);
        if(tiingoCandles!=null){
          output=Arrays.asList(tiingoCandles);
        }else{
          output=Arrays.asList(new TiingoCandle[0]);
        }
        return output;
      } catch (Exception e) {
        // TODO Auto-generated catch block
       throw new StockQuoteServiceException(e.getMessage());
      }
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    //  String uriTemplate = "https:api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
    //       + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";

    //       return "";
  String URl="https://api.tiingo.com/tiingo/daily/{trade}/prices";
  // LocalDate startDate=trade.getPurchaseDate();
  // return URl+"?startDate="+startDate+"&endDate="+endDate+"&token="+token;
  return UriComponentsBuilder.fromUriString(URl)
  .queryParam("startDate", startDate)
  .queryParam("endDate",endDate).queryParam("token",TOKEN)
  .buildAndExpand(symbol)
  .toUriString();
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
