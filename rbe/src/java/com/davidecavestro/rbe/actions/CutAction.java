/*
 * CutAction.java
 *
 * Created on 10 gennaio 2006, 20.30
 */

package com.davidecavestro.rbe.actions;

import com.davidecavestro.common.gui.ComponentSupport;
import java.awt.Component;
import javax.swing.AbstractAction;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

/**
 * Azione di CUT.
 *
 * @author  davide
 */
public class CutAction extends AbstractAction implements CaretListener {
	
	
	/**
	 * Costruttore.
	 */
	public CutAction () {

	}
	
	public void caretUpdate (javax.swing.event.CaretEvent e) {
		setEnabled (((JTextComponent)e.getSource ()).getSelectedText ()!=null);
	}
	
	public void actionPerformed (java.awt.event.ActionEvent e) {
		Component focusOwner = ComponentSupport.getWindow ((Component)e.getSource ()).getFocusOwner ();
		
		if (focusOwner instanceof JTextComponent){
			((JTextComponent)focusOwner).cut ();
		}
		
	}
	
}
