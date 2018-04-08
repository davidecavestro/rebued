/*
 * MessageType.java
 *
 * Created on January 3, 2008, 1:37 PM
 *
 */

package com.davidecavestro.common.log;

/**
 * Tipo di messaggio per il log.
 *
 * @author Davide Cavestro
 */
public  enum MessageType {
	/**
	 * Messaggio di tipo Errore.
	 */
	ERROR {
		public Object accept (MessageTypeVisitor v, final String... message) {
			return v.acceptError (this, message);
		}
	},
	/**
	 * Messaggio di debug
	 */
	DEBUG {
		public Object accept (MessageTypeVisitor v, final String... message) {
			return v.acceptDebug (this, message);
		}
	},
	/**
	 * Messaggio di info
	 */
	INFO {
		public Object accept (MessageTypeVisitor v, final String... message) {
			return v.acceptInfo (this, message);
		}
	},
	/**
	 * Messaggio di warning
	 */
	WARNING {
		public Object accept (MessageTypeVisitor v, final String... message) {
			return v.acceptWarning (this, message);
		}
	};
	
	/**
	 * Definisce l'accesso da parte di un visitor.
	 */
	public abstract Object accept (MessageTypeVisitor v, String... message);
	
	/**
	 * Consente di accedere ad un messaggio in base al tipo.
	 */
	public static interface MessageTypeVisitor {
		Object acceptError (MessageType t, String... message);
		Object acceptDebug (MessageType t, String... message);
		Object acceptInfo (MessageType t, String... message);
		Object acceptWarning (MessageType t, String... message);
	}
}
