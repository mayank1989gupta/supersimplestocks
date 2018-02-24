/**
 * 
 */
package com.supersimple.stocks.utils;

import java.util.Date;
import java.util.Calendar;

import org.springframework.stereotype.Component;

/**
 * @author Mayank
 *
 */
@Component("superSimpleStocksUtils")
public class SuperSimpleStocksUtils {

	/**
	 * API to get now moved minutes.</br>
	 * 
	 * @param minutes
	 * @return
	 */
	public Date getNowMovedMinutes(int minutes){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		return now.getTime();
	}
}
