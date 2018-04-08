/*
 * ResourceNames.java
 *
 * Created on 19 dicembre 2004, 20.47
 */

package com.davidecavestro.rbe.conf;

/**
 * I nomi delle risorse utilizzabili.
 *
 * @author  davide
 */
public interface ResourceNames {
	
	/**
	 * Nome directory impostazioni utente.
	 */
	public final static String USER_SETTINGSDIR_NAME = "etc";
	
	/**
	 * Nome directory dati utente.
	 */
	public final static String USER_DATADIR_NAME = "var";
	
	/**
	 * Nome file impostazioni utente.
	 */
	public final static String USER_SETTINGSFILE_NAME = "userprefs.properties";
	
	/**
	 * Nome file impostazioni di sistema.
	 */
	public final static String SYSTEM_SETTINGSFILE_NAME = "systemprefs.properties";
	
	/**
	 * Nome proprieta' di sistema contenente il percorso della home directory utente.
	 */
	public final static String USER_HOMEDIR_PATH = "user.home";
	
	/**
	 * Nome proprieta' di sistema contenente il percorso della directory di lavoro dell'utente utente.
	 */
	public final static String USER_WORKINGDIR_PATH = "user.dir";
	
	/**
	 * Nome account utente corrente.
	 */
	public final static String USER_ACCOUNT = "user.name";
	
}
