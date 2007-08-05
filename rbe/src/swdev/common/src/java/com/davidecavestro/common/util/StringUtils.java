/*
 * StringUtils.java
 *
 * Created on 6 maggio 2004, 0.03
 */

package com.davidecavestro.common.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Utilita' per la manipolazione di stringhe.
 *
 * @author  davide
 */
public final class StringUtils {
	
	/** 
	 * Costruttore vuoto.
	 */
	private StringUtils() {
	}
	
	/**
	 * Verifica se un array di stringhe contiene la stringa specificata.
	 *
	 * @param container il contenitore.
	 * @param pattern la stringa da cercare.
	 * @return <code<true</code>se <code>container</code> contiene <code>pattern</code>;
	 * <code>false</code> altrimenti.
	 */	
	public final static boolean contains (Object[] container, Object pattern){
		boolean nullContainer = container==null;
		boolean nullPattern = pattern==null;
		if (nullContainer && nullPattern){
			return true;
		}
		for (int i=0;i<container.length;i++){
			final Object s = container[i];
			if (s!=null){
				if (s.equals(pattern)){
					return true;
				}
			} else {
				//elemento nullo 
				if (nullPattern){
					return true;
				}
			}
		}
		return false;
	}
	
	
	private final static String[] voidStringArray = new String[0];
	/**
	 * Converte una stringa in un array di stringhe. I caratteri di "a capo", ovvero \n
	 *vengono usati come token per la generazione delle stringhe dell'array.
	 *
	 * @param s la stringa.
	 * @return un array di stringhe.
	 */	
	public static String[] toStringArray (final String s){
		final List l = new ArrayList ();
		int from = 0;
		final String delimiter = "\\n";
		final int delimiterLength = delimiter.length ();
		final int length = s.length ();
		while (from <= length) {
			int to = s.indexOf (delimiter, from);
			if (to != -1){
				l.add (s.substring (from, to));
				from = to + delimiterLength;
			} else {
				l.add (s.substring (from, length));
				break;
			}
		}
		
//		String s1 = new String (new char[] {'\n'});
//		for (final StringTokenizer st = new StringTokenizer (s, "\\n", false);st.hasMoreTokens ();){
//			l.add (st.nextToken ());
//		}
		return (String[])l.toArray (voidStringArray);
	}
	
	/**
	 * Stampa l'array di Stringhe.
	 *
	 * @param s le stringhe da stampare.
	 * @param ps lo stream di stampa.
	 */	
	public static void printStringArray (String[] s, PrintStream ps){
		for (int i = 0;i<s.length;i++){
			ps.println (s[i]);
		}
	}
}
