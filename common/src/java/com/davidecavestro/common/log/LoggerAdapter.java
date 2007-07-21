/*
 * LoggerAdapter.java
 *
 * Created on 30 novembre 2005, 23.05
 */

package com.davidecavestro.common.log;

/**
 * Implementazione di comodo vuota. ignora tutti i messaggi.
 *
 * @author  davide
 */
public class LoggerAdapter implements Logger {
	
	/** Costruttore */
	public LoggerAdapter () {
	}
	
	public void close () {
	}
	
	public void debug (String message) {
	}
	
	public void debug (String message, Throwable t) {
	}
	
	public void error (String message) {
	}
	
	public void error (String message, Throwable t) {
	}
	
	public void info (String message) {
	}
	
	public void warning (String message) {
	}
	
	public void warning (String message, Throwable t) {
	}
	
}
