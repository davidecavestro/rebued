/*
 * ResourceBundleModelEvent.java
 *
 * Created on 2 dicembre 2005, 0.08
 */

package com.davidecavestro.rbe.model.event;

import com.davidecavestro.rbe.model.ResourceBundleModel;
import java.util.Locale;

/**
 * ResourceBundleModelEvent  e'  usato per notificare i listener che un modello di 
 * ResourceBundle  e'  cambiato.
 *
 * @author  davide
 */
public class ResourceBundleModelEvent extends java.util.EventObject {
	
	/**
	 * Identifica l'inserimento di locale o di entry.
	 */
	public final static int INSERT = 1;
	/**
	 * Identifica la modifica di locale o di entry.
	 */
	public final static int UPDATE = 0;
	/**
	 * Identifica la rimozione di locale o di entry.
	 */
	public final static int DELETE = -1;
	
	/**
	 * Specifica tutti i locale.
	 */
	public final static Locale ALL_LOCALES = new Locale ("ALL_LOCALES");
	
	/**
	 * Specifica tutte le chiavi.
	 */
	public final static String[] ALL_KEYS = null;
	
	protected int type;
	
	protected Locale locale;
	protected String[] keys;

	/** 
	 * Tutte le entry di tutti i locale sono cambiate.
	 */
	public ResourceBundleModelEvent (ResourceBundleModel source) {
		this (source, ALL_LOCALES, ALL_KEYS, UPDATE);
	}
	
	/**
	 *
	 * Le entry specificate sono cambiate per il locale.
	 * Il tipo deve valere: INSERT, UPDATE o DELETE.
	 * @param source il modello sorgente.
	 * @param locale il locale. Il valore ALL_LOCALES indica tutti i locale.
	 * @param keys le chiavi. Il valore ALL_KEYS indica tutte le chiavi.
	 * @param type il tipo di cambiamento.
	 */
	public ResourceBundleModelEvent (ResourceBundleModel source, Locale locale, String[] keys, int type) {
		super (source);
		this.locale = locale;
		this.keys=keys;
		this.type=type;
	}
	
	/**
	 * Ritorna il tipo di evento.  Puo' valere: INSERT, UPDATE o DELETE.
	 *
	 * @return il tipo di evento.
	 */	
	public int getType (){
		return this.type;
	}
	
	/**
	 * Ritorna il Locale interessato da questo evento.
	 *
	 * @return il Locale interessato da questo evento.
	 */	
	public Locale getLocale (){
		return this.locale;
	}
	
	/**
	 * Ritorna le chiavi interessate da questo evento.
	 *
	 * @return le chiavi interessate da questo evento.
	 */	
	public String[] getKeys (){
		return this.keys;
	}
}
