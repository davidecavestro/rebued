/*
 * ArrayUtils.java
 *
 * Created on December 30, 2006, 10:22 AM
 *
 */

package com.davidecavestro.common.util;

import java.io.PrintStream;

/**
 * Contiene metodistatic di utilit&agrave; per la manipolazione degli array.
 *
 * @author Davide Cavestro
 */
public class ArrayUtils {
	
	/**
	 * Costruttore privato.
	 */
	private ArrayUtils () {
	}
	
	/**
	 * Stampa l'array.
	 *
	 * @param a l'array da stampare.
	 * @param ps lo stream di stampa.
	 */	
	public static <T> void printArray (PrintStream ps, T[] a){
		for (int i = 0;i<a.length;i++){
			ps.print (a[i]);
			if (i<a.length-1) {
				ps.print (", ");
			}
		}
	}
	
	/**
	 * Stampa una riga per ogni elemento dell'array.
	 *
	 * @param a l'array da stampare.
	 * @param ps lo stream di stampa.
	 */	
	public static <T> void printlnArray (PrintStream ps, T[] a){
		for (int i = 0;i<a.length;i++){
			ps.println (a[i]);
		}
	}
	
}
