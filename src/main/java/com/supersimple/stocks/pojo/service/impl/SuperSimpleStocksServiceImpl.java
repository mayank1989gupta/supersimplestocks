/**
 * 
 */
package com.supersimple.stocks.pojo.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supersimple.stocks.constants.Constants;
import com.supersimple.stocks.manager.SuperSimpleStockManager;
import com.supersimple.stocks.pojo.Stock;
import com.supersimple.stocks.pojo.Trade;
import com.supersimple.stocks.pojo.service.SuperSimpleStocksService;
import com.supersimple.stocks.utils.StockPredicate;

/**
 * @author Mayank
 *
 */
@Service("superSimpleStocksService")
public class SuperSimpleStocksServiceImpl implements SuperSimpleStocksService {

	private static final Logger LOGGER = Logger.getLogger(SuperSimpleStocksServiceImpl.class);

	@Autowired
	SuperSimpleStockManager superSimpleStockManager;

	@Override
	public boolean recordTrade(Trade trade) throws Exception{
		boolean recordResult = Boolean.FALSE;
		try{
			LOGGER.debug("Begin recordTrade with trade object: ");
			LOGGER.debug(trade);

			// trade should be an object
			if(trade == null) {
				throw new Exception("Trade Records is invalid.");
			}

			// stock should be an object
			if(trade.getStock() == null) {
				throw new Exception("Stock is not valid.");
			}

			// shares quantity should be greater than zero
			if(trade.getSharesQuantity() <= 0) {
				throw new Exception("Shares quantity should be greater than 0");
			}

			// shares price should be greater than zero
			if(trade.getPrice().compareTo(new BigDecimal(Constants.ZERO)) <= 0){
				throw new Exception("Shares price should be greater than 0");
			}
			//Recording the trade
			recordResult = superSimpleStockManager.recordTrade(trade);

			// Update the ticker price for the stock
			if(recordResult) {
				trade.getStock().setTickerPrice(trade.getPrice());
			}
		} catch(Exception exception) {
			LOGGER.error("Error when trying to record a trade.", exception);
			throw new Exception("Error when trying to record a trade.", exception);
		}
		return recordResult;//Result
	}


	@Override
	public BigDecimal calculateDividendYield(String stockSymbol) throws Exception{
		BigDecimal dividendYield = new BigDecimal(Constants.ZERO);

		try{
			LOGGER.debug("Calculating Dividend Yield for the stock symbol: "+stockSymbol);
			Stock stock = superSimpleStockManager.fetchStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock == null) {
				throw new Exception("The stock symbol: "+stockSymbol+" is not supported by the Super Simple Stock system.");
			}

			// Ticker price with value zero does not make any sense and could produce a zero division
			if(stock.getTickerPrice().compareTo(new BigDecimal(Constants.ZERO)) <= 0){
				throw new Exception("The ticker price for the stock: "+stockSymbol+" should be greater than zero (0).");
			}
			//fetching the dividend
			dividendYield = stock.getDividendYield();

			LOGGER.debug("Dividend Yield calculated: "+dividendYield);

		} catch(Exception exception) {
			LOGGER.error("Error calculating Dividend Yield for the stock symbol: "+stockSymbol+".", exception);
			throw new Exception("Error calculating Dividend Yield for the stock symbol: "+stockSymbol+".", exception);
		}
		return dividendYield;//result
	}

	@Override
	public BigDecimal calculatePERatio(String stockSymbol) throws Exception{
		BigDecimal peRatio = new BigDecimal(Constants.ZERO);
		try{
			LOGGER.debug("Calculating P/E Ratio for the stock symbol: "+stockSymbol);
			Stock stock = superSimpleStockManager.fetchStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock == null) {
				throw new Exception("The stock symbol: "+stockSymbol+", is not supported by the Super Simple Stock system.");
			}

			// Ticker price with value zero does not make any sense and could produce a zero division
			if(stock.getTickerPrice().compareTo(new BigDecimal(Constants.ZERO)) <= 0) {
				throw new Exception("The ticker price for the stock: " + stockSymbol + ", should be greater than zero (0).");
			}
			//getting the PE ratio
			peRatio = stock.getPeRatio();//PE Ratio
			LOGGER.debug(" P/E Ratiocalculated: "+peRatio);

		} catch(Exception exception) {
			LOGGER.error("Error calculating P/E Ratio for the stock symbol: " + stockSymbol + ".", exception);
			throw new Exception("Error calculating P/E Ratio for the stock symbol: " + stockSymbol + ".", exception);
		}
		return peRatio;
	}


	//API to get Trades Number.</br>
	public int getTradesNumber() throws Exception {
		return superSimpleStockManager.fetchTrades().size();
	}

	//API to calculate Stock Price.</br>
	public BigDecimal calculateStockPrice(String stockSymbol) throws Exception{
		BigDecimal stockPrice = new BigDecimal(Constants.ZERO);

		try {
			LOGGER.debug("Stock Price for the stock symbol: " + stockSymbol);
			Stock stock = superSimpleStockManager.fetchStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock == null) {
				throw new Exception("The stock symbol: "+stockSymbol+"] is not supported by the Super Simple Stock system.");
			}
			//Stock Price
			stockPrice = calculateStockPriceinRange(stockSymbol, 15);
			LOGGER.debug(" Stock Price calculated: " + stockPrice);
		} catch(Exception exception) {
			LOGGER.error("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);
			throw new Exception("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);

		}

		return stockPrice;//Result
	}

	//API to calculate GBCEAllShareIndex
	public BigDecimal calculateGBCEAllShareIndex() throws Exception {
		double allShareIndex = 0.0;

		// Calculate stock price for all stock in the system
		HashMap<String, Stock> stocks = superSimpleStockManager.fetchStocks();
		ArrayList<BigDecimal> stockPrices = new ArrayList<BigDecimal>();
		for(String stockSymbol: stocks.keySet()) {
			BigDecimal stockPrice = calculateStockPriceinRange(stockSymbol, 0);
			if(stockPrice.compareTo(new BigDecimal(0)) > 0){
				stockPrices.add(stockPrice);
			}
		}

		if(stockPrices.size()>=1){
			double[] stockPricesArray = new double[stockPrices.size()];

			for(int i=0; i<=(stockPrices.size()-1); i++) {
				stockPricesArray[i] = stockPrices.get(i).doubleValue();
			}
			// Calculates the GBCE All Share Index
			allShareIndex = StatUtils.geometricMean(stockPricesArray);
		}
		return new BigDecimal(allShareIndex);
	}

	/**
	 * API to calculate Stock Price in Range.</br>
	 * 
	 * @param stockSymbol
	 * @param minutesRange
	 * @return
	 * @throws Exception
	 */
	private BigDecimal calculateStockPriceinRange(String stockSymbol, int minutesRange) throws Exception {
		BigDecimal stockPrice = new BigDecimal(0);

		LOGGER.debug("Trades in the original collection: " + getTradesNumber());

		@SuppressWarnings("unchecked")
		Collection<Trade> trades = CollectionUtils.select(superSimpleStockManager.fetchTrades(), new StockPredicate(stockSymbol, minutesRange));

		LOGGER.debug("Trades in the filtered collection by ["+stockSymbol+","+minutesRange+"]: "+trades.size());

		// Calculate the summation
		BigDecimal shareQuantityAcum = new BigDecimal(0);
		BigDecimal tradePriceAcum = new BigDecimal(0);
		for(Trade trade : trades){
			// Calculating --> Trade Price x Quantity
			tradePriceAcum = tradePriceAcum.add(trade.getPrice().multiply(new BigDecimal(trade.getSharesQuantity())));
			// Accumulate Quantity
			shareQuantityAcum = shareQuantityAcum.add(new BigDecimal(trade.getSharesQuantity()));
		}

		// calculate the stock price
		if(shareQuantityAcum.compareTo(new BigDecimal(0)) > 0) {
			stockPrice = tradePriceAcum.divide(shareQuantityAcum, 2, RoundingMode.HALF_UP);	
		}
		return stockPrice;//result
	}
}
//End of File