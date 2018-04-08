/*
 * Enumerator.java
 *
 * Created on August 11, 2007, 9:09 PM
 *
 */

package com.davidecavestro.common.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Implementazione di <TT>Enumeration</TT> basata su <TT>Iterator</TT>.
 *
 * @author Davide Cavestro
 */
public class IteratorEnumerationAdapter<T> implements Enumeration<T>{
	
	private final Iterator<T> _it;
	/**
	 * Costruttore.
	 */
	public IteratorEnumerationAdapter (final Iterator<T> it) {
		_it = it;
	}

	public boolean hasMoreElements () {
		return _it.hasNext ();
	}

	public T nextElement () {
		return _it.next ();
	}
	
}
