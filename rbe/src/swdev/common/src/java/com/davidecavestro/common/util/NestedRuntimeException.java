/*
 * NestedRuntimeException.java
 *
 * Created on 23 maggio 2004, 19.09
 */

package com.davidecavestro.common.util;

/**
 * Condizione di errore derivata. Puo' essere usata per incapsulare una eccezione
 * normale in modo da non doverla dichiarare come sollevabile, ovvero trattarla 
 * come non prevedibile.
 *
 * @author  davide
 */
public final class NestedRuntimeException extends java.lang.RuntimeException {
	
	private Throwable rootCause;
	/**
	 * 
	 * Costruttore vuoto.
	 */
	public NestedRuntimeException() {
	}
	
	
	/**
	 * Costruttore con messaggio di dettaglio.
	 *
	 * @param msg il messaggio di dettaglio.
	 */
	public NestedRuntimeException(String msg) {
		super(msg);
	}
	
	
	/** 
	 * Costruttore con causa scatenante.
	 *
	 * @param rootCause la causa di questa eccezione.
	 */
	public NestedRuntimeException(Throwable rootCause) {
		super ();
		this.rootCause = rootCause;
	}
	
	/**
	 *
	 * Costruttore con messaggio e causa scatenante.
	 *
	 * @param message il messaggio di errore.
	 * @param rootCause la causa di questa eccezione.
	 */
	public NestedRuntimeException(String message, Throwable rootCause) {
		super (message);
		this.rootCause = rootCause;
	}
	
	/**
	 * Ritorna la causa che ha portato al sollevamento di questa eccezione.
	 *
	 * @return la causa.
	 */	
	public Throwable getRootCause (){
		return this.rootCause;
	}
	
	/**
	 * Ritorna una rappresentazione in formato stringa di uesta eccezione.
	 *
	 * @return la stringa che rappresenta questa eccezione.
	 */	
	public String toString (){
		return ExceptionUtils.getStackTrace (this).toString ();
	}
}
