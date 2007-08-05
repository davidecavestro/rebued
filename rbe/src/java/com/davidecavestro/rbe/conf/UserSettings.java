/*
 * UserSettings.java
 *
 * Created on 18 aprile 2004, 12.08
 */

package com.davidecavestro.rbe.conf;

import com.davidecavestro.common.application.ApplicationData;
import com.davidecavestro.common.util.settings.SettingsSupport;
import com.davidecavestro.rbe.Application;
import java.awt.*;
import java.util.*;
import javax.swing.UIManager;

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
		final StringBuffer sb = new StringBuffer ();
		sb.append (
			this._userResources.getUserApplicationSettingsDirPath ())
			.append ("/").append (ResourceNames.USER_SETTINGSFILE_NAME);
		return sb.toString ();
	}

	public String getPropertiesHeader () {
		return PROPERTIES_HEADER;
	}
	
	

}
