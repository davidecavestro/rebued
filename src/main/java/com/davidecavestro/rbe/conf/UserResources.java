/*
 * UserResources.java
 *
 * Created on 19 dicembre 2004, 20.57
 */

package com.davidecavestro.rbe.conf;

import com.davidecavestro.common.application.ApplicationData;
import java.io.File;

/**
 * Risorse applicative legate all'utente.
 *
 * @author  davide
 */
public class UserResources {
	
	private final ApplicationData _applicationData;
	
	/** Costruttore. */
	public UserResources (final ApplicationData applicationData) {
		this._applicationData = applicationData;
	}
	
	/**
	 * Ritorna il percorso della HOME directory dell'utente.
	 *
	 * @return il percorso della HOME directory dell'utente.
	 */	
	public static String getUserHomeDirPath (){
		return System.getProperty (ResourceNames.USER_HOMEDIR_PATH);
	}
	
	/**
	 * Ritorna il nome dell'acount dell'utente.
	 *
	 * @return il nome dell'acount dell'utente.
	 */	
	public static String getUserAccount (){
		return System.getProperty (ResourceNames.USER_ACCOUNT);
	}
	
	/**
	 * Ritorna il percorso della directory privata dell'utente di supporto all'applicazione .
	 *
	 * @return il percorso della directory privata dell'utente di supporto all'applicazione .
	 */	
	public String getUserApplicationDirPath (){
		return new File (getUserHomeDirPath (), getUserApplicationRepositoryDirName ()).getPath ();
	}
	
	/**
	 * Ritorna il percorso della directory privata dell'utente contenente la configurazione dell'applicazione .
	 *
	 * @return il percorso della directory privata dell'utente contenente la configurazione dell'applicazione .
	 */	
	public String getUserApplicationSettingsDirPath (){
		return new File (getUserApplicationDirPath (), ResourceNames.USER_SETTINGSDIR_NAME).getPath ();
	}
	
	/**
	 * Ritorna il percorso della directory privata dell'utente contenente i dati dell'applicazione .
	 *
	 * @return il percorso della directory privata dell'utente contenente i dati dell'applicazione .
	 */	
	public String getUserApplicationDataDirPath (){
		return new File (getUserApplicationDirPath (), ResourceNames.USER_DATADIR_NAME).getPath ();
	}
	
	/**
	 * NRitorna il nome directory di supporto all'applicazione condivisa tra gli utenti.
	 * @erturns il nome directory di supporto all'applicazione condivisa tra gli utenti.
	 */
	private String geSystemApplicationRepositoryDirName () {
		return this._applicationData.getApplicationInternalName ();
	}
	
	/**
	 * Ritorna il nome directory utente di supporto all'applicazione.
	 * @returns il nome directory utente di supporto all'applicazione.
	 */
	private String getUserApplicationRepositoryDirName () {
		return  "."+this._applicationData.getApplicationInternalName ();
	}
	

}
