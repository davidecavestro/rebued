/*
 * JTreeGraph2D.java
 *
 * Created on 28 febbraio 2005, 21.26
 */

package com.davidecavestro.common.charts.swing;


	
import JSci.swing.*;
import java.awt.*;
import JSci.awt.*;
import com.davidecavestro.common.charts.awt.TreeGraph2DModel;

/**
* The JTreeGraph2D superclass provides an abstract encapsulation
* of 2D tree graphs.
 *
 * @author  davide
*/
public abstract class JTreeGraph2D extends JDoubleBufferedComponent implements GraphDataListener {

	        /**
        * Data model.
        */
        protected TreeGraph2DModel model;
        /**
        * Origin.
        */
        protected Point origin=new Point();
        /**
        * Padding.
        */
        protected final int scalePad=5;
        protected final int axisPad=25;
        protected int leftAxisPad;
        /**
        * Constructs a 2D category graph.
        */
        public JTreeGraph2D(TreeGraph2DModel cgm) {
                model=cgm;
                model.addGraphDataListener(this);
        }
        /**
        * Sets the data plotted by this graph to the specified data.
        */
        public final void setModel(TreeGraph2DModel cgm) {
                model.removeGraphDataListener(this);
                model=cgm;
                model.addGraphDataListener(this);
                dataChanged(new GraphDataEvent(model));
        }
        /**
        * Returns the model used by this graph.
        */
        public final TreeGraph2DModel getModel() {
                return model;
        }
        public abstract void dataChanged(GraphDataEvent e);
        /**
        * Returns the preferred size of this component.
        */
        public Dimension getPreferredSize() {
                return getMinimumSize();
        }
        /**
        * Returns the minimum size of this component.
        */
        public Dimension getMinimumSize() {
                return new Dimension(200,200);
        }
}


