/**
 * 
 */
package com.supersimple.stocks.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.supersimple.stocks.manager.SuperSimpleStockManager;
import com.supersimple.stocks.pojo.Stock;
import com.supersimple.stocks.pojo.Trade;

/**
 * @author Mayank
 *
 */
@Service("superSimpleStockService")
public class SuperSimpleStockManagerImpl implements SuperSimpleStockManager {

	private static final Logger LOGGER = Logger.getLogger(SuperSimpleStockManagerImpl.class);

	private HashMap<String, Stock> stocks = new HashMap<>();
	private ArrayList<Trade> trades = new ArrayList<>();

	@Override
	public HashMap<String, Stock> fetchStocks() {
		return stocks;//Stocks
	}
	
	/**
	 * 
	 * API to add to stocks.</br>
	 * 
	 * @param stockName
	 * @param stock
	 */
	private void addToStocks(String stockName, Stock stock) {
		stocks.put(stockName, stock);
	}

	@Override
	public ArrayList<Trade> fetchTrades() {
		return trades;//Trades
	}

	@Override
	public boolean recordTrade(Trade trade) throws Exception {
		boolean result = Boolean.FALSE;
		try {
			result = trades.add(trade);
			addToStocks(trade.getStock().getStockSymbol(), trade.getStock());
		} catch(Exception exception) {
			throw new Exception("Error while recording trade.", exception);
		}

		return result;//Result
	}

	@Override
	public Stock fetchStockBySymbol(String stockSymbol){
		Stock stock = null;
		try {
			if(stockSymbol != null) {
				stock = stocks.get(stockSymbol);
			}
		} catch(Exception exception) {
			LOGGER.error(
					"Error while fetching stock by symbol: " 
					+ stockSymbol
					+ ".", exception);
		}
		return stock;//Result
	}

	/**
	 * API to return the Trade Count
	 * @return
	 */
	public int getTradeCount() {
		return trades.size();
	}
}
