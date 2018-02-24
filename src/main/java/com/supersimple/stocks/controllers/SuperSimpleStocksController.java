/**
 * 
 */
package com.supersimple.stocks.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supersimple.stocks.manager.SuperSimpleStockManager;
import com.supersimple.stocks.pojo.Trade;
import com.supersimple.stocks.pojo.service.SuperSimpleStocksService;

/**
 * @author Mayank
 * 
 * Sample Controller which could be extended further for other use cases.
 *
 */
@RestController
public class SuperSimpleStocksController {

	private Logger logger = Logger.getLogger(SuperSimpleStocksController.class);
	
	@Autowired
	SuperSimpleStocksService superSimpleStocksService;

	@Autowired
	SuperSimpleStockManager superSimpleStockManager;
	
	@GetMapping("/")
	public String test() {
		return "Hello Spring Boot is on!!";
	}
	
	/**
	 * Service to record Trade.</br>
	 * 
	 * @return
	 */
	@PostMapping("/recordTrade")
	public String recordTrade() {

		logger.info("Start  recordATradeTest ...");
		ArrayList<Trade> tradeList = new ArrayList<>();
		logger.info("Trade List size: " + tradeList.size());

		try{
			// Initial trades are empty, means, trades number equals to zero (0)
			int tradesNumber = superSimpleStockManager.fetchTrades().size();
			logger.info("Trades number: "+tradesNumber);

			// Insert many trades in the stock system
			for(Trade trade: tradeList){
				boolean result = superSimpleStocksService.recordTrade(trade);
				logger.info(result);
			}

			// After record trades, the number of trades should be equal to the trades list
			tradesNumber = superSimpleStockManager.fetchTrades().size();
			logger.info("Trades number: " + tradesNumber);
		} catch(Exception exception) {
			logger.error(exception);
		}

		return "Finish recordATradeTest ...OK";
	}
	
	@GetMapping("/calculateDividendYield")
	public String calculateDividendYield() {
		logger.info("Start  calculateDividendYieldTest ...");

		try {
			int tradesNumber = superSimpleStockManager.fetchTrades().size();
			logger.info("Trades number: "+tradesNumber);

			// Calculates the dividend yield for the stock symbol
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			BigDecimal dividendYield = null;
			for(String stockSymbol: stockSymbols) {
				dividendYield = superSimpleStocksService.calculateDividendYield(stockSymbol);
				logger.info(stockSymbol+" - DividendYield calculated: "+dividendYield);
			}
		} catch(Exception exception){
			logger.error(exception);
		}

		return "success";
	}
	
	
	//Add more use cases - sapmples in the test class
	
}
