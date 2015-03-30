package com.fuzzy.objects;

/**
 * @author Paskalis Giannis (J.Paskal@coding-labs.eu)
 * @dateCreated 15 Dec 2014
 * @lastRevision 18 Mar 2015 12:00
 * @info Triangular Memberset Function.
 */
public class Trimf extends MembersetFunction {

	 public Trimf(String label, double a, double b, double c)
     	
 {
     super();
     this._label = label.trim();
     this.parameters().add(a); 
     this.parameters().add(b); 
     this.parameters().add(c);
     this._range.add(this.parameters().get(0));
     this._range.add((double)this.parameters().size() - 1);

     this.calc_cog();
     this.calc_spread();
 }

	@Override
	public double calc_grade(double value) {
		 if (value >= this.parameters().get(0) && value <= this.parameters().get(1))
	     {
	         return (value - this.parameters().get(0) / (this.parameters().get(1) - this.parameters().get(0)));
	     }
	     if (value >= this.parameters().get(1) && value <= this.parameters().get(2))
	     {
	         return (this.parameters().get(2) - value) / (this.parameters().get(2) - this.parameters().get(1));
	     }
	     return 0;
	}
	

}
