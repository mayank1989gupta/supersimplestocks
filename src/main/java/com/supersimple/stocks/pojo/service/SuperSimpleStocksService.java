/**
 * 
 */
package com.supersimple.stocks.pojo.service;

import java.math.BigDecimal;

import com.supersimple.stocks.pojo.Trade;

/**
 * @author Mayank
 *
 */
public interface SuperSimpleStocksService {

	/**
	 * Record a trade in the Super Simple Stocks application.</br>
	 * 
	 * @param trade
	 * @return
	 * @throws Exception
	 */
	public boolean recordTrade(Trade trade) throws Exception;
	
	/**
	 * Calculates the dividend yield for a given stock.</br>
	 * 
	 * @param stockSymbol
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculateDividendYield(String stockSymbol) throws Exception;
	
	/**
	 * Calculates the P/E Ratio for a given stock.</br>
	 * 
	 * @param stockSymbol
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculatePERatio(String stockSymbol) throws Exception;
	
	/**
	 * API to calculate Stock Price.</br>
	 * 
	 * @param stockSymbol
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculateStockPrice(String stockSymbol) throws Exception;
	
	/**
	 * Calculates the GBCE All Share Index using the geometric mean of prices for all stocks.
	 * For the technical test purpose we have assumed the next:
	 * 
	 * 1. Because the geometric mean is undefined when some price is zero, we will consider only 
	 *    the stock's prices greater than zero.
	 * 2. If all prices are zero then the value for the GBCE All Share Index is zero.
	 * 
	 * @return
	 * @throws Exception
	 */
	public BigDecimal calculateGBCEAllShareIndex() throws Exception;
}
