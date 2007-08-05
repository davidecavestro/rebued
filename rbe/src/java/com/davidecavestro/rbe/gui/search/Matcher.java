/*
 * Matcher.java
 *
 * Created on 28 dicembre 2005, 23.07
 */

package com.davidecavestro.rbe.gui.search;

/**
 * INterfaccia per la ricerca di un pattern in una stringa.
 *
 * @author  davide
 */
public interface Matcher {
	/**
	 * Ritorna <TT>true</TT> se la ricerca del pattern nella stringa specificata produce un esito positivo
	 * 
	 * @param s la stringa su cui effettuare la ricerca del pattern.
	 * @return <TT>true</TT> se il pattern è contenuto nella stringa specificata.
	 */
	boolean match (String s);
	/**
	 * Imposta il pattern di ricerca.
	 * 
	 * @param p il pattern.
	 */
	void setPattern (String p);
	/**
	 * Ritorna il pattern di ricerca.
	 * 
	 * @return il patten di ricerca.
	 */
	String getPattern ();
	/**
	 * Imposta l'opzione di highlight dei match.
	 * 
	 * @param v il valore dell'opzione. 
	 */
	void setHighlight (boolean v);
	/**
	 * Ritorna il valore dell'opzione di highlight dei match.
	 * 
	 * @return il valore dell'opzione di highlight dei match.
	 */
	boolean getHighlight ();
	
	/**
	 * Imposta l'opzione di case-sensitivity.
	 * 
	 * @param v il valore dell'opzione. 
	 */
	void setCaseSensitive (boolean v);
	
	/**
	 * Ritorna il valore dell'opzione di case-sensitivity.
	 * 
	 * @return il valore dell'opzione di case-sensitivity.
	 */
	boolean getCaseSensitive ();
	
	/**
	 * Ritorna l'array contenente gli indici delle sottostringhe che soddisfano i criteri di ricerca.
	 * 
	 * @param s la stringa su cui effettuare la ricerca.
	 * @return  l'array contenente gli indici delle sottostringhe che soddisfano i criteri di ricerca.
	 */
	int[] getMatches (String s);
}
