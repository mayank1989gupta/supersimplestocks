/**
 * 
 */
package com.supersimple.stocks.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.supersimple.stocks.pojo.Stock;
import com.supersimple.stocks.pojo.Trade;

/**
 * @author Mayank
 *
 */
public interface SuperSimpleStockManager {

	/**
	 * Record in the Stock Entity Manager a trade represented by the object <i>trade</i>.
	 * 
	 * @param trade
	 * @return
	 * @throws Exception
	 */
	public boolean recordTrade(Trade trade) throws Exception;
	
	/**
	 * Gets the array list that contains all the trades.</br>
	 * 
	 * @return
	 */
	public ArrayList<Trade> fetchTrades();
	
	/**
	 * API to fetch the stocks bases on the symbol.</br>
	 * 
	 * @param stockSymbol
	 * @return
	 */
	public Stock fetchStockBySymbol(String stockSymbol);
	
	/**
	 * Gets all the stocks supported by Super Simple Stocks application.
	 * 
	 * @return
	 */
	public HashMap<String, Stock> fetchStocks();
}
