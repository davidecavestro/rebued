/*
 * DialogEvent.java
 *
 * Created on 11 dicembre 2005, 11.51
 */

package com.davidecavestro.common.gui.dialog;

import java.awt.Dialog;
import java.util.Locale;

/**
 * DialogEvent e' usato per notificare i listener di una dialog.
 * Il tipo puo' assumere i valori di ritorno di JOptionPane quali, ad esempio, JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION.
 *
 * L'evento puo'avere un valore associato, al quale e' possibile accedere tramite il metodo #getValue .
 * @author  davide
 */
public class DialogEvent<T> extends java.util.EventObject {
	
	/**
	 * Il tipo di evento.
	 */
	protected int type;
	
	/**
	 * Il valore associato all'evento.
	 */
	protected T value;

	/** 
	 * Evento senza valore associato.
	 */
	public DialogEvent (Dialog source, int type) {
		this (source, null, type);
	}
	
	/**
	 *
	 * La dialog ha avuto una modificadi tipo <TT>type</TT>, e ora ritorna un valore <TT>value</TT>.
	 * Il tipo puo' assumere i valori di ritorno di JOptionPane quali, ad esempio, JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION.
	 * @param source la dialog sorgente.
	 * @param value il valore interessato.
	 * @param type il tipo di evento.
	 */
	public DialogEvent (Dialog source, T value, int type) {
		super (source);
		this.value = value;
		this.type=type;
	}
	
	/**
	 * Ritorna il tipo di evento.  
	 * Il tipo puo' assumere i valori di ritorno di JOptionPane quali, ad esempio, JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION.
	 *
	 * @return il tipo di evento.
	 */	
	public int getType (){
		return this.type;
	}
	
	/**
	 * Ritorna il valore interessato da questo evento.
	 *
	 * @return il valore interessato da questo evento.
	 */	
	public T getValue (){
		return this.value;
	}
	
}
