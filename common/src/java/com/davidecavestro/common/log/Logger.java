/*
 * Logger.java
 *
 * Created on 11 dicembre 2004, 19.10
 */

package com.davidecavestro.common.log;

/**
 * Componente per le registrazione di messaggi inerenti allo stato 
 * dell'applicazione ed alle elaborazioni in atto.
 *
 * @author  davide
 */
public interface Logger {
	/**
	 * Registra un messaggio di DEBUG.
	 * @param message il messaggio.
	 */
	public void debug (String... message);
	/**
	 * Registra un messaggio di DEBUG associatoall'eventospecificato.
	 * @param t l'evento.
	 * @param message il messaggio.
	 */
	public void debug (Throwable t, String... message);
	/**
	 * Registra un messaggio di INFORMAZIONE.
	 * @param message il messaggio.
	 */
	public void info (String... message);
	/**
	 * Registra un messaggio di AVVISO.
	 * @param message il messaggio.
	 */
	public void warning (String... message);
	/**
	 * Registra un messaggio di AVVISO associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void warning (Throwable t, String... message);
	/**
	 * Registra un messaggio di ERRORE.
	 * @param message il messaggio.
	 */
	public void error (String... message);
	/**
	 * Registra un messaggio di ERRORE associato ad un evento specificato.
	 * @param message il messaggio.
	 * @param t l'evento.
	 */
	public void error (Throwable t, String... message);
	
	/**
	 * Chiude gli stream aperti.
	 */
	public void close();
}
