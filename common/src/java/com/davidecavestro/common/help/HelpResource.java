/*
 * HelpResource.java
 *
 * Created on 27 dicembre 2004, 13.45
 */

package com.davidecavestro.common.help;

/**
 * Una risorsa di help. E' costituita da un valore, utilizzabile direttamente 
 * per il recupero di risorse dall'help, oppure come chiave per una mappatura.
 *
 * @author  davide
 */
public final class HelpResource {
	/**
	 * La risorsa.
	 */
	private final String _resource;
	
	
	/**
	 * Costruttore con risorsa specificata.
	 *
	 * @param resource il valore della risorsa.
	 */	
	public HelpResource (final String value){
		this._resource = value;
	}
	
	/**
	 * Ritorna il valore di questa risorsa.
	 *
	 * @return il valore di questa risorsa.
	 */
	public String getValue (){
		return this._resource;
	}
	
	/**
	 * Ritorna <TT>true</TT> se questa risorsa ? uguale all'oggetto specificato.
	 *
	 * @param obj l'oggetto da confrontare.
	 * @return <TT>true</TT> se questa risorsa ? uguale all'oggetto specificato.
	 */	
	public boolean equals (Object obj){
		if (this==obj){
			/* identit?*/
			return true;
		} else if (obj==null){
			/* test su NULL*/
			return false;
		} else {
			if (obj instanceof HelpResource){
				/* stesso tipo */
				final HelpResource test = (HelpResource)obj;
				/* testa valore */
				return this._resource==test._resource ||
				(this._resource!=null && this._resource.equals (test._resource));
			} else {
				/* tipo incompatibile*/
				return false;
			}
		}
		
	}
	
	/**
	 * Ritorna la rappresentazione in formato stringa di questa risorsa.
	 *
	 * @return una stringa che rappresenta questa risorsa.
	 */	
	public String toString (){
		final StringBuffer sb = new StringBuffer ();
		sb.append ("resource: ").append (this._resource);
		return sb.toString ();
	}
}
