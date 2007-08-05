/*
 * SettingsSupport.java
 *
 * Created on 13 settembre 2004, 23.01
 */

package com.davidecavestro.common.util.settings;

import com.davidecavestro.common.util.CalendarUtils;
import com.davidecavestro.common.util.NestedRuntimeException;
import java.awt.*;
import java.io.File;
import java.util.*;

/**
 * Gestisce la persistenza delle impostazioni personalizzate.
 *
 * @author  davide
 */
public final class SettingsSupport {
	
	/** 
	 * Costruttore privato.
	 */
	private SettingsSupport() {
	}
	
	/**
	 * Ritorna il valore di una proprieta' di tipo booleano.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @return il valore di una proprieta' di tipo booleano.
	 */	
	public static Boolean getBooleanProperty (Properties properties, String propertyName){
		final String propertyValue = properties.getProperty (propertyName);
		if (propertyValue!=null && propertyValue.trim ().length ()>0){
			return new Boolean (propertyValue);
		} else {
			return null;
		}
	}
	
	/**
	 * Imposta il valore di una proprieta' di tipo booleano.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @param propertyValue il valore della proprieta'.
	 */	
	public static void setBooleanProperty (final Properties properties, final String propertyName, final Boolean propertyValue){
		if (propertyValue!=null){
			properties.setProperty (propertyName, propertyValue.toString ());
		} else {
			properties.setProperty (propertyName, "");
		}
	}
	
	/**
	 * Ritorna il valore di una proprieta' di tipo stringa.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @return il valore di una proprieta' di tipo stringa.
	 */	
	public static String getStringProperty (Properties properties, String propertyName){
		return properties.getProperty (propertyName);
	}
	
	/**
	 * Imposta il valore di una proprieta' di tipo stringa.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @param propertyName il valore della proprieta'.
	 */	
	public static void setStringProperty (final Properties properties, final String propertyName, final String propertyValue){
		properties.setProperty (propertyName, propertyValue);
	}
	
	/**
	 * Ritorna il valore di una proprieta' di tipo data.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @return il valore di una proprieta' di tipo data.
	 */	
	public static Calendar getCalendarProperty (Properties properties, String propertyName){
		final String propertyValue = properties.getProperty (propertyName);
		if (propertyValue!=null){
			return CalendarUtils.getCalendar (propertyValue, CalendarUtils.TIMESTAMP_FORMAT);
		} else {
			return null;
		}
	}
	
	/**
	 * Ritorna il valore di una proprieta' di tipo intero.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @return il valore di una proprieta' di tipo intero.
	 */	
	public static Integer getIntegerProperty (Properties properties, String propertyName){
		final String propertyValue = properties.getProperty (propertyName);
		if (propertyValue!=null){
			return new Integer (propertyValue);
		} else {
			return null;
		}
	}
	
	/**
	 * Imposta il valore di una proprieta' di tipo intero.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @param propertyValue il valore della proprieta'.
	 */	
	public static void setIntegerProperty (Properties properties, String propertyName, Integer propertyValue){
		if (propertyValue!=null){
			properties.setProperty (propertyName, propertyValue.toString ());
		} else {
			/* Imposta valore NULL*/
			properties.setProperty (propertyName, "");
		}
	}
	
	/**
	 * Ritorna il valore di una proprieta' di tipo double.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @return il valore di una proprieta' di tipo double.
	 */	
	public static Double getDoubleProperty (Properties properties, String propertyName){
		final String propertyValue = properties.getProperty (propertyName);
		if (propertyValue!=null){
			return new Double (propertyValue);
		} else {
			return null;
		}
	}
	
	/**
	 * Imposta il valore di una proprieta' di tipo double.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @param propertyValue il valore della proprieta'.
	 */	
	public static void setDoubleProperty (Properties properties, String propertyName, Double propertyValue){
		if (propertyValue!=null){
			properties.setProperty (propertyName, propertyValue.toString ());
		} else {
			/* Imposta valore NULL*/
			properties.setProperty (propertyName, "");
		}
	}
	
	/**
	 * Ritorna il valore di una proprieta' di tipo Color.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @return il valore di una proprieta' di tipo Color.
	 */	
	public static Color getColorProperty (Properties properties, String propertyName){
		final String colorValue = properties.getProperty (propertyName);
		if (colorValue==null || colorValue.length ()==0){
			return null;
		}
		final StringTokenizer st = new StringTokenizer (colorValue, ",");
		if (st.countTokens ()>=3){
			/*
			 * Interpretato come RGB (es: 255,255,255).
			 * Contiene valori comma-separated
			 */
			return new Color (
				//R
				Integer.parseInt (st.nextToken()), 
				//G
				Integer.parseInt (st.nextToken()), 
				//B
				Integer.parseInt (st.nextToken())
				);
			} else {
				try {
					return (Color)Color.class.getDeclaredField (colorValue).get (null);
				} catch (Exception e){
					throw new NestedRuntimeException (e);
				}
			}
		
	}
	
	/**
	 * Imposta il valore di una proprieta' di tipo Color.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 * @param colorValue il valore della proprieta'.
	 */	
	public static void setColorProperty (Properties properties, String propertyName, Color colorValue){
		final StringBuffer propertyValue = new StringBuffer ();
		if (colorValue==null){
			propertyValue.append ("");
		} else {
			propertyValue.append (colorValue.getRed ())
			.append (",")
			.append (colorValue.getGreen ())
			.append (",")
			.append (colorValue.getBlue ());
		}
		properties.setProperty (propertyName, propertyValue.toString ());
	}
	
	/**
	 * Ritorna il valore di un set di proprieta' che rappresentano un tipo rettangolo.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param xPosName il nome della proprieta' POSIZIONE ORIZZONTALE.
	 * @param yPosName il nome della proprieta' POSIZIONE VERTICALE.
	 * @param widthName il nome della proprieta' LARGHEZZA.
	 * @param heightName il nome della proprieta' ALTEZZA.
	 * @return il valore di un set di proprieta' che rappresentano un tipo rettangolo.
	 */	
	public static Rectangle getRectangle (final Properties properties, final String xPosName, final String yPosName, final String widthName, final String heightName){
		final Double xPos = getDoubleProperty (properties, xPosName);
		if (xPos==null){
			return null;
		}
		final Double yPos = getDoubleProperty (properties, yPosName);
		if (yPos==null){
			return null;
		}
		final Double width = getDoubleProperty (properties, widthName);
		if (width==null){
			return null;
		}
		final Double height = getDoubleProperty (properties, heightName);
		if (height==null){
			return null;
		}
		return new Rectangle (xPos.intValue (), yPos.intValue (), width.intValue (), height.intValue ());
	}
	
	/**
	 * Imposta il valore di un set di proprieta' che rappresentano un tipo rettangolo.
	 *
	 * @param properties la risorsa di configurazione.
	 * @param r il rettangolo.
	 * @param xPosName il nome della proprieta' POSIZIONE ORIZZONTALE.
	 * @param yPosName il nome della proprieta' POSIZIONE VERTICALE.
	 * @param widthName il nome della proprieta' LARGHEZZA.
	 * @param heightName il nome della proprieta' ALTEZZA.
	 */	
	public static void setRectangle (final Properties properties, final Rectangle r, final String xPosName, final String yPosName, final String widthName, final String heightName){
		setDoubleProperty (properties, xPosName, new Double (r.getX ()));
		setDoubleProperty (properties, yPosName, new Double (r.getY ()));
		setDoubleProperty (properties, widthName, new Double (r.getWidth ()));
		setDoubleProperty (properties, heightName, new Double (r.getHeight ()));
	}
	
	/**
	 * Ritorna l'array di valori di una proprieta' di tipo stringa vettoriale.
	 *
	 * @return i valori di una proprieta' di tipo stringa vettoriale.
	 * @param delimiter il delimitatore dei valori.
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 */	
	public static String[] getPaths (Properties properties, String propertyName, String delimiter){
		
		String value = properties.getProperty (propertyName);
		if (null==value){
			return new String[0];
		}
		final StringTokenizer st = new StringTokenizer (value, delimiter, false);
		final String[] retValue = new String[st.countTokens ()];
		int i = 0;
		while (st.hasMoreTokens ()){
			retValue[i] = st.nextToken ();
			i++;
		}
		return retValue;
	}
	
	/**
	 * Imposta i valori di una proprieta' di tipo stringa vettoriale.
	 *
	 * @param values i valori.
	 * @param delimiter il delimitatore dei valori.
	 * @param properties la risorsa di configurazione.
	 * @param propertyName il nome della proprieta'.
	 */	
	public static void setPaths (Properties properties, String propertyName, String[] values, String delimiter){
		final StringBuffer value = new StringBuffer ();
		for (int i =0;i<values.length;i++){
			if (0!=i){
				value.append (File.pathSeparator);
			}
			value.append (values[i]);
		}
		properties.setProperty (propertyName, value.toString ());
	}
	
	
}
