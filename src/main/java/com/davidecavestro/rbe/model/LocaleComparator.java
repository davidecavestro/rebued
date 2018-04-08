/*
 * LocaleComparator.java
 *
 * Created on 25 dicembre 2005, 11.27
 */

package com.davidecavestro.rbe.model;

import java.util.Comparator;
import java.util.Locale;

/**
 * Compara due Locale. Il locale di Default (<TT>LocalizationProperties.DEFAULT</TT>)  e'  il minore.
 *
 * @author  davide
 */
public class LocaleComparator implements Comparator {
	
	/** Costruttore. */
	public LocaleComparator () {
	}
	
	public int compare (Object o1, Object o2) {
		return compare ((Locale)o1, (Locale)o2);
	}
	
	public int compare (Locale l1, Locale l2) {
		if (l1==l2){
			return 0;
		}
		if (l1==LocalizationProperties.DEFAULT){
			return Integer.MIN_VALUE;
		}
		if (l2==LocalizationProperties.DEFAULT){
			return Integer.MAX_VALUE;
		}
		return l2.toString ().compareTo (l2.toString ());
	}
	
	
}
