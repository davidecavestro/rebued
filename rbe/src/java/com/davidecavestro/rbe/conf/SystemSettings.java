/*
 * SystemSettings.java
 *
 * Created on 18 aprile 2004, 12.08
 */

package com.davidecavestro.rbe.conf;

import com.davidecavestro.rbe.Application;
import java.awt.*;

/**
 * impostazioni configurabili di sistema.
 *
 * @author  davide
 */
public final class SystemSettings extends AbstractSettings {
	
	/**
	 * header file impostazioni.
	 */
	public final static String PROPERTIES_HEADER = "SYSTEM SETTINGS";

	private final SystemResources  _systemResources;
	
	/**
	 * Costruttore.
	 * @param application l'applicazione.
	 */
	public SystemSettings (final Application application, final SystemResources  systemResources ) {
		super (application);
		this._systemResources = systemResources;
	}	
	
	public String getPropertiesFileName () {
		final StringBuffer sb = new StringBuffer ();
		sb.append (this._systemResources.getSystemApplicationSettingsPath ()).append ("/").append (ResourceNames.SYSTEM_SETTINGSFILE_NAME);
		return sb.toString ();
	}

	public String getPropertiesHeader () {
		return PROPERTIES_HEADER;
	}
	
	public String[] getRecentPaths () {
		return null;
	}	

}
