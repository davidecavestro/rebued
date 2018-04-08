/*
 * DefaultSettings.java
 *
 * Created on 13 settembre 2004, 22.50
 */

package com.davidecavestro.rbe.conf;

import java.awt.*;
import javax.swing.*;

/**
 * Impostazioni di predefinite.
 *
 * @author  davide
 */
public final class DefaultSettings implements ApplicationSettings {
	
	/**
	 * Header file impostazioni.
	 */
	public final static String PROPERTIES_HEADER = "DEFAULT SETTINGS";

	/**
	 * Dimensione predefinita per il buffer del log di testo semplice
	 */
	public final static Integer DEFAULT_PLAINTEXTLOG_BUFFERSIZE = new Integer (8192);
	
	private static DefaultSettings _instance;
	
	private final ApplicationEnvironment _env;
	
	/** Costruttore. */
	public DefaultSettings (final ApplicationEnvironment environment){
		this._env = environment;
	}
	
	/**
	 * Ritorna il percorso della directory contenente i file di log.
	 * Il valore di ritorno risulta composto nel seguente modo:
	 * <CODE>Application.getEnvironment ().getApplicationDirPath ()+"/logs/";</CODE>
	 *
	 * @return il percorso della directory contenente i file di log.
	 */
	public String getLogDirPath () {
		final StringBuffer sb = new StringBuffer ();
		final String applicationDirPath = this._env.getApplicationDirPath ();
		if (applicationDirPath!=null){
			sb.append (applicationDirPath);
		} else {
			sb.append (System.getProperty (ResourceNames.USER_WORKINGDIR_PATH));
		}
		sb.append ("/logs");
		return sb.toString ();
	}
	
	/**
	 * Ritorna il lookAndFeel predefinito (JGoodies Plastic3D).
	 * @return il lookAndFeel.
	 */
	public String getLookAndFeel () {
		return "com.jgoodies.plaf.plastic.Plastic3DLookAndFeel";
//		return UIManager.getSystemLookAndFeelClassName ();
	}
	
	/**
	 * Ritorna la dimensione del buffer per il logger di testo semplice.
	 *
	 * @return la dimensione del buffer per il logger di testo semplice.
	 */
	public Integer getPlainTextLogBufferSize (){
		return DEFAULT_PLAINTEXTLOG_BUFFERSIZE;
	}
	
	public String[] getRecentPaths () {
		return new String[0];
	}
	
	public String getLastPath () {
		return null;
	}
	
	/**
	 * Ritorna sempre Boolean.TRUE.
	 */	
	public Boolean getBackupOnSave () {
		return Boolean.valueOf (backupOnSave ());
	}
	
	/**
	 * Ritorna il valore di default per l'impostazione di salvataggio copie di backup.
	 * Ritorna sempre <TT>true</TT>.
	 */	
	public static boolean backupOnSave () {
		return true;
	}
	
	/**
	 * Ritorna sempre Boolean.FALSE.
	 */	
	public Boolean getKeyEditing () {
		return Boolean.valueOf (keyEditing ());
	}
	
	/**
	 * Ritorna il valore di default per l'impostazione di editazione chiavi.
	 * Ritorna sempre <TT>false</TT>.
	 */	
	public static boolean keyEditing () {
		return false;
	}
	
	/**
	 * Ritorna il valore di default per l'impostazione di scarto dei locale con codifiche non valide.
	 * Ritorna sempre <TT>false</TT>.
	 *
	 * @return il valore di default per l'impostazione di scarto dei locale con codifiche non valide.
	 */	
	public Boolean getDiscardMalformedEncoding (){
		return Boolean.valueOf (discardMalformedEncoding ());
	}
	
	/**
	 * Ritorna il valore di default per l'impostazione di scarto dei locale con codifiche non valide.
	 * Ritorna sempre <TT>false</TT>.
	 *
	 */	
	public static boolean discardMalformedEncoding (){
		return false;
	}
	
}
