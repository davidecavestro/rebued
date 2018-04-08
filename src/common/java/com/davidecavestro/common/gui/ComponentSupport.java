/*
 * ComponentSupport.java
 *
 * Created on 10 gennaio 2006, 20.54
 */

package com.davidecavestro.common.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

/**
 * Supporto per l'accesso a proprieta' dei componenti visuali non esposte.
 *
 * @author  davide
 */
public class ComponentSupport {
	
	/** Costruttore. */
	private ComponentSupport () {
	}
	
	/**
	 * Ritorna la finestra di appartenenza di un componente.
	 *
	 * @param c il componente.
	 * @return la finestra cui il componente appartiene.
	 */	
	public static Window getWindow (Component c){
		if (c instanceof Window){
			return (Window)c;
		}
		Container parent = c.getParent ();
		if (null==parent){
			return null;
		}
		return getWindow (parent);
	}
}
