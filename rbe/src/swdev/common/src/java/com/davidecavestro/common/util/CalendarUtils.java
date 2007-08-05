/*
 * CalendarUtils.java
 *
 * Created on 24 aprile 2004, 11.06
 */

package com.davidecavestro.common.util;

import java.text.*;
import java.util.*;

/**
 * Classe di utilita' per oggetti rappresentanti date.
 * @author  davide
 */
public final class CalendarUtils {
	/**
	 * Formato timestamp.
	 */
	public final static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	/**
	 * Formato timestamp per nomi di file.
	 */
	public final static String FILENAME_TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS";
	
	/** Costruttore privato, la clase deve esporre solometodi statici */
	private CalendarUtils() {
	}
	
	/**
	 * Permette di valutare l'uguaglianza fra due <code>Date</code>, comprese
	 * istanze <code>null</code>.
	 * @param c1 la prima istanza da confrontare.
	 * @param c2 la seconda istanza da confrontare.
	 * @return <code>true</code> se <code>c1</code> e' uguale a <code>c2</code>; 
	 * <code>false</code> altrimenti.
	 */	
	public static boolean equals (final Date c1, final Date c2){
		return (c1==null && c2==null) || 
			(c1!=null && c2!=null &&
				(c1.equals(c2)));
	}
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
	
	/**
	 * Ritorna una stringa che rappresenta la data specificata. Il formato utilizzato
	 * e' <TT>dd/MM/yyyy HH:mm:ss</TT>. Questo metodo deve essere usatoa solo scopo di 
	 * log o debug. Il formato potebbe in futuro cambiare.
	 *
	 * @param date la data
	 * @return una stringa che rappresenta la data specificata.
	 */	
	public static String toTSString (final Date date){
		if (date==null){
			return null;
		}
		return dateFormat.format(date);
	}
	
	/**
	 * Ritorna un timestamp della data specificata usando il formato specificato.
	 *
	 * @param format il formato.
	 * @param date la data da cui ricavare l timestamp
	 * @return un timestamp della data specificata usando il formato specificato.
	 * @deprecated usare <TT>getTimestamp ()</TT>;
	 */	
	public static String getTS (final Date date, final String format){
		return getTimestamp (date, format);
	}
	
	/**
	 * Ritorna un timestamp della data specificata usando il formato 
	 * {@link com.ost.timekeeper.util.CalendarUtils#TIMESTAMP_FORMAT}.
	 *
	 * @param format il formato.
	 * @param date la data da cui ricavare l timestamp
	 * @return un timestamp della data specificata usando il formato specificato.
	 */	
	public static String getTimestamp (final Date date){
		return getTimestamp (date, TIMESTAMP_FORMAT);
	}
	
	/**
	 * Ritorna un timestamp della data specificata usando il formato specificato.
	 *
	 * @param format il formato.
	 * @param date la data da cui ricavare l timestamp
	 * @return un timestamp della data specificata usando il formato specificato.
	 */	
	public static String getTimestamp (final Date date, final String format){
		if (date==null){
			return null;
		}
		return new SimpleDateFormat (format).format(date);
	}
	
	/** Ritorna una istanza di  <TT>Calendar</TT> valorizzata con la data
	 * specificata dal timestamp speccificato.
	 *
	 * @return una <TT>Calendar</TT> valorizzato con <TT>timestamp</TT>, interpretato
	 * nel formato <TT>timestampFormat</TT>.
	 * @see {@link java.text.SimpleDateFormat} per il formato del timestamp.
	 * @param timeStamp il timestamp.
	 * @param timestampFormat il formato del timestamp.
	 * @throws NestedRuntimeException in caso di timestamp non interpretabile.
	 */	
	public static Calendar getCalendar ( final String timeStamp, final String timestampFormat ) throws NestedRuntimeException{
		if (timeStamp==null || timeStamp.length ()==0){
			return null;
		}
		
		final SimpleDateFormat tsFormat = new SimpleDateFormat ( timestampFormat );
		try {
			final Date date = tsFormat.parse ( timeStamp );
			final Calendar calendar = new GregorianCalendar ();
			calendar.setTime ( date );

			return calendar;
		} catch (java.text.ParseException pe) {
			throw new NestedRuntimeException (pe);
		}
	}
	
	/**
	 * Converte un timestamp in un <TT>Calendar</TT>, interpretandolo nel formato 
	 * {@link com.ost.timekeeper.util.CalendarUtils#TIMESTAMP_FORMAT}.
	 *
	 * @param strDate il timestamp.
	 * @param format il formato del timestamp.
	 * @return il <TT>Calendar</TT> ricavato dalla conversione del timestamp specificato.
	 * @throws ParseException se il timestamp ed il formato specificati non solo compatibili.
	 */	
	public static Calendar timestamp2Calendar ( final String strDate) throws ParseException {
		return timestamp2Calendar ( strDate, TIMESTAMP_FORMAT);
	}
	 
	/**
	 * Converte un timestamp in un <TT>Calendar</TT>
	 *
	 * @param strDate il timestamp.
	 * @param format il formato del timestamp.
	 * @return il <TT>Calendar</TT> ricavato dalla conversione del timestamp specificato.
	 * @throws ParseException se il timestamp ed il formato specificati non solo compatibili.
	 */	
	public static Calendar timestamp2Calendar ( final String strDate, final String format ) throws ParseException {
		if (strDate==null || strDate.length ()==0){
			return null;
		}
		final Date fDate = timestamp2Date ( strDate, format );
		final Calendar calendar = new GregorianCalendar ();
		calendar.setTime ( fDate );
		
		return calendar;
	}
	
	/**
	 * Converte un timestamp in una data, interpretandolo nel formato 
	 * {@link com.ost.timekeeper.util.CalendarUtils#TIMESTAMP_FORMAT}.
	 *
	 * @return la data ricavata da <TT>strDate</TT>.
	 * @param format il formato
	 * @param strDate il timestamp
	 * @throws ParseException se il timestamp ed il formato specificati non solo compatibili.
	 */	
	public static Date timestamp2Date ( final String strDate) throws ParseException {
		return timestamp2Date (strDate, TIMESTAMP_FORMAT);
	}
	
	/**
	 * Converte un timestamp in una data.
	 *
	 * @return la data ricavata da <TT>strDate</TT>.
	 * @param format il formato
	 * @param strDate il timestamp
	 * @throws ParseException se il timestamp ed il formato specificati non solo compatibili.
	 */	
	public static Date timestamp2Date ( final String strDate, final String format ) throws ParseException {
		if (strDate==null || strDate.length ()==0){
			return null;
		}
		return new SimpleDateFormat ( format ).parse ( strDate );
	}
	
	/**
	 * Reimposta a 0 le ore, minuti, secondi e frazioni della data specificata.
	 *
	 * @param date la data.
	 */	
	public static void resetTime (Date date ){
		final Calendar now = new GregorianCalendar ();
		
		if (date!=null){
			now.setTime (date);
		}
		now.set (Calendar.HOUR_OF_DAY, 0);
		now.set (Calendar.MINUTE, 0);
		now.set (Calendar.SECOND, 0);
		now.set (Calendar.MILLISECOND, 0);

		date.setTime (now.getTime ().getTime ());
	}
	
	/**
	 * Ritorna una data a partire della data specificata con le ore, minuti, secondi e frazioni azzerate.
	 *
	 * @param date la data.
	 */	
	public static Date resetTimeCopy (final Date date ){
		final Date clone = new Date (date.getTime ());
		resetTime (clone);
		return clone;
	}
}
