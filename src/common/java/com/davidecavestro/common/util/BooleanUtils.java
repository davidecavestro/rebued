/*
 * BooleanUtils.java
 *
 * Created on 18 dicembre 2004, 11.45
 */

package com.davidecavestro.common.util;

/**
 * Utilita' per la manipolazione di valori booleani.
 *
 * @author  davide
 */
public final class BooleanUtils {
	
	/** 
	 * Costruttore vuoto.
	 */
	private BooleanUtils () {
	}
	
	/**
	 * Ritorna un booleano che incapsula il valore primitivo  specificato.
	 * Evita di creare innumerevoli istanze per una classe immutabile.
	 *
	 * @param b il valore primitivo.
	 * @return un booleano che incapsula il valore primitivo  specificato.
	 */	
	public static Boolean getBoolean (boolean b){
		return b?Boolean.TRUE:Boolean.FALSE;
	}
}
