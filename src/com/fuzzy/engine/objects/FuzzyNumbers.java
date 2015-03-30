package com.fuzzy.engine.objects;

/**
 * @author Paskalis Giannis (J.Paskal@coding-labs.eu)
 * @dateCreated 16 Dec 2014
 * @lastRevision 18 Mar 2015 12:00
 */
public class FuzzyNumbers {
	
	 private String _memberset;
     double _fuzzyout;

     public FuzzyNumbers(String memberset_name, double value)
     {
         this._memberset = memberset_name.trim();
         _fuzzyout = value;
     }

     public String get_memberset()
     {
         return this._memberset.trim();
     }

     public double get_fuzzy_value()
     {
        return this._fuzzyout;
     }
     public void set_fuzzy_value(double val)
     {
        this._fuzzyout = val;
     }

     @Override
     public String toString()
     {
         return this._memberset + " ---> " + String.valueOf(this.get_fuzzy_value());
     }

}
