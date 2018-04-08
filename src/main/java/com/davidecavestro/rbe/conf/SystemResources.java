/*
 * SystemResources.java
 *
 * Created on 19 dicembre 2004, 21.08
 */

package com.davidecavestro.rbe.conf;

import com.davidecavestro.common.application.ApplicationData;
import java.io.File;

/**
 * Risorse applicative condivise tra gli utenti.
 *
 * @author  davide
 */
public class SystemResources {
	
	private final ApplicationData _applicationData;

	private final ApplicationEnvironment _env;
	
	/** Costruttore. */
	public SystemResources (final ApplicationData applicationData, final ApplicationEnvironment env) {
		this._applicationData = applicationData;
		this._env = env;
	}

	/**
	 * Ritorna il percorso della directory contenente la configurazione dell'applicazione.
	 *
	 * @return il percorso della directory contenente la configurazione dell'applicazione.
	 */	
	public String getSystemApplicationSettingsPath (){
		/**
		 * Prova a cercare nell'ambiente.
		 */
		final String envArg = this._env.getApplicationSettingsPath ();
		if (envArg!=null){
			return envArg;
		}
		/**
		 * Prende directory corrente.
		 */
		return System.getProperty ("user.dir");
	}	
	/**
	 * Ritorna il percorso della directory privata dell'utente di supporto all'applicazione .
	 *
	 * @return il percorso della directory privata dell'utente di supporto all'applicazione .
	 */	
	public String getSystemApplicationDirPath (){
		return new File (_env.getApplicationDirPath (), getUserApplicationRepositoryDirName ()).getPath ();
	}
	
	/**
	 * Ritorna il percorso della directory privata dell'utente contenente la configurazione dell'applicazione .
	 *
	 * @return il percorso della directory privata dell'utente contenente la configurazione dell'applicazione .
	 */	
	public String getUserApplicationSettingsDirPath (){
		return new File (_env.getApplicationDirPath (), ResourceNames.USER_SETTINGSDIR_NAME).getPath ();
	}
	
	/**
	 * Ritorna il percorso della directory privata dell'utente contenente i dati dell'applicazione .
	 *
	 * @return il percorso della directory privata dell'utente contenente i dati dell'applicazione .
	 */	
	public String getUserApplicationDataDirPath (){
		return new File (_env.getApplicationDirPath (), ResourceNames.USER_DATADIR_NAME).getPath ();
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
