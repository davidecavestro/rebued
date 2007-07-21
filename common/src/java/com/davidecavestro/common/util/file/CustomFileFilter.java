/*
 * CustomFIleFilter.java
 *
 * Created on 15 marzo 2005, 21.10
 */

package com.davidecavestro.common.util.file;

import java.io.File;

/**
 * Filtro personalizzato per la scelta dei file.
 * @author  davide
 */
public class CustomFileFilter extends  javax.swing.filechooser.FileFilter {
	/**
	 * Le estensioni accettate.
	 */
	private String[] _acceptedExtensions;
	/**
	 * Descrizioni delle estensioni.
	 */
	private String[] _descriptions;
	
	/**
	 * Costruttore con estensioni accettatee relative descrizioni.
	 *
	 * @param acceptedExtensions le estensioni accettate.
	 * @param descriptions le descrizioni.
	 */
	public CustomFileFilter (final String[] acceptedExtensions, final String[] descriptions){
		this._acceptedExtensions = acceptedExtensions;
		this._descriptions = descriptions;
	}
	
	/**
	 * Accetta tutte le directory e le estensioni specificate.
	 */
	public boolean accept (File f) {
		if (f.isDirectory ()) {
			return true;
		}
		
		final String extension = FileUtils.getExtension (f, false);
		if (extension != null) {
			for (int i=0;i<_acceptedExtensions.length;i++){
				final String testingExtension = _acceptedExtensions[i];
				if (extension.equalsIgnoreCase (testingExtension)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	//The description of this filter
	public String getDescription () {
		final StringBuffer sb = new StringBuffer ();
		for (int i=0;i<this._descriptions.length;i++){
			if (i>0){
				sb.append (" / ");
			}
			sb.append (this._descriptions[i]);
		}
		return sb.toString ();
	}
}