/*
 * PersistenceStorage.java
 *
 * Created on 23 maggio 2005, 23.25
 */

package com.davidecavestro.common.gui.persistence;

import java.util.Properties;

/**
 * Il contenitore dei dati persistenti per i comoponenti di interfaccia utente.
 *
 * @author  davide
 */
public interface PersistenceStorage {
	/**
	 * Ritorna il registro contenente i dati persistenti.
	 *
	 * @return il registro contenente i dati persistenti.
	 */	
	Properties getRegistry ();
}
