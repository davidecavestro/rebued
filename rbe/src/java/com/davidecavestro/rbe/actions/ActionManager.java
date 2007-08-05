/*
 * ActionManager.java
 *
 * Created on 31 dicembre 2005, 11.11
 */

package com.davidecavestro.rbe.actions;

import com.davidecavestro.rbe.ApplicationContext;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

/**
 * Gestore delle Action.
 *
 * @author  davide
 */
public class ActionManager {
	
//	private final ApplicationContext _context;
	
	/** Costruttore. */
	public ActionManager () {
//		this._context = context;
	}
	
	private FindNextAction _findNextAction;
	public FindNextAction getFindNextAction (){
		if (null==this._findNextAction){
			this._findNextAction = new FindNextAction ();
		}
		return this._findNextAction;
	}
	
//	private class FocusManager implements CaretListener {
//		
//		public FocusManager () {
//			init ();
//		}
//		
//		public void caretUpdate (javax.swing.event.CaretEvent e) {
//			
//		}
//		
//		private void init (){Toolkit.getDefaultToolkit ().
//			final KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager ();
//			focusManager.addPropertyChangeListener (
//				new PropertyChangeListener () {
//					public void propertyChange (PropertyChangeEvent e) {
//						String prop = e.getPropertyName ();
//						if ("focusOwner".equals (prop)) {
//
//							final Object oldValue = e.getOldValue ();
//							if (oldValue != null &&
//								oldValue instanceof JTextComponent) {
//
//								((JTextComponent)oldValue).removeCaretListener (FocusManager.this);
//							}
//
//							final Object newValue = e.getNewValue ();
//							if (newValue != null &&
//								newValue instanceof JTextComponent) {
//
//								((JTextComponent)newValue).addCaretListener (FocusManager.this);
//							}
//						}
//					}
//				}
//			);
//		}
//	}
}
