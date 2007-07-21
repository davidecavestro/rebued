/*
 * DefaultSerieNode.java
 *
 * Created on 1 marzo 2005, 0.41
 */

package com.davidecavestro.common.charts.awt;

/**
 * Implementazione di base per <CODE>SerieNode</CODE>.
 *
 * @author  davide
 */
public class DefaultSerieNode implements SerieNode {
	
	private String _name;
	private double _value;
	private SerieNode[] _children;
	private final static SerieNode[] noChildren = new SerieNode[0];
	
	private final Object _source;
	/**
	 * Costruttore senza figli.
	 */
	public DefaultSerieNode (String name, double value, final Object source) {
		this._name=name;
		this._value=value;
		this._children=noChildren;
		_source = source;
	}
	/** 
	 * Costruttore con figli 
	 */
	public DefaultSerieNode (String name, double value, SerieNode[] children, final Object source) {
		this._name=name;
		this._value=value;
		this._children=children;
		_source = source;
	}
	
	public SerieNode childAt (int param) {
		return this._children[param];
	}
	
	public int childrenLength () {
		return this._children.length;
	}
	
	private double _childrenValue=-1;
	public double getChildrenValue () {
		if (0>_childrenValue) {
			_childrenValue = 0;
			for (int i=0;i< this._children.length;i++){
				_childrenValue+=this._children[i].getTotalValue ();
			}
		}
		return _childrenValue;
	}
	
	public String getName () {
		return this._name;
	}
	
	public double getValue () {
		return this._value;
	}
	
	private double _totalValue=-1;
	public double getTotalValue () {
		if (0>_totalValue) {
			this._totalValue =this._value+this.getChildrenValue ();
		}
		return _totalValue;
	}
	
	public Object getSource () {
		return _source;
	}
	
	
	/**
	 * Ritorna una rappresentazione in formato stringa di questonodo.
	 *
	 * @return na stringa che rappresenta questo nodo.
	 */	
	public String toString (){
		final StringBuffer sb = new StringBuffer ();
		sb.append ("name: ").append (this._name)
		.append (" value: ").append (this.getValue ())
		.append (" childrenValue: ").append (this.getChildrenValue ())
		.append (" childrenLength: ").append (this.childrenLength ())
		.append (" source: ").append (getSource ());
		return sb.toString ();
	}
}
