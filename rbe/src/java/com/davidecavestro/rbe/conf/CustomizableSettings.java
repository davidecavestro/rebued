/*
 * CustomizableSettings.java
 *
 * Created on 11 dicembre 2004, 15.02
 */

package com.davidecavestro.rbe.conf;

import java.util.*;

/**
 * Impostazione applicative personalizzabili.
 *
 * @author  davide
 */
public interface CustomizableSettings extends ApplicationSettings{
	
	
	/** Dimensione del buffer per il log di testo semplice. */
	public final static String PLAINTEXTLOG_BUFFERSIZE = "palintextlogbuffersize";
	
	/**
	 * Percorso della directory contenente i file di log.
	 */
	public final static String PROPNAME_LOGDIRPATH = "logdirpath";
	
	/**
	 * Il LookAndFeel.
	 */
	public final static String PROPNAME_LOOKANDFEEL = "lookandfeel";
	
	/**
	 * I percorsi degli ultimi file aperti.
	 */
	public final static String PROPNAME_RECENTPATHS = "recentpaths";
	
	/**
	 * Il percorso dell'ultima directory utilizzata.
	 */
	public final static String PROPNAME_LASTPATH = "lastpath";
	
	/**
	 * Il parametro di abilitazione del salvataggiocopie di backup.
	 */
	public final static String PROPNAME_BACKUPONSAVE = "backuponsave";
	
	/**
	 * Il parametro di abilitazione dell'editazione chiavi.
	 */
	public final static String PROPNAME_KEYEDITING = "keyediting";
	
	/**
	 * Il parametro di scarto locale con codifica non valida.
	 */
	public final static String PROPNAME_DISCARDMALFORMEDENCODING = "discardmalformedencoding";
	
	
	
	
	/**
	 * Ritorna il nome del file di preferenze associato a queste impostazioni.
	 * @return il nome del file di preferenze associato a queste impostazioni.
	 */	
	public String getPropertiesFileName ();
	
	/**
	 * Carica e ritorna le properties a partire dal nome del file associato a queste impostazioni.
	 *
	 * @throws RuntimeException in caso di errori nellapertura del file di risorse.
	 * @return le properties caricate.
	 */	
	public Properties loadProperties () throws RuntimeException;
	
	/**
	 * Salva le properties a partire dal nome del file associato a queste impostazioni.
	 *
	 * @throws RuntimeException in caso di errori nell'apertura del file di risorse.
	 */	
	public void storeProperties () throws RuntimeException;
	
	/**
	 * Ritorna lo header del file di properties di queste impostazioni.
	 * @return lo header del file di properties di queste impostazioni.
	 */	
	public String getPropertiesHeader ();
	
	/**
	 * Ritorna il file contenente le impostazioni applicative persistenti.
	 *
	 * @return il file contenente le impostazioni applicative persistenti.
	 */	
	public Properties getProperties ();
	
	
	/**
	 * Impostala dimensione del buffer per il logger di testo semplice.
	 */
	public void setPlainTextLogBufferSize (final Integer size);
	
	/**
	 * Imposta il LookAndFeel.
	 */
	void setLookAndFeel (final String lookAndFeel);
	
	/**
	 * Imposta il percorso dell'ultima directory usata.
	 */
	void setLastPath (String value);
	
	/**
	 * Imposta i percorsi dei file recenti.
	 */
	void setRecentPaths (String[] paths);
	
	/**
	 * Abilita/disabilita il salvataggio dei file di backup.
	 */
	void setBackupOnSave (Boolean b);
	
	/**
	 * Abilita/disabilita l'editazione delle chiavi.
	 */
	void setKeyEditing (Boolean b);
	
	/**
	 * Abilita/disabilita la rimozione dei locale con codifica non valida..
	 */
	void setDiscardMalformedEncoding (Boolean b);
	
	
}
