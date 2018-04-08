/*
 * Data.java
 *
 * Created on 12 dicembre 2004, 10.21
 */

package com.davidecavestro.common.cache;

/**
 * Componente che contiene i dati in cache.
 *
 * @author  davide
 */
public interface Data {
	/**
	 * Ritorna <TT>true</TT> se questa entry e' ancora valida.
	 * Un'entry potrebbe essere invalidata da processi di riduzione della 
	 * memoria allocata, qualora la politica di gestione dell'applicazione
	 * lo consenta.
	 *
	 * @return <TT>true</TT> se questa entry e' ancora valida.
	 */	
	public boolean isValid ();
	/**
	 * Ritorna il dato memorizzato nella cache tramite questa entry.
	 *
	 * @return il dato memorizzato nella cache tramite questa entry.
	 */	
	public Object getData ();
}
