/*
 * ApplicationData.java
 *
 * Created on 19 dicembre 2004, 19.32
 */

package com.davidecavestro.common.application;

import com.davidecavestro.common.cache.DisposableData;
import com.davidecavestro.common.util.settings.SettingsSupport;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * Contiene i dati descrittivi dell'applicazione quali, ad esempio,il numero di versione...
 *
 * @author  davide
 * @todo completare ampliando le informazioni disponibili.
 */
public final class ApplicationData {

	/**
	 * Nome della proprieta' contenente il nome dell'applicazione per uso interno.
	 */
	public final static String PROPNAME_INTERNALAPPLICATIONNAME = "internal.name";

	/**
	 * Nome della proprieta' contenente il nome dell'applicazione per uso esterno.
	 */
	public final static String PROPNAME_EXTERNALAPPLICATIONNAME = "external.name";

	/**
	 * Nome della proprieta' contenente il numero di versione piu' significativo.
	 */
	public final static String PROPNAME_MAJORVERSIONNUMBER = "major.version.number";

	/**
	 * Nome della proprieta' contenente il numero di versione meno significativo.
	 */
	public final static String PROPNAME_MINORVERSIONNUMBER = "minor.version.number";

	/**
	 * Nome della proprieta' contenente il suffisso di versione.
	 */
	public final static String PROPNAME_VERSIONSUFFIX = "version.suffix";

	/**
	 * Nome della proprieta' contenente il numero progressivo di build dell'applicazione.
	 */
	public final static String PROPNAME_BUILDNUMBER = "build.number";

	/**
	 * Nome della proprieta' contenente le annotazioni di di build dell'applicazione.
	 */
	public final static String PROPNAME_BUILDNOTES = "build.notes";

	/**
	 * Nome della proprieta' contenente la data di rilascio dell'applicazione.
	 */
	public final static String PROPNAME_RELEASE_DATE = "release.date";

	/**
	 * Nome della proprieta' contenente la data di preparazione dell'applicazione.
	 */
	public final static String PROPNAME_BUILD_DATE = "build.date";

	/**
	 * L'istanza del singleton.
	 */
//	private static ApplicationData _instance;
	
	/**
	 * Proprieta' di configurazione di questo descrittore.
	 */
	private final Properties _releaseProperties;
	
	
	/** Costruttore. */
	public ApplicationData (Properties p) {
		this._releaseProperties = p;
	}
	
//	public static ApplicationData getInstance (){
//		if (_instance==null){
//			_instance = new ApplicationData ();
//		}
//		return _instance;
//	}
	
	/**
	 * Ritorna il nome interno dell'applicazione. Questo nome e' inteso ad uso 
	 * dei processi di identificazione e configurazione dell'applicazione.
	 *
	 * @return il nome interno dell'applicazione.
	 */
	public String getApplicationInternalName (){
		return SettingsSupport.getStringProperty (_releaseProperties, PROPNAME_INTERNALAPPLICATIONNAME);
	}
	
	/**
	 * Ritorna il nome esterno dell'applicazione. Questo nome e' inteso ad uso 
	 * dei processi di presentazione dell'applicazione.
	 *
	 * @return il nome esterno dell'applicazione.
	 */
	public String getApplicationExternalName (){
		return SettingsSupport.getStringProperty (_releaseProperties, PROPNAME_EXTERNALAPPLICATIONNAME);
	}
	

	/**
	 * Il numero di versione (cache).
	 */
	private DisposableData _versionNumber;
	/**
	 * Ritorna il numero di versione di questo rilascio.
	 *
	 * @return il numero di versione di questo rilascio.
	 */	
	public final String getVersionNumber (){
		if (_versionNumber==null || !_versionNumber.isValid ()){
			final StringBuffer sb = new StringBuffer ();
			sb.append (_releaseProperties.getProperty (PROPNAME_MAJORVERSIONNUMBER));
			sb.append (".");
			sb.append (_releaseProperties.getProperty (PROPNAME_MINORVERSIONNUMBER));
			
			final String versionSuffix = _releaseProperties.getProperty (PROPNAME_VERSIONSUFFIX);
			if (versionSuffix!=null){
				sb.append (versionSuffix);
			}
			_versionNumber = new DisposableData (sb.toString ());
		}
		return (String)_versionNumber.getData ();
	}
	
	/**
	 * Il numero di build (cache).
	 */
	private DisposableData _buildNumber;
	/**
	 * Ritorna il numero di build di questo rilascio.
	 *
	 * @return il numero di build di questo rilascio.
	 */	
	public final String getBuildNumber (){
		if (_buildNumber==null || !_buildNumber.isValid ()){
			_buildNumber = new DisposableData (_releaseProperties.getProperty (PROPNAME_BUILDNUMBER));
		}
		return (String)_buildNumber.getData ();
	}
	
	/**
	 * Le note di build (cache).
	 */
	private DisposableData _buildNotes;
	/**
	 * Ritorna il numero di build di questo rilascio.
	 *
	 * @return il numero di build di questo rilascio.
	 */	
	public final String getBuildNotes (){
		if (_buildNotes==null || !_buildNotes.isValid ()){
			_buildNotes = new DisposableData (_releaseProperties.getProperty (PROPNAME_BUILDNOTES));
		}
		return (String)_buildNotes.getData ();
	}
	
	/**
	 * La data di rilascio (cache).
	 */
	private DisposableData _releaseDate;
	/**
	 * Ritorna la data di rilascio.
	 *
	 * @return la data di rilascio.
	 */	
	public final Calendar getReleaseDate (){
		if (_releaseDate==null || !_releaseDate.isValid ()){
			_releaseDate = new DisposableData (SettingsSupport.getCalendarProperty (_releaseProperties, PROPNAME_RELEASE_DATE));
		}
		return (Calendar)_releaseDate.getData ();
	}
	
	/**
	 * La data di preparazione (cache).
	 */
	private DisposableData _buildDate;
	/**
	 * Ritorna la data di preparazione.
	 *
	 * @return la data di preparazione.
	 */	
	public final Calendar getBuildDate (){
		if (_buildDate==null || !_buildDate.isValid ()){
			_buildDate = new DisposableData (SettingsSupport.getCalendarProperty (_releaseProperties, PROPNAME_BUILD_DATE));
		}
		return (Calendar)_buildDate.getData ();
	}
	
}
