/**
 * 
 */
package com.supersimple.stocks.utils;

import java.util.Calendar;

import org.apache.commons.collections4.Predicate;
import org.apache.log4j.Logger;

import com.supersimple.stocks.constants.Constants;
import com.supersimple.stocks.pojo.Trade;

/**
 * @author Mayank
 *
 */
@SuppressWarnings("rawtypes")
public class StockPredicate implements Predicate {
	
	private Logger logger = Logger.getLogger(StockPredicate.class);
	private String stockSymbol = Constants.EMPTY_STRING;
	private Calendar dateRange = null;

	/**
	 * 
	 * @param stockSymbol
	 * @param minutesRange
	 */
	public StockPredicate(String stockSymbol, int minutesRange){
		this.stockSymbol = stockSymbol;
		if( minutesRange > 0 ){
			dateRange = Calendar.getInstance();
			dateRange.add(Calendar.MINUTE, -minutesRange);
			logger.debug(String.format("Filter date pivot: %tF %tT", dateRange.getTime(), dateRange.getTime()));
		}

	}

	/**
	 * API to evaluate the Trade Range.
	 */
	public boolean evaluate(Object tradeObject) {
		Trade trade = (Trade) tradeObject;
		boolean include = trade.getStock().getStockSymbol().equals(stockSymbol);
		if(include && dateRange != null){
			include = dateRange.getTime().compareTo(trade.getTimeStamp())<=0;
		}
		return include;
	}
}
//End of File