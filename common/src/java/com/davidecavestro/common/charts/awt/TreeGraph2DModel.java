/*
 * Interface.java
 *
 * Created on 28 febbraio 2005, 21.28
 */

package com.davidecavestro.common.charts.awt;

import JSci.awt.*;

/**
* This is a generic interface for sending data to 2D category graphs.
 *
 * @author  davide
 */
public interface TreeGraph2DModel {
        /**
        * Add a listener.
        */
        void addGraphDataListener(GraphDataListener l);
        /**
        * Remove a listener.
        */
        void removeGraphDataListener(GraphDataListener l);
		
		/**
		 * Ritorna la radice.
		 */
		SerieNode getRoot ();
		void setRoot (SerieNode root);
}


