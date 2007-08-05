/*
 * LocalizationProperties.java
 *
 * Created on 3 dicembre 2005, 15.00
 */

package com.davidecavestro.rbe.model;

import com.davidecavestro.common.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Risorse di localizzazione.
 *
 * @author  davide
 */
public class LocalizationProperties {
	
	public final static Locale DEFAULT = new Locale ("");
	
	private final Locale locale;
	private final CommentedProperties properties;
	
	/**
	 * Costruttore.
	 * @param locale il locale.
	 * @param props le proeprties;
	 */
	public LocalizationProperties (Locale locale, CommentedProperties props) {
		this.locale = locale;
		this.properties = props;
	}
	
	/**
	 * Ritorna il locale.
	 *
	 * @return il locale.
	 */	
	public Locale getLocale (){
		return this.locale;
	}
	
	/**
	 * Ritorna la mappa delle risorse.
	 *
	 * @return la amppa delle risorse.
	 */	
	public CommentedProperties getProperties (){
		return this.properties;
	}
	
	/**
	 * Salva il contenuto di queste risorse nel file specificato.
	 *
	 * @param file il file di salvataggio.
	 * @param header commento di testa. Puo' essere <TT>null</TT>.
	 * @throws FileNotFoundException nel caso di problemi di accesso al file.
	 * @throws IOException nel caso qualcosa vada male durante il salvataggio.
	 */	
	public void store (File file, String header) throws FileNotFoundException, IOException{
		this.properties.store (new FileOutputStream (file), header);
	}
	
	
	/**
	 * Ritorna <TT>true</TT> se queste LocalizationProperties sono le predefinite.
	 *
	 * @return <TT>true</TT> se queste LocalizationProperties sono le predefinite.
	 */	
	public boolean isDefault (){
		return this.locale.equals (DEFAULT);
	}
	
	/**
	 * Ritorna una rappresentazione in formato stringa di queste LocalizationProperties.
	 *
	 * @return una stringa che rappresenta queste LocalizationProperties.
	 */	
	public String toString (){
		final StringBuffer sb = new StringBuffer ();
		sb.append ("locale: ").append (this.locale);
		sb.append (" proeprties: ").append (this.properties);
		return sb.toString ();
	}
}
