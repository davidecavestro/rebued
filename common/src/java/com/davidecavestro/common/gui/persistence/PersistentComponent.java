/*
 * PersistentComponent.java
 *
 * Created on 16 maggio 2005, 22.48
 */

package com.davidecavestro.common.gui.persistence;

/**
 * Componente con attributi persistenti.
 *
 * @author  davide
 */
public interface PersistentComponent {
//	/**
//	 * Ritorna il rettangolo che descrive la dimensione e la posizione di questo componente.
//	 *
//	 * @return il rettangolo che descrive la dimensione e la posizione di questo componente.
//	 */	
//    Rectangle getBounds ();
//	
//	/**
//	 *
//	 * Impostra la posizione e le dimensioni di questo componente.
//	 *
//	 * @param r il rettangolo che descrive la dimensione e la posizione di questo componente.
//	 */	
//    void setBounds (Rectangle r);
	/**
	 * Ritorna la chiave usata per la persistenza degli attributi di questo componente.
	 *
	 * @return la chiave usata per la persistenza degli attributi di questo componente.
	 */	
	String getPersistenceKey ();
	
	/**
	 * Rende persistente lo statodi questo componente.
	 * @param props lo storage delle impostazioni persistenti.
	 */
	void makePersistent (PersistenceStorage props);
	
	/**
	 * Ripristina lo stato persistente, qualora esista.
	 * @param props lo storage delle impostazioni persistenti.
	 * @return <TT>true</TT> se sono stati ripristinati i dati persistenti.
	 */
	boolean restorePersistent (PersistenceStorage props);
}
