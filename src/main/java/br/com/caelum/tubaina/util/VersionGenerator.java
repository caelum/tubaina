package br.com.caelum.tubaina.util;

import java.util.Calendar;

public class VersionGenerator {
	
	public String generate() {
		Calendar cal = Calendar.getInstance();
		//Version is calculated using the date: M + (12 * YY)
		int version = ((cal.get(Calendar.YEAR) % 100)*12) + cal.get(Calendar.MONTH);
		return "" + version / 10 + "." + version % 10 + "." + cal.get(Calendar.DAY_OF_MONTH);
	}

}
