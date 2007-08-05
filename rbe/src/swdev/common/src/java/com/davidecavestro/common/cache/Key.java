/*
 * Key.java
 *
 * Created on 12 dicembre 2004, 10.21
 */

package com.davidecavestro.common.cache;

/**
 * Componente che contiene la chiave per l'accesso ai dati in cache.
 *
 * @author  davide
 */
public interface Key {
	/**
	 * Ritorna la chiave.
	 *
	 * @return la chiave.
	 */	
	public Object getKey ();
}
