/*
 * DataImpl.java
 *
 * Created on 12 dicembre 2004, 10.35
 */

package com.davidecavestro.common.cache;

/**
 * Componente dei dati in cache. Questa componente non offre la possibilita' di riduzione automatica
 * della memoria allocata. Naturalmente le sue prestazioni non soffrono del degrado dovuto 
 * all'introduzione dei references.
 * Inoltre i dati non possono essere invalidati.
 *
 * @see DisposableData
 * @author  davide
 */
public class HardData implements Data {
	
	/**
	 * Il riferimento al dato.
	 */
	private Object _data;
	
	/**
	 *
	 * Costruttore con dati.
	 *
	 * @param data i dati.
	 */
	public HardData (final Object data) {
		this._data = data;
	}
	
	/**
	 * Ritorna i dati contentuti in questa componente.
	 *
	 * @return i dati contenuti in questa componente.
	 */	
	public Object getData () {
		return this._data;
	}
	
	/**
	 * Ritorna <TT>true</TT> se questa componente contiene dati validi.
	 * Questa implementazione ritorna sempre <TT>true</TT>.
	 *
	 * @return ritorna <TT>true</TT> se questa componente contiene dati validi.
	 */	
	public boolean isValid () {
		return true;
	}
	
	/**
	 * Ritorna una rappresentazione in formato stringa di questa componente dati.
	 *
	 * @return un stringa che rappresenta questi dati.
	 */	
	public String toString (){
		final StringBuffer sb = new StringBuffer ();
		sb.append ("data: "+this._data);
		sb.append (" isValid: "+true);
		return sb.toString ();
	}
}
