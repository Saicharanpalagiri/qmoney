
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;



public class PortfolioManagerApplication_BASE_5440 {
  // static String token;
  // public PortfolioManagerApplication() {
  //   this.token = "b331dd53bf162390532b93a0b5e1df3dd58986ec";
  // }
  public static String getToken() {
     return "b331dd53bf162390532b93a0b5e1df3dd58986ec";
   // return "46903f5f94e1bc20668ec874e214dcce5e74e375";
  }


  // public void setToken(String token) {
  //   this.token = token;
  // }
  

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

 





  


  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
   
      File file=resolveFileFromResources(args[0]);
      ObjectMapper objectMapper = getObjectMapper();
      PortfolioTrade[] input=objectMapper.readValue(file, PortfolioTrade[].class);
      List<String> output=new ArrayList<>();
      for(PortfolioTrade trade:input){
        output.add(trade.getSymbol());
      }
       return output;
  }





  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.









  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication_BASE_5440.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
      return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
      }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {
      String valueOfArgument0 = "trades.json";
      String resultOfResolveFilePathArgs0 ="home/crio-user/workspace/charan517214-ME_QMONEY_V2/qmoney/bin/main/resources/trades.json";
      String toStringOfObjectMapper = getObjectMapper().toString();
      String functionNameFromTestFileInStackTrace = "mainReadFile";
      String lineNumberFromTestFileInStackTrace = "141";
 
 
     return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
         toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
         lineNumberFromTestFileInStackTrace});
    
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    //  return Collections.emptyList();
    LocalDate lastDate=LocalDate.parse(args[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    RestTemplate restTemplate=new RestTemplate();
    List<PortfolioTrade> trades=readTradesFromJson(args[0]);
    List<TotalReturnsDto> ans=new ArrayList();
    for(PortfolioTrade trade:trades){
      String url=prepareUrl(trade,lastDate,"b331dd53bf162390532b93a0b5e1df3dd58986ec");
      TiingoCandle[] candles=restTemplate.getForObject(url, TiingoCandle[].class);
      TiingoCandle candle=candles[candles.length-1];
      TotalReturnsDto tDto=new TotalReturnsDto(trade.getSymbol(), candle.getClose());
      ans.add(tDto);
  
      // System.out.println(candles);
      // if(candles!=null && !candles.isEmpty()){
      //   System.out.println("hi");
      //   Candle cp=candles.get(candles.size()-1);
      //   System.out.println(cp);
      //   // TotalReturnsDto tDto=new TotalReturnsDto(trade.getSymbol(), cp);
      //   // ans.add(tDto);
      // }
      // System.out.println(ans);
    }
    // System.out.println(ans);
    List<String> sortedList=ans.stream().
    sorted(Comparator.comparingDouble(TotalReturnsDto::getClosingPrice))
    .map(TotalReturnsDto::getSymbol).collect(Collectors.toList());

    // List<String> ansList=new ArrayList();

    return sortedList;
  }

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
    
    //  return Collections.emptyList();
    File file=resolveFileFromResources(filename);
    ObjectMapper objectMapper = getObjectMapper();
    PortfolioTrade[] input=objectMapper.readValue(file, PortfolioTrade[].class);
    List<PortfolioTrade> output=new ArrayList<>();
    for(PortfolioTrade trade:input){
      output.add(trade);
    }
     return output;
  }


  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    // LocalDate startDate=trade.getPurchaseDate();

    String URl="https://api.tiingo.com/tiingo/daily/{trade}/prices";
    LocalDate startDate=trade.getPurchaseDate();
    // return URl+"?startDate="+startDate+"&endDate="+endDate+"&token="+token;
    return UriComponentsBuilder.fromUriString(URl)
    .queryParam("startDate", startDate)
    .queryParam("endDate",endDate).queryParam("token",token)
    .buildAndExpand(trade.getSymbol())
    .toUriString();
  }
  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) {
     return candles.get(0).getOpen();
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
     return candles.get(candles.size()-1).getClose();
  }


  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
     String url=prepareUrl(trade, endDate, token); 
     RestTemplate restTemplate=new RestTemplate();
     TiingoCandle[] candles=restTemplate.getForObject(url, TiingoCandle[].class);
    List<Candle> candleList=Arrays.asList(candles);
     return candleList;
  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
      List<AnnualizedReturn> ans=new ArrayList<AnnualizedReturn>();
      List<PortfolioTrade> trades=readTradesFromJson(args[0]);
      LocalDate endDate=LocalDate.parse(args[1],DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      for(PortfolioTrade trade:trades){
        List<Candle> candles=fetchCandles(trade,endDate,"b331dd53bf162390532b93a0b5e1df3dd58986ec");
        double openingPrice=getOpeningPriceOnStartDate(candles);
        double closingPrice=getClosingPriceOnEndDate(candles);
        AnnualizedReturn annualizedReturn=calculateAnnualizedReturns(endDate,trade,openingPrice,closingPrice);
        ans.add(annualizedReturn);
      }
      List<AnnualizedReturn> output=ans.stream().sorted(Comparator.comparingDouble(AnnualizedReturn::getTotalReturns).reversed())
      .collect(Collectors.toList());
      return output;
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

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













  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    // printJsonObject(mainReadFile(args));


    // printJsonObject(mainReadQuotes(args));



    printJsonObject(mainCalculateSingleReturn(args));

  }
}

