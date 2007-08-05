/*
 * HelpResourcesResolver.java
 *
 * Created on 27 dicembre 2004, 13.47
 */

package com.davidecavestro.common.help;

import com.davidecavestro.rbe.ApplicationContext;
import java.io.*;
import java.util.*;

/**
 * Risolve gli identificatori delle risorse di help con dei target validi.
 * Si appoggia ai file di configurazione.
 *
 * @author  davide
 */
public class HelpResourcesResolver {
	/**
	 * La configurazione.
	 */
	private Properties _properties;
	
	
	/** Costruttore.*/
	public HelpResourcesResolver (Properties p) {
		this._properties = p;
	}
	
	/**
	 * Risolve il valore della risorsa di help specificata.
	 * Cerca nel file di mappatura, altrimenti ritorna direttamente il valore della risorsa. 
	 *
	 * @param helpResource la risorsa.
	 * @return il valore della risorsa di help specificata..
	 */	
	public String resolveHelpID (HelpResource helpResource){
		final String resourceValue = helpResource.getValue ();
		/* cerca nel file di mappatura, altrimenti ritorna direttamente il valore della risorsa. */
		return this._properties.getProperty (resourceValue, resourceValue);
	}
	
}
