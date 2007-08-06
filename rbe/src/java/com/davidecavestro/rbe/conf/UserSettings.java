/*
 * UserSettings.java
 *
 * Created on 18 aprile 2004, 12.08
 */

package com.davidecavestro.rbe.conf;

import com.davidecavestro.rbe.Application;
import java.io.File;

/**
 * Le impostazioni personalizzate dell'utente.
 *
 * @author  davide
 */
public final class UserSettings extends AbstractSettings {
	
	/**
	 * header file impostazioni.
	 */
	public final static String PROPERTIES_HEADER = " *** USER SETTINGS *** ";


	private final UserResources  _userResources;
	
	/**
	 * Costruttore.
	 * @param application l'applicazione.
	 */
	public UserSettings (final Application application, final UserResources  userResources ) {
		super (application);
		this._userResources = userResources;
	}
	
	/**
	 * Ritorna il percorso del file di properties.
	 *
	 * @return il percorso del file di properties.
	 */	
	public String getPropertiesFileName () {
		return new File (_userResources.getUserApplicationSettingsDirPath (), ResourceNames.USER_SETTINGSFILE_NAME).getPath ();
	}

	public String getPropertiesHeader () {
		return PROPERTIES_HEADER;
	}
	
	

}
