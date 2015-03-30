package com.fuzzy.objects;

import java.util.ArrayList;

/**
 *@author Paskalis Giannis (J.Paskal@coding-labs.eu)
 * @dateCreated 15 Dec 2014
 * @lastRevision 18 Mar 2015 12:00
 * @info Changed from A LinkedHashMap<String, ArrayList<Double> to  an abstract
 *       object
 *       **/
public abstract class MembersetFunction {

	protected String _label;
	protected ArrayList<Double> _points = new ArrayList<Double>();
	protected double _spread;
	protected double y_axis = 1;
	protected double _centerofmass;
	protected ArrayList<Double> _range = new ArrayList<Double>();

	/**
	 * @param String
	 *            {Set Label of memberset function}
	 */

	public void set_label(String lbl) {
		this._label = lbl;
	}

	public String get_label() {
		return this._label;
	}

	public ArrayList<Double> points() {
		return this._points;
	}
	
	public ArrayList<Double> range() {
		return this._range;
	}

	public double center_of_mas() {
		return this._centerofmass;
	}

	public double spread() {
		return this._spread;
	}

	public ArrayList<Double> parameters() {
		return this._points;
	}


	public double get_y_axis() {
		return y_axis;
	}

	public void set_y_axis(double vl) {
		this.y_axis = vl;
	}


	/**
	 * TODO: Change it to protected so it cannot be accessed outside
	 * the package.
	 */
    protected void calc_cog()
    {
        double stepN = 20;
        double start = this._range.get(0);
        double end = this._range.get(1);
        double step = (end - start) / (stepN - 1);
        double uppersum = 0;
        double lowersum = 0;

            for (double j = start; j < end; j = step + j)
            {
                double value = this.calc_grade (j);
                if (value > 0)
                {
                        uppersum = value * j + uppersum;
                        lowersum = value + lowersum;
                }
            }
          _centerofmass = (uppersum / lowersum);
      }
    
    protected void calc_spread()
    {
        _spread = this._range.get(1) - _range.get(0)-1;
    }
	
    @Override
	public String toString()
	{
		return this.get_label();
	}
	public abstract double calc_grade (double x_axis_val);

}
