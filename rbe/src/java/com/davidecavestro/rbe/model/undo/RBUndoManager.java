/*
 * RBUndoManager.java
 *
 * Created on 24 dicembre 2005, 11.29
 */

package com.davidecavestro.rbe.model.undo;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * UndoManager per il Resource Bundle Model.
 *
 * @author  davide
 */
public class RBUndoManager extends UndoManager {
	
	protected Action undoAction;
	protected Action redoAction;
	
	/** Costruttore */
	public RBUndoManager () {
		this.undoAction = new JvUndoAction (this);
		this.undoAction.putValue (Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke (java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));

		this.redoAction = new JvRedoAction (this);
		this.redoAction.putValue (Action.ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke (java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
		
		synchronizeActions ();           // to set initial names
	}
	
	
	public Action getUndoAction () {
		return undoAction;
	}
	
	
	public Action getRedoAction () {
		return redoAction;
	}
	
	
	public boolean addEdit (UndoableEdit anEdit) {
		try {
			return super.addEdit (anEdit);
		}
		finally {
			synchronizeActions ();
		}
	}
	
	
	protected void undoTo (UndoableEdit edit) throws CannotUndoException {
		try {
			super.undoTo (edit);
		} finally {
			synchronizeActions ();
		}
	}
	
	
	protected void redoTo (UndoableEdit edit) throws CannotRedoException {
		try {
			super.redoTo (edit);
		} finally {
			synchronizeActions ();
		}
	}
	
	
	protected void synchronizeActions () {
		undoAction.setEnabled (canUndo ());
		undoAction.putValue (Action.NAME, getUndoPresentationName ());
		
		redoAction.setEnabled (canRedo ());
		redoAction.putValue (Action.NAME, getRedoPresentationName ());
	}
	
	
	class JvUndoAction extends AbstractAction {
		protected final UndoManager manager;
		
		
		public JvUndoAction (UndoManager manager) {
			this.manager = manager;
		}
		
		
		public void actionPerformed (ActionEvent e) {
			try {
				manager.undo ();
			}
			catch (CannotUndoException ex) {
				ex.printStackTrace ();
			}
		}
	}
	
	
	class JvRedoAction extends AbstractAction {
		protected final UndoManager manager;
		
		
		public JvRedoAction (UndoManager manager) {
			this.manager = manager;
		}
		
		
		public void actionPerformed (ActionEvent e) {
			try {
				manager.redo ();
			}
			catch (CannotRedoException ex) {
				ex.printStackTrace ();
			}
		}
		
	}
	
}
