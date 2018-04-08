/*
 * DefaultResourceBundleModel.java
 *
 * Created on 2 dicembre 2005, 20.49
 */

package com.davidecavestro.rbe.model;

import com.davidecavestro.common.util.CommentedProperties;
import com.davidecavestro.common.util.PropertiesExceptionHandler;
import com.davidecavestro.rbe.conf.ApplicationOptions;
import com.davidecavestro.rbe.model.event.ResourceBundleModelEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

/**
 * Il modello di ResourceBundle.
 *
 * @author  davide
 */
public class DefaultResourceBundleModel extends AbstractResourceBundleModel {
	
	/**
	 * Mappa Locale->Properties
	 */
	private final Map<Locale, LocalizationProperties> _resourceMap = new HashMap<Locale, LocalizationProperties> ();
	
	/*
	 * I campi di cache sono previsti a scopo di incremento delle performance.
	 * Essi devono essere mantenuti allineati ad ogni modifica.
	 */
	/**
	 * Cache: array di Locale
	 */
	private Locale[] _locales;
	/**
	 * Cache: array di Properties
	 */
	private LocalizationProperties[] _resources;
	/**
	 * Cache: set di chiavi (unione delle chiavi per tutti i locale)
	 */
	private final Set<String> _keys = new HashSet<String> ();
	/**
	 * Cache: Set di Locale
	 */
	private final Set<Locale> _localesSet = new HashSet<Locale> ();
	
	private String _name;
	
    /**
     * If any <code>PropertyChangeListeners</code> have been registered,
     * the <code>changeSupport</code> field describes them.
     *
     * @serial
     * @since 1.2
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     * @see #firePropertyChange
     */
    private java.beans.PropertyChangeSupport changeSupport;
	
	private final ApplicationOptions _applicationOptions;
	private final PropertiesExceptionHandler _peh;
	
	private final static LocalizationProperties[] voidResourceArray = new LocalizationProperties[0];
	private final static Locale[] voidLocaleArray = new Locale[0];
	private final static String[] voidStringArray = new String[0];
	
	/**
	 * Costruttore.
	 * @param applicationOptions le opzioni di configurazione.
	 * @param name il nome.
	 * @param resources le risorse di localizzazione.
	 */
	public DefaultResourceBundleModel (final ApplicationOptions applicationOptions, final PropertiesExceptionHandler peh, final String name, final LocalizationProperties[] resources) {
		_applicationOptions = applicationOptions;
		_peh = peh;
		setName (name);
		setBundles (resources);
		
	}

	public void setName (final String name){
		if (this._name!=name){
			final String old = this._name;
			this._name = name;
			firePropertyChange ("name", old, name);
		}
	}
	
	public void setBundles (LocalizationProperties[] resources){
		this._resources = resources;
		cacheResources (resources);
//		setModified (false);
		fireResourceBundleStructureChanged ();
	}
	
	public LocalizationProperties[] getResources (){
		return this._resources;
		//return (LocalizationProperties[])this._resourceMap.values ().toArray (voidResourceArray);
	}
	
	public java.util.Set<String> getKeySet () {
		return this._keys;
	}
	
	private Locale[] _lastClone;
	private Locale[] _lastSource;
	public java.util.Locale[] getLocales () {
		if (null==_lastClone || _lastSource!=this._locales){
			this._lastClone = new Locale[_locales.length];
			System.arraycopy (_locales, 0, _lastClone, 0, _locales.length);
			this._lastSource=this._locales; 
		}
		
		return this._lastClone;
	}

	private void cacheResources (final LocalizationProperties[] resources){
		mapResources (resources);
		cacheLocales (resources);
		cacheKeys (resources);
	}
	
	private void mapResources (final LocalizationProperties[] resources){
		final Map<Locale, LocalizationProperties> map = _resourceMap;
		map.clear ();
		for (int i = 0; i < resources.length;i++){
			final LocalizationProperties properties = resources[i];
			map.put (properties.getLocale (), properties);
		}
		
	}
	
	private void cacheLocales (final LocalizationProperties[] resources) {
		final Locale[] locales  = new Locale [resources.length];
		for (int i = 0 ; i <resources.length;i++){
			final LocalizationProperties properties = resources[i];
			locales[i] = properties.getLocale ();
		}
		
		this._locales = locales;
		this._localesSet.clear ();
		this._localesSet.addAll (Arrays.asList (this._locales));
		
	}
	
	private void cacheKeys (final LocalizationProperties[] resources) {
		this._keys.clear ();
		for (int i = 0 ; i <resources.length;i++){
			final LocalizationProperties properties = resources[i];
			this._keys.addAll (properties.getProperties ().keySet ());
		}
	}
	
	private LocalizationProperties getLocalizationProperties (Locale locale){
		return _resourceMap.get (locale);
	}
	
	public String getValue (Locale locale, String key) {
		return getLocalizationProperties (locale).getProperties ().getProperty (key);
	}
	
	public String getComment (Locale locale, String key) {
		return getLocalizationProperties (locale).getProperties ().getComment (key);
	}
	
	public void setValue (Locale locale, String key, String value) {
		setValue (locale, key, value, true, false);
	}
	
	public void setValue (final Locale locale, final String key, final String value, final boolean undoable, final boolean undoing) {
		{
			/* 
			 * ottimizzazione 
			 * evita modifica invariante
			 */
			final String oldValue = getLocalizationProperties (locale).getProperties ().getProperty (key);
			if (oldValue == value || (oldValue != null && oldValue.equals (value))){
				return;
			}
		}
		final UndoableEditListener[] listeners = (UndoableEditListener[])getListeners (UndoableEditListener.class);

		
		if (!undoable || listeners == null) {
			internalSetValue (locale, key, value, undoing);
			return;
		}


		final String oldValue = getValue (locale, key);
		internalSetValue (locale, key, value, undoing);
		
		/*
		 * gestione UNDO
		 */
		if (undoable){
			ValueEdit valueEdit = new ValueEdit (this, oldValue, value, locale, key);
			UndoableEditEvent editEvent = new UndoableEditEvent (this, valueEdit);
			for (int i=0; i< listeners.length;i++){
				listeners[i].undoableEditHappened (editEvent);
			}
		}
	}

	private void internalSetValue (final Locale locale, final String key, final String value, final boolean undoing) {
		CommentedProperties props = getLocalizationProperties (locale).getProperties ();
		if (null==value){
			props.remove (key);
			/* e' possibile che la chiave vada rimossa */
			cacheKeys (this._resources);
			fireResourceBundleModelChanged (
				new ResourceBundleModelEvent (
					this, getLocales (key).isEmpty ()?ResourceBundleModelEvent.ALL_LOCALES:locale, 
					new String[]{key}, 
					ResourceBundleModelEvent.DELETE
				)
			);
		} else {
			String oldValue = props.getProperty (key);
			if (oldValue == value || (oldValue!=null && oldValue.equals (value))){
				return;
			}
			props.setProperty (key, value);
			/* potrebbe essere una nuova chiave */
			this._keys.add (key);
			if (null==oldValue){
				fireResourceBundleModelChanged (new ResourceBundleModelEvent (this, locale, new String[]{key}, ResourceBundleModelEvent.INSERT));
			} else {
				fireResourceBundleValueUpdated (locale, key);
			}
		}
		guardModified (undoing);
	}

	
		
	public void setComment (final Locale locale, final String key, final String comment) {
		setComment (locale, key, comment, true, false);
	}
	
	public void setComment (final Locale locale, final String key, final String comment, final boolean undoable, final boolean undoing) {
		final UndoableEditListener[] listeners = (UndoableEditListener[])getListeners (UndoableEditListener.class);
		if (undoable == false || listeners == null) {
			internalSetComment (locale, key, comment, undoing);
			return;
		}


		final String oldComment = getComment (locale, key);
		internalSetComment (locale, key, comment, undoing);
		
		/*
		 * gestione UNDO
		 */
		if (undoable){
			final CommentEdit commentEdit = new CommentEdit (this, oldComment, comment, locale, key);
			UndoableEditEvent editEvent = new UndoableEditEvent (this, commentEdit);
			for (int i=0; i< listeners.length;i++){
				listeners[i].undoableEditHappened (editEvent);
			}
		}
	}
	
	private void internalSetComment (final Locale locale, final String key, final String comment, final boolean undoing) {
		CommentedProperties props = getLocalizationProperties (locale).getProperties ();
		
		String oldComment = props.getComment (key);
		if (oldComment == comment || (oldComment!=null && oldComment.equals (comment))){
			return;
		}
		props.setComment (key, comment);
		
		fireResourceBundleModelChanged (new ResourceBundleModelEvent (this, locale, new String[]{key}, ResourceBundleModelEvent.UPDATE));
		guardModified (undoing);
	}
	
	public void changeKey (final String oldKey, final String newKey){
		changeKey (oldKey, newKey, true, false);
	}
	
	public void changeKey (final String oldKey, final String newKey, final boolean undoable, final boolean undoing){
		changeKey (oldKey, newKey, undoable, true, undoing);
	}
	
	public void changeKey (final String oldKey, final String newKey, final boolean undoable, final boolean fireEvents, final boolean undoing){
		if (oldKey == newKey || (oldKey!=null && oldKey.equals (newKey))){
			return;
		}
		
		final UndoableEditListener[] listeners = (UndoableEditListener[])getListeners (UndoableEditListener.class);
		
		final List<Locale> locales = new ArrayList<Locale> (this.getLocales (oldKey));
		final List<String> values = new ArrayList<String> ();
		final List<String> comments = new ArrayList<String> ();
		for (final Iterator<Locale> it = locales.iterator (); it.hasNext ();){
			final Locale l = it.next ();
			final String value = this.getValue (l, oldKey);
			values.add (value);
			final String comment = this.getComment (l, oldKey);
			comments.add (comment);
			this.addKey (l, newKey, value, comment, false, false, undoing);
		}
		
		this.removeKey (oldKey, false, false, undoing);
		
		/*
		 * gestione UNDO
		 */
		if (undoable && listeners!=null){
			final KeyChange undoableChange = new KeyChange (this, oldKey, newKey);
			final UndoableEditEvent editEvent = new UndoableEditEvent (this, undoableChange);
			for (int i=0; i< listeners.length;i++){
				listeners[i].undoableEditHappened (editEvent);
			}
		}

		if (fireEvents){
			fireKeysDeleted (new String[]{oldKey});
			fireKeysInserted (new String[]{newKey});
		}
	}
	
	public void removeKey (String key){
		removeKey (key, true);
	}
	
	public void removeKey (String key, boolean undoable){
		removeKey (key, undoable, true, false);
	}
	
	public void removeKey (String key, boolean undoable, boolean fireRemovalEvent, boolean undoing){
		final UndoableEditListener[] listeners = (UndoableEditListener[])getListeners (UndoableEditListener.class);
		
		final String[] values = undoable?new String [_locales.length]:null;
		for (int i = 0;i<this._locales.length;i++){
			final Locale locale = this._locales[i];
			if (undoable){
				values[i] = getValue (locale, key);
			}
			getLocalizationProperties (locale).getProperties ().remove (key);
		}
		this._keys.remove (key);
		if (fireRemovalEvent){
			fireKeysDeleted (new String[]{key});
		}
		
		if (undoable){
			KeyRemoval keyRemoval = new KeyRemoval (this, key, values);

			UndoableEditEvent editEvent = new UndoableEditEvent (this, keyRemoval);
			for (int i=0; i< listeners.length;i++){
				listeners[i].undoableEditHappened (editEvent);
			}
		}
		guardModified (undoing);		
	}
	
	/**
	 * Inserisce una entry per il Locale di default (LocalizationProeprties.DEFAULT).
	 *
	 * @param key la chiave.
	 * @param value il valore.
	 */	
	public void addKey (String key, String value, String comment ){
		addKey (LocalizationProperties.DEFAULT, key, value, comment);
	}
	
	public void addKey (Locale locale, String key, String value, String comment ){
		addKey (locale, key, value, comment, true, true, false);
	}
	
	public void addKey (Locale locale, String key, String value, String comment, boolean undoable, boolean fireInsertionEvent, boolean undoing){
		if (value==null){
			return;
		}
		
		if (this.getLocaleKeys (locale).contains (key)){
			throw new IllegalArgumentException ("Duplicate key");
		}

		/*
		 * gestione UNDO
		 */
		if (undoable){
			final UndoableEditListener[] listeners = (UndoableEditListener[])getListeners (UndoableEditListener.class);

			final KeyAddition valueEdit = new KeyAddition (this, value, comment, locale, key);
			UndoableEditEvent editEvent = new UndoableEditEvent (this, valueEdit);
			for (int i=0; i< listeners.length;i++){
				listeners[i].undoableEditHappened (editEvent);
			}
		}
		
		
		getLocalizationProperties (locale).getProperties ().setProperty (key, value, comment);
		this._keys.add (key);
		
		if (fireInsertionEvent){
			fireResourceBundleModelChanged (new ResourceBundleModelEvent (this, locale, new String[]{key}, ResourceBundleModelEvent.INSERT));
		}
		
		guardModified (undoing);
	}
	
	public void addKey (String key, String[] values){
		addKey (key, values, true, false);
	}
	
	public void addKey (String key, String[] values, boolean undoable, boolean undoing){
		for (int i = 0;i<values.length;i++){
			final Locale locale = this._locales[i];
			addKey (locale, key, values[i], null, undoable, false, undoing);
		}
		fireKeysInserted (new String[]{key});
	}
	
	/**
	 * Aggiunge un Locale con le properties associate.
	 * <BR>
	 * L'azioneviene posta nella coda di uno.
	 *
	 * @param resource le properties di locale.
	 *@see #setBundles
	 */	
	public void addLocale (LocalizationProperties resource){
		addLocale (resource, true, false);
	}
	
	/**
	 * Aggiunge un Locale con le properties associate.
	 *
	 * @see #setBundles
	 * @param undoable <TT>true</TT> per memorizzare l'azione nella coda di undo.
	 * @param resource le properties di locale.
	 */	
	public void addLocale (LocalizationProperties resource, boolean undoable, boolean undoing){
		if (containsLocale (resource.getLocale ())){
			throw new IllegalArgumentException ("Duplicate locale");
		}

		/*
		 * gestione UNDO
		 */
		if (undoable){
			final UndoableEditListener[] listeners = (UndoableEditListener[])getListeners (UndoableEditListener.class);

			final LocaleAddition undoableAction = new LocaleAddition (this, resource);
			final UndoableEditEvent editEvent = new UndoableEditEvent (this, undoableAction);
			for (int i=0; i< listeners.length;i++){
				listeners[i].undoableEditHappened (editEvent);
			}
		}
		
		
		final LocalizationProperties[] backup = this._resources;
		final int oldLength = backup.length;
		final int newLength = oldLength+1;
		final LocalizationProperties[] newResources = new LocalizationProperties [newLength];
		System.arraycopy (backup, 0, newResources, 0, oldLength);
		newResources [newLength-1] = resource;
		setBundles (newResources);
		guardModified (undoing);
	}
	
	/**
	 * Rimuove un Locale con le properties associate.
	 * <BR>
	 * L'azioneviene posta nella coda di uno.
	 *
	 * @param undoable <TT>true</TT> per memorizzare l'azione nella coda di undo.
	 * @param resource le properties di locale.
	 *@see #setBundles
	 */	
	public void removeLocale (Locale locale){
		removeLocale (locale, true, false);
	}
	
	/**
	 * Rimuove un Locale con le properties associate.
	 *
	 * @param resource le properties di locale.
	 *@see #setBundles
	 */	
	public void removeLocale (Locale locale, boolean undoable, boolean undoing){
		if (locale==LocalizationProperties.DEFAULT){
			throw new IllegalArgumentException ("Cannot remove DEFAULT locale");
		}
		
		/*
		 * gestione UNDO
		 */
		if (undoable){
			final UndoableEditListener[] listeners = (UndoableEditListener[])getListeners (UndoableEditListener.class);

			final LocaleRemoval undoableAction = new LocaleRemoval (this, this.getLocalizationProperties (locale));
			final UndoableEditEvent editEvent = new UndoableEditEvent (this, undoableAction);
			for (int i=0; i< listeners.length;i++){
				listeners[i].undoableEditHappened (editEvent);
			}
		}
		
		final LocalizationProperties[] backup = this._resources;
		final int oldLength = backup.length;
		final int newLength = oldLength-1;
		final LocalizationProperties[] newResources = new LocalizationProperties [newLength];

		for (int i=0;i<newLength;i++){
			final LocalizationProperties lp = backup[i];
			if (lp.getLocale ().equals (locale)){
				if (i<newLength){
					System.arraycopy (backup, i+1, newResources, i, oldLength-i-1);
				}
				break;
			} else {
				newResources[i] = lp;
			}
		}
		guardModified (undoing);
		setBundles (newResources);
	}
	
	public String getName () {
		return this._name;
	}
	
	public String toString (){
		return getName ();
	}
	
	public void load (final File file){
		final String fileName = file.getName ();
		
		final int idx = fileName.lastIndexOf (".properties");
		if (idx<0){
			return;
		}
		setName (fileName.substring (0, idx));
		setPath (file.getParentFile ());
		setBundles (buildResources (file));
		resetModified ();
	}

	private File _path;
	/**
	 * Imposta la directory di salvataggio dei file di properties.
	 *
	 * @param path la directory che contiene i file di properties.
	 */	
	public void setPath (File path){
		if (this._path!=path){
			final File old = this._path;
			this._path = path;
			firePropertyChange ("path", old, path);
		}
	}
	
	/**
	 * Ritorna la directory di salvataggio delle properties.
	 */
	public File getPath (){
		return this._path;
	}

	/**
	 * Prepara e ritorna le risorse (Properties) individuate a partire da un file.
	 * Utilizza un algoritmo di ricerca analogo a quello implmentato in ResourceBundle.
	 *
	 * @param file il file base. Dovrebbe essere un Properties file (estensione .properties).
	 * @return tutte le properties associate al file specificato, secondo la logica di ResourceBundle.
	 */	
	private LocalizationProperties[] buildResources (File file){
		/*
		 * Individua il nome del file senza estensione, per la ricerca.
		 */
		final String fileName = file.getName ();
		int extensionIdx = fileName.indexOf ('.');
		final String baseName = extensionIdx>=0?fileName.substring (0, extensionIdx):fileName;
		final File parentDirectory = file.getParentFile ();
		final File[] properties = parentDirectory.listFiles (new FilenameFilter (){
			public boolean accept(File dir, String name){
				return name.startsWith (baseName) && name.endsWith (".properties");
			}
		});
		boolean defaultFound = false;
		final List<LocalizationProperties> retValue = new ArrayList<LocalizationProperties> ();
		for (int i=0; i<properties.length;i++){
			final File f = properties[i];
			try {
				int idx = f.getName ().indexOf (".properties");
				Locale l = getBundleLocale (baseName, f.getName ().substring (0, idx));
				if (!defaultFound && l == LocalizationProperties.DEFAULT){
					defaultFound = true;
				}
				final CommentedProperties p = new CommentedProperties (_peh);
				p.load (new FileInputStream (f));
				retValue.add (new LocalizationProperties (l, p));
			} catch (IllegalArgumentException iae){
				/* caso gestito */
			} catch (FileNotFoundException fnfe){
				throw new RuntimeException (fnfe);
			} catch (IOException ioe){
				throw new RuntimeException (ioe);
			}
		}
		if (!defaultFound){
			/*
			 * Aggiunge Locale di default
			 */
			retValue.add (0, new LocalizationProperties (LocalizationProperties.DEFAULT, new CommentedProperties (_peh)));
		}
		return (LocalizationProperties[])retValue.toArray (voidResourceArray);
		
	}
	
	/**
	 * Ritorna il Locale del bundle specificato. (Codice estratto da Resourcebundle);
	 * @param baseName the bundle's base name
	 * @param bundleName the complete bundle name including locale
	 * extension.
	 * @return
	 */
    private Locale getBundleLocale (String baseName, String bundleName) {
		if (baseName.length () == bundleName.length ()) {
			return LocalizationProperties.DEFAULT;
		} else if (baseName.length () < bundleName.length ()) {
			int pos = baseName.length ();
			String temp = bundleName.substring (pos + 1);
			pos = temp.indexOf ('_');
			if (pos == -1) {
				return new Locale (temp, "", "");
			}
			
			String language = temp.substring (0, pos);
			temp = temp.substring (pos + 1);
			pos = temp.indexOf ('_');
			if (pos == -1) {
				return new Locale (language, temp, "");
			}
			
			String country = temp.substring (0, pos);
			temp = temp.substring (pos + 1);
			
			return new Locale (language, country, temp);
		} else {
			//The base name is longer than the bundle name.  Something is very wrong
			//with the calling code.
			throw new IllegalArgumentException ();
		}
		
	}
	
	
	/**
	 * Rende persistente lo stato del modello.
	 * @param comment un commento.
	 */
	public void saveAs (File file, String comment) throws FileNotFoundException, IOException{
		setName (file.getName ());
		setPath (file.getParentFile ());
		if (null == comment){
			comment ="Created by URBE";
		}
		store (comment);
	}
	
	/**
	 * Rende persistente lo stato del modello.
	 * @param comment un commento.
	 */
	public void store (String comment) throws FileNotFoundException, IOException{
		for (int i =0;i<this._resources.length;i++){
			final LocalizationProperties lp = this._resources[i];
			final CommentedProperties p = lp.getProperties ();
			final Locale l = lp.getLocale ();
			final String language = l.getLanguage ();
			final String country = l.getCountry ();
			final String variant = l.getVariant ();
			final StringBuffer fileName = new StringBuffer (this.getName ());
			
			if (language!=null&&language.length ()>0){
				fileName.append ('_').append (language);
			}
			if (country!=null && country.length ()>0){
				fileName.append ('_').append (country);
			}
			if (variant!=null && variant.length ()>0){
				fileName.append ('_').append (variant);
			}
			fileName.append (".properties");
			
			final File f = new File (_path.getPath (), fileName.toString ());
			if (_applicationOptions.isBackupOnSaveEnabled ()) {
				/*
				 * crea copie di backup
				 */
				final File fbak = new File (f.getPath ()+"~");
				if (f.exists ()){
					final FileInputStream in = new FileInputStream (f);
					try {
						final FileOutputStream out = new FileOutputStream (fbak);
						try {
							int c;

							while ((c = in.read ()) != -1) {
								out.write (c);
							}

						} finally {
							out.close ();
						}
					} finally {
						in.close ();
					}
				}
			}
			
			lp.store (f, comment);
		}
		resetModified ();
	}	
	
	/**
	 * Adds a PropertyChangeListener to the listener list. The listener is
	 * registered for all bound properties of this class, including the
	 * following:
	 * <ul>
	 *    <li>il 'name' di questo modello
	 *    <li>il 'path' di questo modello
	 * </ul>
	 * <p>
	 * If listener is null, no exception is thrown and no action is performed.
	 *
	 * @param    listener  the PropertyChangeListener to be added
	 *
	 * @see #removePropertyChangeListener
	 * @see #getPropertyChangeListeners
	 * @see #addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public synchronized void addPropertyChangeListener (
	PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new java.beans.PropertyChangeSupport (this);
		}
		changeSupport.addPropertyChangeListener (listener);
	}
	
	/**
	 * Removes a PropertyChangeListener from the listener list. This method
	 * should be used to remove PropertyChangeListeners that were registered
	 * for all bound properties of this class.
	 * <p>
	 * If listener is null, no exception is thrown and no action is performed.
	 *
	 * @param listener the PropertyChangeListener to be removed
	 *
	 * @see #addPropertyChangeListener
	 * @see #getPropertyChangeListeners
	 * @see #removePropertyChangeListener(java.lang.String,java.beans.PropertyChangeListener)
	 */
	public synchronized void removePropertyChangeListener (
	PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener (listener);
	}
	
	/**
	 * Returns an array of all the property change listeners
	 * registered on this component.
	 *
	 * @return all of this component's <code>PropertyChangeListener</code>s
	 *         or an empty array if no property change
	 *         listeners are currently registered
	 *
	 * @see      #addPropertyChangeListener
	 * @see      #removePropertyChangeListener
	 * @see      #getPropertyChangeListeners(java.lang.String)
	 * @see      java.beans.PropertyChangeSupport#getPropertyChangeListeners
	 * @since    1.4
	 */
	public synchronized PropertyChangeListener[] getPropertyChangeListeners () {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners ();
	}
	
	/**
	 * Adds a PropertyChangeListener to the listener list for a specific
	 * property. The specified property may be user-defined, or one of the
	 * following:
	 * <ul>
	 *    <li>il 'name' di questo modello
	 *    <li>il 'path' di questo modello
	 * </ul>
	 * Note that if this Component is inheriting a bound property, then no
	 * event will be fired in response to a change in the inherited property.
	 * <p>
	 * If listener is null, no exception is thrown and no action is performed.
	 *
	 * @param propertyName one of the property names listed above
	 * @param listener the PropertyChangeListener to be added
	 *
	 * @see #removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see #getPropertyChangeListeners(java.lang.String)
	 * @see #addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public synchronized void addPropertyChangeListener (
	String propertyName,
	PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new java.beans.PropertyChangeSupport (this);
		}
		changeSupport.addPropertyChangeListener (propertyName, listener);
	}
	
	/**
	 * Removes a PropertyChangeListener from the listener list for a specific
	 * property. This method should be used to remove PropertyChangeListeners
	 * that were registered for a specific bound property.
	 * <p>
	 * If listener is null, no exception is thrown and no action is performed.
	 *
	 * @param propertyName a valid property name
	 * @param listener the PropertyChangeListener to be removed
	 *
	 * @see #addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see #getPropertyChangeListeners(java.lang.String)
	 * @see #removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public synchronized void removePropertyChangeListener (
	String propertyName,
	PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener (propertyName, listener);
	}
	
	/**
	 * Returns an array of all the listeners which have been associated
	 * with the named property.
	 *
	 * @return all of the <code>PropertyChangeListeners</code> associated with
	 *         the named property or an empty array if no listeners have
	 *         been added
	 *
	 * @see #addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see #removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 * @see #getPropertyChangeListeners
	 * @since 1.4
	 */
	public synchronized PropertyChangeListener[] getPropertyChangeListeners (
	String propertyName) {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners (propertyName);
	}
	
	/**
	 * Support for reporting bound property changes for Object properties.
	 * This method can be called when a bound property has changed and it will
	 * send the appropriate PropertyChangeEvent to any registered
	 * PropertyChangeListeners.
	 *
	 * @param propertyName the property whose value has changed
	 * @param oldValue the property's previous value
	 * @param newValue the property's new value
	 */
	protected void firePropertyChange (String propertyName,
	Object oldValue, Object newValue) {
		java.beans.PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange (propertyName, oldValue, newValue);
	}
	
	/**
	 * Support for reporting bound property changes for boolean properties.
	 * This method can be called when a bound property has changed and it will
	 * send the appropriate PropertyChangeEvent to any registered
	 * PropertyChangeListeners.
	 *
	 * @param propertyName the property whose value has changed
	 * @param oldValue the property's previous value
	 * @param newValue the property's new value
	 */
	protected void firePropertyChange (String propertyName,
	boolean oldValue, boolean newValue) {
		java.beans.PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange (propertyName, oldValue, newValue);
	}
	
	/**
	 * Support for reporting bound property changes for integer properties.
	 * This method can be called when a bound property has changed and it will
	 * send the appropriate PropertyChangeEvent to any registered
	 * PropertyChangeListeners.
	 *
	 * @param propertyName the property whose value has changed
	 * @param oldValue the property's previous value
	 * @param newValue the property's new value
	 */
	protected void firePropertyChange (String propertyName,
	int oldValue, int newValue) {
		java.beans.PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange (propertyName, oldValue, newValue);
	}
	
	public boolean containsLocale (Locale l){
		return this._localesSet.contains (l);
	}
	
	public Set getLocaleKeys (Locale l){
		return getLocalizationProperties (l).getProperties ().keySet ();
	}
	
	/**
	 * RItorna il set di Locale che contengono la chiave specificata.
	 *
	 * @param key la chiave.
	 * @return il set di Locale che contengono la chiave specificata.
	 */	
	public Set<Locale> getLocales (String key){
		final Set<Locale> s = new HashSet<Locale> ();
		for (int i = 0; i< this._resources.length;i++){
			LocalizationProperties lp = this._resources[i];
			if (lp.getProperties ().keySet ().contains (key)){
				s.add (lp.getLocale ());
			}
		}
		return s;
	}
	
	private boolean _isModified = false;
	/**
	 * Imposta lo stato di "modificato" al valore specificato..
	 * Se lo stato viene variato, notifica i PropertyChangeListener della modifica alla property "isModified".
	 *
	 * @param modified lo stato di "modificato".

	 */	
	private void setModified (boolean modified){
		if (this._isModified!=modified){
			this._isModified = modified;
			firePropertyChange ("isModified", !modified, modified);
		}
	}
	
	private int _modifiedCounter = 0;
	private void pushModified (){
		_modifiedCounter++;
		syncModified ();
	}
	
	private void syncModified (){
		setModified (_modifiedCounter!=0);
	}
	
	private void resetModified (){
		_modifiedCounter=0;
		syncModified ();
	}
	
	private void popModified (){
//		if (_modifiedCounter==0){
//			/*
//			 * UNDO dopo salvataggio (e successivo azzeramento)
//			 */
////			pushModified ();
//			return;
//		}
		_modifiedCounter--;
		
		syncModified ();
	}
	
	/**
	 * Ritorna <TT>true</TT> se ci sono modifiche pendenti non salvate.
	 *
	 * @return <TT>true</TT> se ci sono modifiche pendenti non salvate.
	 */	
	public boolean isModified (){
		return this._isModified;
	}
	
	
	public void addUndoableEditListener (UndoableEditListener listener) {
		listenerList.add (UndoableEditListener.class, listener);
	}
	
	public void removeUndoableEditListener (UndoableEditListener listener) {
		listenerList.remove (UndoableEditListener.class, listener);
	}
	
	/* Inizio UNDO/REDO */
	
	private void guardModified (boolean undoing){
		if (undoing){
			popModified ();
		} else {
			pushModified ();
		}
	}
	
//	class JvUndoableTableModel extends DefaultTableModel {
//		
//		public void setValueAt (Object value, int row, int column) {
//			setValueAt (value, row, column, true);
//		}
//		
//		
//		public void setValueAt (Object value, int row, int column, boolean undoable) {
//			UndoableEditListener listeners[] = getListeners (UndoableEditListener.class);
//			if (undoable == false || listeners == null) {
//				super.setValueAt (value, row, column);
//				return;
//			}
//			
//			
//			Object oldValue = getValueAt (row, column);
//			super.setValueAt (value, row, column);
//			JvCellEdit cellEdit = new JvCellEdit (this, oldValue, value, row, column);
//			UndoableEditEvent editEvent = new UndoableEditEvent (this, cellEdit);
//			for (UndoableEditListener listener : listeners)
//				listener.undoableEditHappened (editEvent);
//		}
//		
//		
//		public void addUndoableEditListener (UndoableEditListener listener) {
//			listenerList.add (UndoableEditListener.class, listener);
//		}
//	}
	
	
	class ValueEdit extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
		protected String oldValue;
		protected String newValue;
		protected Locale locale;
		protected String key;
		
		
		public ValueEdit (DefaultResourceBundleModel model, String oldValue, String newValue, Locale locale, String key) {
			this.model = model;
			this.oldValue = oldValue;
			this.newValue = newValue;
			this.locale = locale;
			this.key = key;
		}
		
		
		public String getPresentationName () {
			if (null==newValue && newValue!=oldValue) {
				/*
				 * impostato valore a null
				 */
				return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("set_null");
			} else {
				return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("edit");
			}
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.setValue (locale, key, oldValue, false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.setValue (locale, key, newValue, false, false);
		}
	}
	
	class ValueCommentEdit extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
		protected String oldValue;
		protected String newValue;
		protected String oldComment;
		protected String newComment;
		protected Locale locale;
		protected String key;
		
		
		public ValueCommentEdit (DefaultResourceBundleModel model, String oldValue, String newValue, String oldComment, String newComment, Locale locale, String key) {
			this.model = model;
			this.oldValue = oldValue;
			this.newValue = newValue;
			this.oldComment = oldComment;
			this.newComment = newComment;
			this.locale = locale;
			this.key = key;
		}
		
		
		public String getPresentationName () {
			if (null==newValue && newValue!=oldValue) {
				/*
				 * impostato valore a null
				 */
				return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("set_null");
			} else {
				return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("edit");
			}
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.setValue (locale, key, oldValue, false, true);
			model.setComment (locale, key, oldComment, false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.setValue (locale, key, newValue, false, false);
			model.setComment (locale, key, newComment, false, false);
		}
	}
	
	class KeyAddition extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
		protected String newValue;
		protected String newComment;
		protected Locale locale;
		protected String key;
		
		
		public KeyAddition (DefaultResourceBundleModel model, String newValue, String newComment, Locale locale, String key) {
			this.model = model;
			this.newValue = newValue;
			this.newComment = newComment;
			this.locale = locale;
			this.key = key;
		}
		
		
		public String getPresentationName () {
			return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("key_addition");
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.setValue (this.locale, this.key, null, false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.addKey (locale, key, newValue, newComment, false, true, false);
		}
	}
	
	class KeyRemoval extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
//		protected Locale[] locales;
		protected String[] values;
		protected String key;
		
		
		public KeyRemoval (DefaultResourceBundleModel model, String key, /*Locale[] locales, */String[] values) {
			this.model = model;
			this.values = values;
//			this.locales = locales;
			this.key = key;
		}
		
		
		public String getPresentationName () {
			return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("key_removal");
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.addKey (key, values, false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.removeKey (key, false, true, false);
		}
	}
	
	
	class CommentEdit extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
		protected String oldValue;
		protected String newValue;
		protected Locale locale;
		protected String key;
		
		
		public CommentEdit (DefaultResourceBundleModel model, String oldValue, String newValue, Locale locale, String key) {
			this.model = model;
			this.oldValue = oldValue;
			this.newValue = newValue;
			this.locale = locale;
			this.key = key;
		}
		
		
		public String getPresentationName () {
			return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("comment_change");
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.setComment (locale, key, oldValue, false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.setComment (locale, key, newValue, false, false);
		}
	}
	
	class LocaleAddition extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
		protected LocalizationProperties resource;
		
		
		public LocaleAddition (DefaultResourceBundleModel model, LocalizationProperties resource) {
			this.model = model;
			this.resource = resource;
		}
		
		
		public String getPresentationName () {
			return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("locale_addition");
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.removeLocale (resource.getLocale (), false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.addLocale (resource, false, false);
		}
	}
	
	class LocaleRemoval extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
		protected LocalizationProperties resource;
		
		
		public LocaleRemoval (DefaultResourceBundleModel model, LocalizationProperties resource) {
			this.model = model;
			this.resource = resource;
		}
		
		
		public String getPresentationName () {
			return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("locale_removal");
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.addLocale (resource, false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.removeLocale (resource.getLocale (), false, false);
		}
	}
	
	class KeyChange extends AbstractUndoableEdit {
		protected DefaultResourceBundleModel model;
		protected String oldKey;
		protected String newKey;
		
		
		public KeyChange (DefaultResourceBundleModel model, String oldKey, String newKey) {
			this.model = model;
			this.oldKey = oldKey;
			this.newKey = newKey;
		}
		
		
		public String getPresentationName () {
			return java.util.ResourceBundle.getBundle("com.davidecavestro.rbe.gui.res").getString("key_change");
		}
		
		
		public void undo () throws CannotUndoException {
			super.undo ();
			
			model.changeKey (newKey, oldKey, false, true);
		}
		
		
		public void redo () throws CannotUndoException {
			super.redo ();
			
			model.changeKey (oldKey, newKey, false, false);
		}
	}
}
