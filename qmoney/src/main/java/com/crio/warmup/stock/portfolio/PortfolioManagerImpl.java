
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.crio.warmup.stock.quotes.StockQuotesService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class PortfolioManagerImpl implements PortfolioManager {

@Autowired
 private RestTemplate restTemplate;

 @Autowired
 private StockQuotesService stockQuotesService;


  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  public PortfolioManagerImpl(StockQuotesService stockQuotesService) {
    this.stockQuotesService=stockQuotesService;
  }


  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF






  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to) throws Exception{
        // String url=buildUri(symbol, from, to);
        // TiingoCandle[] candles=restTemplate.getForObject(url, TiingoCandle[].class);
        // List<Candle> candleList=Arrays.asList(candles);
        //  return candleList;

        return stockQuotesService.getStockQuote(symbol, from, to);
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
    .queryParam("endDate",endDate).queryParam("token","46903f5f94e1bc20668ec874e214dcce5e74e375")
    .buildAndExpand(symbol)
    .toUriString();
  }

  static Double getOpeningPriceOnStartDate(List<Candle> candles) {
    return candles.get(0).getOpen();
 }


 public static Double getClosingPriceOnEndDate(List<Candle> candles) {
    return candles.get(candles.size()-1).getClose();
 }

 static double totalYears(LocalDate startDate,LocalDate endDate){
  double yearNo=endDate.getYear()-startDate.getYear();
  double yearFromDay=(endDate.getDayOfYear()-startDate.getDayOfYear())/365.00;
  return yearNo+yearFromDay;
}

public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
    PortfolioTrade trade, Double buyPrice, Double sellPrice) {
    double  totalReturns = (sellPrice - buyPrice) / buyPrice;
    double years=totalYears(trade.getPurchaseDate(), endDate);
    double annualizedReturns = Math.pow((1 + totalReturns),(1 / years)) - 1;
    return new AnnualizedReturn(trade.getSymbol(), annualizedReturns,totalReturns);
}

  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate) throws StockQuoteServiceException {
        try {
             // TODO Auto-generated method stub
    List<AnnualizedReturn> ans=new ArrayList<AnnualizedReturn>();
    for(PortfolioTrade trade:portfolioTrades){
      List<Candle> candles=getStockQuote(trade.getSymbol(),trade.getPurchaseDate(),endDate);
      double openingPrice=getOpeningPriceOnStartDate(candles);
      double closingPrice=getClosingPriceOnEndDate(candles);
      AnnualizedReturn annualizedReturn=calculateAnnualizedReturns(endDate,trade,openingPrice,closingPrice);
    // System.out.println(annualizedReturn);
      ans.add(annualizedReturn);
    }
    List<AnnualizedReturn> output=ans.stream().sorted(Comparator.comparingDouble(AnnualizedReturn::getTotalReturns).reversed())
    .collect(Collectors.toList());
    return output;
          
        } catch (Exception e) {
          //TODO: handle exception
          e.printStackTrace();
          throw new StockQuoteServiceException(e.getMessage());
        }
 
  }
  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturnParallel(
      List<PortfolioTrade> portfolioTrades, LocalDate endDate, int numThreads)
      throws InterruptedException, StockQuoteServiceException {
    // TODO Auto-generated method stub
    ExecutorService executorService=Executors.newFixedThreadPool(numThreads);
    List<Future<AnnualizedReturn>> futureList=new ArrayList();
    List<AnnualizedReturn> ans=new ArrayList();
    for(PortfolioTrade trade:portfolioTrades){
      Future<AnnualizedReturn> future=executorService.submit(()->{
        try {
          List<Candle> candles=getStockQuote(trade.getSymbol(),trade.getPurchaseDate(),endDate);
          double openingPrice=getOpeningPriceOnStartDate(candles);
          double closingPrice=getClosingPriceOnEndDate(candles);
          return calculateAnnualizedReturns(endDate,trade,openingPrice,closingPrice);
        } catch (Exception e) {
          //TODO: handle exception
          e.printStackTrace();
          throw new StockQuoteServiceException(e.getMessage());
        }
      }); 
      futureList.add(future);
    }

    executorService.shutdown();
    executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    for(Future<AnnualizedReturn> futureItem:futureList){
      try {
        AnnualizedReturn ret=futureItem.get();
        if(ret!=null){
          ans.add(ret);
        }
      } catch (Exception e) {
        //TODO: handle exception
        e.printStackTrace();
        throw new StockQuoteServiceException(e.getMessage());
        
      }
    }

    return ans.stream().sorted(Comparator.comparingDouble(AnnualizedReturn::getTotalReturns).reversed())
    .collect(Collectors.toList());
  }




  // ¶TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Modify the function #getStockQuote and start delegating to calls to
  //  stockQuoteService provided via newly added constructor of the class.
  //  You also have a liberty to completely get rid of that function itself, however, make sure
  //  that you do not delete the #getStockQuote function.

}
