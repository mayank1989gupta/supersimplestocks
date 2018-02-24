/**
 * 
 */
package com.supersimple.stocks.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.supersimple.stocks.constants.Constants;
import com.supersimple.stocks.enums.TradeType;

/**
 * @author Mayank
 *
 */
public class Trade {

	private Date timeStamp;
	private Stock stock;
	private TradeType tradeIndicator = TradeType.BUY;
	private int sharesQuantity = 0;
	private BigDecimal price = new BigDecimal(Constants.ZERO);
	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	/**
	 * @return the tradeIndicator
	 */
	public TradeType getTradeIndicator() {
		return tradeIndicator;
	}
	/**
	 * @param tradeIndicator the tradeIndicator to set
	 */
	public void setTradeIndicator(TradeType tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}
	/**
	 * @return the sharesQuantity
	 */
	public int getSharesQuantity() {
		return sharesQuantity;
	}
	/**
	 * @param sharesQuantity the sharesQuantity to set
	 */
	public void setSharesQuantity(int sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Trade [timeStamp=" + timeStamp + ", stock=" + stock + ", tradeIndicator=" + tradeIndicator
				+ ", sharesQuantity=" + sharesQuantity + ", price=" + price + "]";
	}
}
//End of File