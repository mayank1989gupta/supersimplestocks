/**
 * 
 */
package com.supersimple.stocks.pojo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.supersimple.stocks.constants.Constants;
import com.supersimple.stocks.enums.StockType;

/**
 * @author Mayank
 *
 */
public class Stock {

	private BigDecimal ZERO_VALUE = new BigDecimal(Constants.ZERO);

	private String stockSymbol;
	private StockType stockType = StockType.COMMON;
	private BigDecimal lastDividend = new BigDecimal(Constants.ZERO);
	private BigDecimal fixedDividend = new BigDecimal(Constants.ZERO);
	private BigDecimal parValue = new BigDecimal(Constants.ZERO);
	private BigDecimal marketPrice = new BigDecimal(Constants.ZERO);

	/**
	 * @return the stockSymbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}
	/**
	 * @param stockSymbol the stockSymbol to set
	 */
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	/**
	 * @return the stockType
	 */
	public StockType getStockType() {
		return stockType;
	}
	/**
	 * @param stockType the stockType to set
	 */
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}
	/**
	 * @return the lastDividend
	 */
	public BigDecimal getLastDividend() {
		return lastDividend;
	}
	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}
	/**
	 * @return the fixedDividend
	 */
	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}
	/**
	 * @param fixedDividend the fixedDividend to set
	 */
	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	/**
	 * @return the parValue
	 */
	public BigDecimal getParValue() {
		return parValue;
	}
	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	/**
	 * @return the tickerPrice
	 */
	public BigDecimal getTickerPrice() {
		return marketPrice;
	}
	/**
	 * @param marketPrice the tickerPrice to set
	 */
	public void setTickerPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	} 

	/**
	 * API to get the Dividend Yield.</br>
	 * @return
	 */
	public BigDecimal getDividendYield() {
		BigDecimal dividendYield = new BigDecimal("-1.0");
		if(marketPrice.compareTo(ZERO_VALUE) > 0){
			if( stockType==StockType.COMMON){
				dividendYield = lastDividend.divide(marketPrice, 2, RoundingMode.HALF_UP);
			} else {
				// PREFERRED
				dividendYield = (fixedDividend.multiply(parValue)).divide(marketPrice, 2, RoundingMode.HALF_UP);
			}
		}
		return dividendYield;//result
	}


	/**
	 * API to get the PE Ratio
	 * 
	 * @return
	 */
	public BigDecimal getPeRatio() {
		BigDecimal peRatio = new BigDecimal("-1");

		if(marketPrice.compareTo(ZERO_VALUE)  > 0){
			peRatio = marketPrice.divide(getDividendYield(), 2, RoundingMode.HALF_UP);	
		}

		return peRatio;
	}
}
