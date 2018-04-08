/*
 * DisposableData.java
 *
 * Created on 12 dicembre 2004, 10.35
 */

package com.davidecavestro.common.cache;

import java.lang.ref.*;

/**
 * Componente dei dati in cache.
 * Questa componente offre la possibilita' di riduzione automatica della memoria allocata.
 * Questo avviene tramite l'utiizzo dei weak references, al prezzo di una riduzione delle prestazioni.
 * I dati possono quindi venire invalidati.
 * L'azione di deallocazione dei dati e' demandata interamente alla JVM, fermo restando
 * la possibilita' di accedere in modo sicuro alla componente, che diverra' non piu' valida.
 *
 * @see HardData
 * @author  davide
 */
public class DisposableData implements Data {
	
	private boolean _nullData;
	
	/**
	 * Il riferimento al dato.
	 */
	private Reference _ref;
	
	/**
	 *
	 * Costruttore con dati.
	 *
	 * @param data i dati.
	 */
	public DisposableData (final Object data) {
		this._ref = new WeakReference (data);
		this._nullData = data==null;
	}
	
	/**
	 * Ritorna i dati contentuti in questa componente.
	 *
	 * @return i dati contenuti in questa componente.
	 */
	public Object getData () {
		return this._ref.get ();
	}
	
	/**
	 * Ritorna <TT>true</TT> se questa componente contiene dati validi.
	 * I dati vengono invalidati dalla JVM, all'atto della deallocazione dei dati
	 * usati solamente da riferimenti deboli, quali quelli di questo tipo di componente.
	 *
	 * @return ritorna <TT>true</TT> se questa componente contiene dati validi.
	 */
	public boolean isValid () {
		return _nullData
		|| (this._ref.get ()!=null);
	}
	
	/**
	 * Ritorna una rappresentazione in formato stringa di questa componente dati.
	 *
	 * @return un stringa che rappresenta questi dati.
	 */
	public String toString (){
		final StringBuffer sb = new StringBuffer ();
		sb.append ("reference: "+this._ref);
		sb.append (" isValid: "+true);
		return sb.toString ();
	}
}
