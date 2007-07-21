/*
 * SerieNode.java
 *
 * Created on 28 febbraio 2005, 22.24
 */

package com.davidecavestro.common.charts.awt;

/**
 *
 * @author  davide
 */
public interface SerieNode {
	
        /**
        * Returns the node name.
        */
        String getName();
        /**
        * Returns the node local value.
        */
        double getValue();
        /**
        * Returns the number of children.
        */
        int childrenLength();
        /**
        * Returns the total children subtree value.
        */
        double getChildrenValue();
        /**
        * Returns the child at index <tt>i</tt>.
        */
        SerieNode childAt(int i);
		/**
		 * Ritorna il valore totale del nodo (locale + sottoalbero).
		 */
		double getTotalValue ();
		
		/**
		 * Ritorna l'oggettosorgente dei dati del nodo della serie.
		 *
		 * Questo oggetto pu&ograve; essere utilizzatoper ulteriori elaborazioni o mappature..
		 */
		Object getSource ();
}
