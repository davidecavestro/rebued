/*
 * UIPersister.java
 *
 * Created on 16 maggio 2005, 22.41
 */

package com.davidecavestro.common.gui.persistence;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

/**
 * Gestore della persistenza per l'interfaccia utente (posizione form, dimensioni controlli, etc).
 *
 * @author  davide
 */
public class UIPersister {
	
	private final List _register = new ArrayList ();
	
	private final PersistenceStorage _uiStorage;
		
	/** Costruttore. */
	public UIPersister (final PersistenceStorage persistenceStorage) {
		this._uiStorage = persistenceStorage;
	}
	
	/**
	 * Registra il componente specificato per la persistenza e lo inizializza.
	 *
	 * @param component il componente.
	 * @return <TT>true</TT> se il <TT>component</TT> e' stato inizializzato con i dati persistenti.
	 */	
	public boolean register (final PersistentComponent component) {
		return this.register (component, true);
	}
	/**
	 * Registra il componente specificato per la persistenza.
	 *
	 * @param component il componente.
	 * @param init <TT>true</TT> per inizializzare il componente con i dati precedentemente salvati.
	 * @return <TT>true</TT> se il <TT>component</TT> e' stato inizializzato con i dati persistenti.
	 */	
	public boolean register (final PersistentComponent component, final boolean init) {
		if (!this._register.contains (component)){
			this._register.add (component);
		}
		if (init){
			return component.restorePersistent (this._uiStorage);
		}
		return false;
	}
	
	/**
	 * Rimuove il componente specificato dal registro.
	 *
	 * @param component il componente da rimuovere dal registro.
	 */	
	public void unregister (final PersistentComponent component){
		this._register.remove (component);
	}
	
	/**
	 * Salva le impostazioni di tuttii componenti registrati.
	 */
	public void makePersistentAll (){
		for (final Iterator it = this._register.iterator ();it.hasNext ();){
			((PersistentComponent)it.next ()).makePersistent (this._uiStorage);
		}
	}
	
}
