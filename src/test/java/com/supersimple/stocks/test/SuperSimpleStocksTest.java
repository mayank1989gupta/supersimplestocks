/**
 * 
 * Test class for testing the App.
 * 
 * Also has the method to set up data.</br>
 */
package com.supersimple.stocks.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.supersimple.stocks.enums.StockType;
import com.supersimple.stocks.enums.TradeType;
import com.supersimple.stocks.manager.SuperSimpleStockManager;
import com.supersimple.stocks.pojo.Stock;
import com.supersimple.stocks.pojo.Trade;
import com.supersimple.stocks.pojo.service.SuperSimpleStocksService;
import com.supersimple.stocks.utils.SuperSimpleStocksUtils;

/**
 * @author Mayank
 *
 * Test class for testing the App.
 * Also has the method to set up data.</br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperSimpleStocksTest {

	private Logger logger = Logger.getLogger(SuperSimpleStocksTest.class);

	@Autowired
	SuperSimpleStocksService superSimpleStocksService;

	@Autowired
	SuperSimpleStockManager superSimpleStockManager;

	@Autowired
	SuperSimpleStocksUtils superSimpleStocksUtils;

	Map<String, Stock> stocks = null;
	List<Trade> trades = null;


	@Before
	public void setup() {
		populateStocksANDTrades();
	}
	
	@After
	public void destroy() {
		stocks = null;
		trades = null;
	}

	@Test
	public void recordTrade() {
		Assert.assertNotNull(superSimpleStocksService);
		Assert.assertNotNull(trades);
		try {
			// Insert many trades in the stock system
			boolean result = Boolean.FALSE;
			for(Trade trade: trades) {
				result = superSimpleStocksService.recordTrade(trade);
				Assert.assertTrue(result);
			}

			// After record trades, the number of trades should be equal to the trades list
			int tradeCount = superSimpleStockManager.fetchTrades().size();
			logger.info("Trades number: "+ tradeCount);
		} catch (Exception exception) {
			logger.error(exception);
			Assert.assertTrue(Boolean.FALSE);
		}

		logger.info("Finish recordTrade ...OK");
	}


	@Test
	public void calculateDividendYieldTest() {
		logger.info("Start  calculateDividendYieldTest ...");

		try {
			//verify it's not null object
			Assert.assertNotNull(superSimpleStocksService);
			int tradeCount = superSimpleStockManager.fetchTrades().size();
			logger.info("Trades number: "+tradeCount);
			
			//To Set up data
			if(tradeCount == 0) {
				recordTrade();//Record Trades
			}

			// Calculates the dividend yield for the stock symbol
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			for(String stockSymbol: stockSymbols) {
				BigDecimal dividendYield = superSimpleStocksService.calculateDividendYield(stockSymbol);
				logger.info(stockSymbol+" - DividendYield calculated: "+dividendYield);
				Assert.assertTrue(dividendYield.compareTo(new BigDecimal(0)) >= 0);
			}

		} catch(Exception exception) {
			logger.error(exception);
			Assert.assertTrue(Boolean.FALSE);
		}

		logger.info("Finish calculateDividendYieldTest ...OK");
	}


	@Test
	public void calculateStockPriceTest() {
		try {
			// Create the stock service and verify it's not null object
			Assert.assertNotNull(superSimpleStocksService);
			int tradeCount = superSimpleStockManager.fetchTrades().size();
			logger.info("Trades number: "+tradeCount);

			if(tradeCount == 0) {
				recordTrade();//Record Trades
			}

			// Calculates the Stock Price for all stocks
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			for(String stockSymbol: stockSymbols) {
				BigDecimal stockPrice = superSimpleStocksService.calculateStockPrice(stockSymbol);
				logger.info(stockSymbol+" - Stock Price calculated: "+stockPrice);
				Assert.assertTrue(stockPrice.compareTo(new BigDecimal(0)) >= 0);
			}


		} catch(Exception exception) {
			logger.error(exception);
			Assert.assertTrue(Boolean.FALSE);
		}

	}

	/**
	 * 
	 */
	@Test
	public void calculateGBCEAllShareIndexTest(){
		try{
			// Create the stock service and verify it's not null object
			Assert.assertNotNull(superSimpleStocksService);

			int tradesNumber = superSimpleStockManager.fetchTrades().size();
			logger.info("Trades number: "+tradesNumber);

			if(tradesNumber == 0) {
				recordTrade();//Record Trades
			}
			
			BigDecimal gbceallShareIndex = superSimpleStocksService.calculateGBCEAllShareIndex();
			logger.info("GBCE All Share Index: "+gbceallShareIndex);
			Assert.assertTrue(gbceallShareIndex.compareTo(new BigDecimal(0)) >= 0);

		}catch(Exception exception){
			logger.error(exception);
			Assert.assertTrue(false);
		}

	}

	//Method to populate the test data.
	private void populateStocksANDTrades() {
		stocks = new HashMap<>();//Stocks
		trades = new ArrayList<>();//Trades

		//TEA
		Stock stock1 = new Stock();
		stock1.setStockSymbol("TEA");
		stock1.setStockType(StockType.COMMON);
		stock1.setLastDividend(new BigDecimal(0));
		stock1.setFixedDividend(new BigDecimal(0));
		stock1.setParValue(new BigDecimal(100));
		//adding to map
		stocks.put("TEA", stock1);

		//POP
		Stock stock2 = new Stock();
		stock2.setStockSymbol("POP");
		stock2.setStockType(StockType.COMMON);
		stock2.setLastDividend(new BigDecimal(8));
		stock2.setFixedDividend(new BigDecimal(0));
		stock2.setParValue(new BigDecimal(100));
		//adding to map
		stocks.put("POP", stock2);

		//ALE
		Stock stock3 = new Stock();
		stock3.setStockSymbol("ALE");
		stock3.setStockType(StockType.COMMON);
		stock3.setLastDividend(new BigDecimal(23));
		stock3.setFixedDividend(new BigDecimal(0));
		stock3.setParValue(new BigDecimal(60));
		//adding to map
		stocks.put("ALE", stock3);

		//ALE
		Stock stock4 = new Stock();
		stock4.setStockSymbol("GIN");
		stock4.setStockType(StockType.PREFERRED);
		stock4.setLastDividend(new BigDecimal(8));
		stock4.setFixedDividend(new BigDecimal(2));
		stock4.setParValue(new BigDecimal(100));
		//adding to map
		stocks.put("GIN", stock4);

		//ALE
		Stock stock5 = new Stock();
		stock5.setStockSymbol("JOE");
		stock5.setStockType(StockType.COMMON);
		stock5.setLastDividend(new BigDecimal(13));
		stock5.setFixedDividend(new BigDecimal(0));
		stock5.setParValue(new BigDecimal(250));
		//adding to map
		stocks.put("JOE", stock5);

		//Stocks Map is populated.

		//Populating the Trades
		Trade trade1 = new Trade();
		trade1.setTimeStamp(superSimpleStocksUtils.getNowMovedMinutes(-30));
		trade1.setStock(stocks.get("TEA"));
		trade1.setTradeIndicator(TradeType.SELL);
		trade1.setSharesQuantity(20);
		trade1.setPrice(new BigDecimal(20.23));
		//Adding to list
		trades.add(trade1);

		Trade trade2 = new Trade();
		trade2.setTimeStamp(superSimpleStocksUtils.getNowMovedMinutes(-28));
		trade2.setStock(stocks.get("POP"));
		trade2.setTradeIndicator(TradeType.BUY);
		trade2.setSharesQuantity(70);
		trade2.setPrice(new BigDecimal(8.5));
		//Adding to list
		trades.add(trade2);


		Trade trade3 = new Trade();
		trade3.setTimeStamp(superSimpleStocksUtils.getNowMovedMinutes(-26));
		trade3.setStock(stocks.get("TEA"));
		trade3.setTradeIndicator(TradeType.BUY);
		trade3.setSharesQuantity(400);
		trade3.setPrice(new BigDecimal(15.5));
		//Adding to list
		trades.add(trade3);

		Trade trade4 = new Trade();
		trade4.setTimeStamp(superSimpleStocksUtils.getNowMovedMinutes(-24));
		trade4.setStock(stocks.get("ALE"));
		trade4.setTradeIndicator(TradeType.BUY);
		trade4.setSharesQuantity(200);
		trade4.setPrice(new BigDecimal(25.5));
		//Adding to list
		trades.add(trade4);


		Trade trade5 = new Trade();
		trade5.setTimeStamp(superSimpleStocksUtils.getNowMovedMinutes(-24));
		trade5.setStock(stocks.get("GIN"));
		trade5.setTradeIndicator(TradeType.BUY);
		trade5.setSharesQuantity(200);
		trade5.setPrice(new BigDecimal(25.5));
		//Adding to list
		trades.add(trade5);

		Trade trade6 = new Trade();
		trade6.setTimeStamp(superSimpleStocksUtils.getNowMovedMinutes(-24));
		trade6.setStock(stocks.get("JOE"));
		trade6.setTradeIndicator(TradeType.SELL);
		trade6.setSharesQuantity(100);
		trade6.setPrice(new BigDecimal(35.5));
		//Adding to list
		trades.add(trade6);
	}
}
