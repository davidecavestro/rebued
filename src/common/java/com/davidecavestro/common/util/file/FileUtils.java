/*
 * FileUtils.java
 *
 * Created on 12 dicembre 2004, 11.44
 */

package com.davidecavestro.common.util.file;

import java.io.*;

/**
 * Utilita' per la gestione dei file.
 *
 * @author  davide
 */
public class FileUtils {
	
	/**
	 * Estensione file immagine.
	 */
    public final static String jpeg = "jpeg";
	/**
	 * Estensione file immagine.
	 */
    public final static String jpg = "jpg";
	/**
	 * Estensione file immagine.
	 */
    public final static String gif = "gif";
	/**
	 * Estensione file immagine.
	 */
    public final static String tiff = "tiff";
	/**
	 * Estensione file immagine.
	 */
    public final static String tif = "tif";
	/**
	 * Estensione file immagine.
	 */
    public final static String png = "png";
	/**
	 * Estensione PDF.
	 */
    public final static String pdf = "pdf";
	/**
	 * Estensione file CSV.
	 */
    public final static String csv = "csv";
	/**
	 * Estensione file XLS.
	 */
    public final static String xls = "xls";
	/**
	 * Estensione file HTML.
	 */
    public final static String html = "html";
	/**
	 * Estensione file htm.
	 */
    public final static String htm = "htm";
	/**
	 * Estensione file XML.
	 */
    public final static String xml = "xml";
	
	/**
	 * Estensione file proeprties.
	 */
    public final static String properties = "properties";
	
	/** Costruttore vuoto e privato (evita istanziazione).*/
	private FileUtils () {
	}
	
	/**
	 * Garantisce l'esistenza del percorso che porta al file specificato.
	 *
	 * @param file il file.
	 */	
	public static void makeFilePath (File file){
		if (file.exists ()){
			/*
			 * File gia' esistente.
			 */
			return;
		} else {
			final File parent = file.getParentFile ();
			if (parent!=null && !parent.exists ()){
				/*
				 * Ha una directory padre, non esistente.
				 * Crea il percorso.
				 */
				parent.mkdirs ();
			}
		}
	}
	

    /*
     * Ritorna l'estensione del file specificato, forzandola in caratteri minuscoli se specificato.
     */  
    public static String getExtension(final File f, final boolean toLowerCase) {
        String ext = null;
        final String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
			if (toLowerCase){
				ext = s.substring(i+1).toLowerCase();
			} else {
				ext = s.substring(i+1);
			}
        }
        return ext;
    }	
}
