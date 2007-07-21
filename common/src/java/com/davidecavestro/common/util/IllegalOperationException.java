/*
 * IllegalOperationException.java
 *
 * Created on 20 aprile 2005, 23.58
 */

package com.davidecavestro.common.util;

/**
 * Eccezione dovuta al tentativo di eseguire un'operazione illegale.
 *
 * @author  davide
 */
public class IllegalOperationException extends java.lang.RuntimeException {
	
	/**
	 * Creates a new instance of <code>IllegalOperationException</code> without detail message.
	 */
	public IllegalOperationException () {
	}
	
	
	/**
	 * Constructs an instance of <code>IllegalOperationException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public IllegalOperationException (String msg) {
		super (msg);
	}
}
