/*
 * ApplicationEnvironment.java
 *
 * Created on 12 dicembre 2004, 10.09
 */

package com.davidecavestro.rbe.conf;

/**
 * Ambiente di lancio dell'applicazione.
 * Espone sostanzialmente i parametri di lancio che possono influenzare il 
 * funzionamento dell'applicazione.
 *
 * @author  davide
 */
public interface ApplicationEnvironment {
	
	/**
	 * Ritorna il percorso di installazione dell'applicazione.
	 *
	 * @return il percorso di installazione dell'applicazione.
	 */	
	public String getApplicationDirPath ();
	
	/**
	 * Ritorna il percorso dei file di configurazione dell'applicazione.
	 *
	 * @return il percorso dei file di configurazione dell'applicazione.
	 */	
	public String getApplicationSettingsPath ();
}
