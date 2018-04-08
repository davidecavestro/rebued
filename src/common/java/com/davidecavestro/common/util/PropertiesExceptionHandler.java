/*
 * PropertiesExceptionHandler.java
 *
 * Created on April 5, 2006, 12:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davidecavestro.common.util;

/**
 * Interfaccia per la gestione personalizzata delle eccezioni di manipolazione
 * delle Properties.
 *
 * @author davide
 */
public interface PropertiesExceptionHandler {
	/**
	 * Codifica non ammessa.
	 * 
	 * 
	 * @param s la stringa non correttamente codificata.
	 * @throws java.lang.IllegalArgumentException in caso di eccezione non gestita.
	 */
	void malformedEncoding (String s) throws IllegalArgumentException;
}
