/**
 * 
 * @author John Paskalis
 * @datetime 10/12/2014 : 12:43
 * @revision 0.01
 * 
 * 
 * 
 */
package com.fuzzy.objects;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Membersets extends LinkedHashMap <String, ArrayList<Double>> {
	
	private LinkedHashMap <String, Double> _grades;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7115541059128540216L;
	/**
	 * 
	 */

	public Membersets()
	{
		_grades = new LinkedHashMap <String, Double>();
	}

	public void set_grades(String key, double value){
		this._grades.put(key, value);
	}
	public LinkedHashMap <String, Double> get_grades(){
		return this._grades;
	}



}
