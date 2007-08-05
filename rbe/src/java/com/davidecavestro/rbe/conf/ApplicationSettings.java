/*
 * ApplicationSettings.java
 *
 * Created on 4 dicembre 2004, 14.15
 */

package com.davidecavestro.rbe.conf;

import java.awt.*;

/**
 * Impostazioni configurazbili.
 *
 * @author  davide
 */
public interface ApplicationSettings {
	/**
	 * Ritorna il percorso della directory contenente i file di log.
	 *
	 * @return il percorso della directory contenente i file di log.
	 */
	public String getLogDirPath ();
	
	/**
	 * Ritorna la dimensione del buffer per il logger di testo semplice.
	 *
	 * @return la dimensione del buffer per il logger di testo semplice.
	 */
	public Integer getPlainTextLogBufferSize ();
	
	/**
	 * Ritorna il LookAndFeel.
	 *
	 * @return il LookAndFeel.
	 */	
	String getLookAndFeel ();
	
	/**
	 * Ritorna la lista dei percorsi degli ultimi bundle aperti.
	 *
	 * @return un aray contenente i percorsi degli ultimi bundle aperti.
	 */	
	String[] getRecentPaths ();
	
	/**
	 * Ritorna il percorso dell'ultima directory utilizzata.
	 * @return il percorso dell'ultima directory utilizzata.
	 */
	String getLastPath ();
	
	/**
	 * Ritorna l'impostazione di creazione copie di backup.
	 *
	 * @return l'impostazione di creazione copie di backup.
	 */	
	Boolean getBackupOnSave ();
	
	/**
	 * Ritorna l'impostazione di abilitazione editazione chiavi.
	 *
	 * @return l'impostazione di abilitazione editazione chiavi.
	 */	
	Boolean getKeyEditing ();
	
	/**
	 * Ritorna l'impostazione di scarto dei locale con codifiche non valide.
	 *
	 * @return l'impostazione di scarto dei locale con codifiche non valide.
	 */	
	Boolean getDiscardMalformedEncoding ();
}
