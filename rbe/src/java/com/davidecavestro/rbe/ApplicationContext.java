/*
 * ApplicationContext.java
 *
 * Created on 21 dicembre 2005, 20.48
 */

package com.davidecavestro.rbe;

import com.davidecavestro.common.application.ApplicationData;
import com.davidecavestro.common.gui.persistence.UIPersister;
import com.davidecavestro.common.help.HelpManager;
import com.davidecavestro.common.log.Logger;
import com.davidecavestro.common.util.PropertiesExceptionHandler;
import com.davidecavestro.rbe.conf.ApplicationOptions;
import com.davidecavestro.rbe.conf.UserSettings;
import com.davidecavestro.rbe.gui.WindowManager;
import com.davidecavestro.rbe.actions.ActionManager;
import com.davidecavestro.rbe.conf.ApplicationEnvironment;
import com.davidecavestro.rbe.model.DefaultResourceBundleModel;
import com.davidecavestro.rbe.model.undo.RBUndoManager;
import java.beans.PropertyChangeListener;

/**
 *
 * @author  davide
 */
public class ApplicationContext {
	
	private final WindowManager _windowManager;
	private final ApplicationOptions _applicationOptions;
	private final UIPersister _uiPersister;
	private final Logger _logger;
	private final UserSettings _userSettings;
	private final ApplicationData _applicationData;
	private final ApplicationEnvironment _env;
	private final DefaultResourceBundleModel _resourceBundleModel;
	private final RBUndoManager _undoManager;
	private ActionManager _actionManager;
	private HelpManager _helpManager;
	private final PropertiesExceptionHandler _propsExceptionHandler;
	
    private java.beans.PropertyChangeSupport changeSupport;
	
	private boolean _isProcessing;
	
	/** 
	 * Costruttore.
	 */
	public ApplicationContext (
		ApplicationEnvironment env,
		ApplicationOptions applicationOptions,
		WindowManager windowManager,
		UIPersister uiPersister,
		Logger logger,
		UserSettings userSettings,
		ApplicationData applicationData,
		DefaultResourceBundleModel resourceBundleModel,
		RBUndoManager undoManager,
		ActionManager actionManager,
		HelpManager helpManager,
		PropertiesExceptionHandler propsExceptionHandler
		) {
			
		this._env = env;
		this._applicationOptions = applicationOptions;
		this._logger = logger;
		this._applicationData = applicationData;
		this._windowManager = windowManager;
		this._userSettings = userSettings;
		this._uiPersister = uiPersister;
		this._resourceBundleModel = resourceBundleModel;
		this._undoManager = undoManager;
		this._actionManager = actionManager;
		this._helpManager = helpManager;
		this._propsExceptionHandler = propsExceptionHandler;
	}
	
	public ApplicationEnvironment getApplicationEnvironment (){
		return this._env;
	}
	
	public ApplicationOptions getApplicationOptions (){
		return this._applicationOptions;
	}
	
	public WindowManager getWindowManager (){
		return this._windowManager;
	}
	
	public UIPersister getUIPersisteer (){
		return this._uiPersister;
	}
	
	public UserSettings getUserSettings (){
		return this._userSettings;
	}
	
	public DefaultResourceBundleModel getModel (){
		return this._resourceBundleModel;
	}
	
	public RBUndoManager getUndoManager (){
		return this._undoManager;
	}
	
	public ActionManager getActionManager (){
		return this._actionManager;
	}
	
	public HelpManager getHelpManager (){
		return this._helpManager;
	}
	
	public PropertiesExceptionHandler getPropertiesExceptionHandler (){
		return this._propsExceptionHandler;
	}	
	
	public ApplicationData getApplicationData (){
		return _applicationData;
	}
	
	public Logger getLogger (){
		return _logger;
	}
	
	public synchronized void addPropertyChangeListener (PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new java.beans.PropertyChangeSupport (this);
		}
		changeSupport.addPropertyChangeListener (listener);
	}
	
	public synchronized void removePropertyChangeListener (PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener (listener);
	}
	
	public synchronized PropertyChangeListener[] getPropertyChangeListeners () {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners ();
	}
	
	public synchronized void addPropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new java.beans.PropertyChangeSupport (this);
		}
		changeSupport.addPropertyChangeListener (propertyName, listener);
	}
	
	public synchronized void removePropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener (propertyName, listener);
	}
	
	public synchronized PropertyChangeListener[] getPropertyChangeListeners ( String propertyName) {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners (propertyName);
	}
	
	protected void firePropertyChange (String propertyName, Object oldValue, Object newValue) {
		java.beans.PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange (propertyName, oldValue, newValue);
	}
	
	protected void firePropertyChange (String propertyName, boolean oldValue, boolean newValue) {
		java.beans.PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange (propertyName, oldValue, newValue);
	}
	
	protected void firePropertyChange (String propertyName, int oldValue, int newValue) {
		java.beans.PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange (propertyName, oldValue, newValue);
	}
	
	public void setProcessing (final boolean processing) {
		final boolean oldValue = this._isProcessing;
		this._isProcessing = processing;
		if (oldValue!=processing) {
			firePropertyChange ("isProcessing", oldValue, processing);
		}
	}
	
	public boolean isProcessing (){
		return this._isProcessing;
	}
}
