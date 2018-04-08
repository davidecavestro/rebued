/*
 * DefaultResourceBundleModel.java
 *
 * Created on 2 dicembre 2005, 20.49
 */

package com.davidecavestro.rbe.model;

import com.davidecavestro.rbe.model.event.ResourceBundleModelEvent;
import com.davidecavestro.rbe.model.event.ResourceBundleModelListener;
import java.util.EventListener;
import java.util.Locale;
import javax.swing.event.EventListenerList;

/**
 *
 * @author  davide
 */
public abstract class AbstractResourceBundleModel implements ResourceBundleModel {
	
	protected final EventListenerList listenerList = new EventListenerList ();
	
	/** Costruttore. */
	public AbstractResourceBundleModel () {
	}
	
	/**
	 * Aggiunge un listener che deve essere notificato ad ogni modifica al modello.
	 *
	 * @param	l		il listener
	 */
	public void addResourceBundleModelListener (ResourceBundleModelListener l) {
		listenerList.add (ResourceBundleModelListener.class, l);
	}
	
	/**
	 * Rimuove un listener registrato du questo modello.
	 *
	 * @param l il listenenr da rimuovere.
	 */	
	public void removeResourceBundleModelListener (ResourceBundleModelListener l) {
		listenerList.remove (ResourceBundleModelListener.class, l);
	}

	/**
	 * Implementazione vuota. 
	 * Serve a evitare di dover implementare ilmetodo se non necessario.
	 *
	 * @param locale
	 * @param key
	 * @param value
	 */	
	public void setValue (java.util.Locale locale, String key, String value) {
	}
	
    /**
	 * Notifica tutti i listener che sono state aggiunte entry nel ResourceBundle .
	 *
	 * @see ResourceBundleModelEvent
	 * @see EventListenerList
	 * @param keys le entry inserite.
	 */
    public void fireKeysInserted (String[] keys) {
        fireResourceBundleModelChanged (
			new ResourceBundleModelEvent (this, ResourceBundleModelEvent.ALL_LOCALES, keys, ResourceBundleModelEvent.INSERT));
    }
	
    /**
	 * Notifica tutti i listener che sono state modificate entry nel ResourceBundle .
	 *
	 * @see ResourceBundleModelEvent
	 * @see EventListenerList
	 * @param keys le entry modificate.
	 */
    public void fireKeysUpdated (String[] keys) {
        fireResourceBundleModelChanged (
			new ResourceBundleModelEvent (this, ResourceBundleModelEvent.ALL_LOCALES, keys, ResourceBundleModelEvent.UPDATE));
    }
	
    /**
	 * Notifica tutti i listener che sono state rimosse entry dal ResourceBundle .
	 *
	 * @see ResourceBundleModelEvent
	 * @see EventListenerList
	 * @param keys le entry rimosse.
	 */
    public void fireKeysDeleted (String[] keys) {
        fireResourceBundleModelChanged (
			new ResourceBundleModelEvent (this, ResourceBundleModelEvent.ALL_LOCALES, keys, ResourceBundleModelEvent.DELETE));
    }
	
    /**
     * Notifica tutti i listener che la struttura del ResourceBundle  e'  cambiata.
	 * Puo' essereusato per notificare l'inserimento o la rimozione di Locale.
     *
     * @see ResourceBundleModelEvent
     * @see EventListenerList
     *
     */
    public void fireResourceBundleStructureChanged () {
        fireResourceBundleModelChanged (
			new ResourceBundleModelEvent (this, ResourceBundleModelEvent.ALL_LOCALES, ResourceBundleModelEvent.ALL_KEYS, ResourceBundleModelEvent.UPDATE));
    }
	
    /**
     * Notifica tutti i listener che i dati del ResourceBundle sono cambiati.
	 * Si assume che i Locale e le chiavi siano rimasti invariati.
     *
     * @see ResourceBundleModelEvent
     * @see EventListenerList
     *
     */
    public void fireResourceBundleDataChanged () {
        fireResourceBundleModelChanged (
			new ResourceBundleModelEvent (this));
    }
	
	
    /**
	 * Notifica tutti i listener che  e'  cambiato il valore per una entry nel ResourceBundle .
	 *
	 * @see ResourceBundleModelEvent
	 * @see EventListenerList
	 * @param locale il Locale.
	 * @param key la chiave.
	 */
    public void fireResourceBundleValueUpdated (Locale locale, String key) {
        fireResourceBundleModelChanged (
			new ResourceBundleModelEvent (this, locale, new String[]{key}, ResourceBundleModelEvent.UPDATE));
    }
	
	/**
	 * notifica i listener dell'evento di modifica del modello.
	 * @param rbmEvent l'evento di modifica
	 */
	public void fireResourceBundleModelChanged (ResourceBundleModelEvent rbmEvent) {
		// Guaranteed to return a non-null array
		final Object[] listeners = listenerList.getListenerList ();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==ResourceBundleModelListener.class) {
				// Lazily create the event:
				if (rbmEvent == null) {
					rbmEvent = new ResourceBundleModelEvent (this);
				}
				((ResourceBundleModelListener)listeners[i+1]).resourceBundleChanged (rbmEvent);
			}
		}
	}
	
    /**
	 * Ritonra un array di tutti gli oggetti attualmente registrati come
	 * listener del tipo <code><em>Foo</em>Listener</code>, su questo modello,
	 * dove <em>Foo</em>  e'  individuato dal tipo specificato.
	 *
	 *
	 * @return un array di oggetti regitrati come 
	 *          <code><em>Foo</em>Listener</code>s su questo modello, 
	 *			oppure un array vuoto, se nessun listener di tale tipo  e' ' stato registrato,
	 * @see #getResourceBundleModelListeners
	 * @param listenerType il tipo di lestener richiesto; questo parametro
	 *			dovrebbe individuare un'interfaccia che derivi da 
	 *          <code>java.util.EventListener</code>
	 */
	public EventListener[] getListeners(Class listenerType) { 
		return listenerList.getListeners(listenerType); 
	}

	
}
