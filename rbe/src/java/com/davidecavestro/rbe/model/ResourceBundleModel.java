/*
 * ResourceBundleModel.java
 *
 * Created on 1 dicembre 2005, 23.49
 */

package com.davidecavestro.rbe.model;

import java.util.Locale;
import java.util.Set;

/**
 * Rappresenta i dati contenuti in un <CODE>ResourceBundle</CODE>.
 *
 * @author  davide
 */
public interface ResourceBundleModel extends ResourceBundleModelNotifier {
	
	/**
	 * Ritorna i <CODE>Locale</CODE> nel modello.
	 *
	 * @return i <CODE>Locale</CODE> nel modello.
	 */	
	Locale[] getLocales ();
	
	/**
	 * Ritorna le chiavi del modello.
	 *
	 * @return le chiavi del modello.
	 */	
	Set getKeySet ();
	
	/**
	 * Ritorna il valore per la chiave ed il locale specificati.
	 *
	 * @return il valore per la chiave ed il locale specificati.
	 * @param locale il locale.
	 * @param key la chiave.
	 */	
	String getValue (Locale locale, String key );
	
	/**
	 * Imposta il valore per la chiave ed il locale specificati.
	 *
	 * @param value il valore.
	 * @param locale il locale.
	 * @param key la chiave.
	 */	
	void setValue (Locale locale, String key, String value );
	
	/**
	 * Ritorna il commento per la chiave ed il locale specificati.
	 *
	 * @return il commento per la chiave ed il locale specificati.
	 * @param locale il locale.
	 * @param key la chiave.
	 */	
	String getComment (Locale locale, String key );
	
	/**
	 * Imposta il commento per la chiave ed il locale specificati.
	 *
	 * @param comnet il commento.
	 * @param locale il locale.
	 * @param key la chiave.
	 */	
	void setComment (Locale locale, String key, String comment );
	
	/**
	 * Ritorna il nome del ResourceBundle.
	 *
	 * @return il nome del ResourceBundle.
	 */	
	String getName ();
	
	/**
	 * Imposta il nome del ResourceBundle.
	 */	
	void setName (String name);
	
//	/**
//	 * Rende persistente lo stato del modello.
//	 */
//	void store ();
	
}
