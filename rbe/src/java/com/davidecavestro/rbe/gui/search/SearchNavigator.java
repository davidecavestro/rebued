/*
 * SearchNavigator.java
 *
 * Created on 1 gennaio 2006, 9.58
 */

package com.davidecavestro.rbe.gui.search;

/**
 * Naviga nei risultati di una ricerca.
 *
 * @author  davide
 */
public interface SearchNavigator {
	/**
	 * Passa al risultato successivo.
	 */
	void nextMatch (Matcher m);
	/**
	 * Torna al risultato precedente.
	 */
	void previousMatch (Matcher m);
}
