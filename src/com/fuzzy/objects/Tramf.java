package com.fuzzy.objects;

/**
 * @author Paskalis Giannis
 * @dateCreated 16 Dec 2014
 */
public class Tramf extends MembersetFunction {
	public Tramf(String label, double a, double b, double c, double d)

	{
		super();
		this._label = label.trim();
		this.parameters().add(a);
		this.parameters().add(b);
		this.parameters().add(c);
		this.parameters().add(d);
		this._range.add(this.parameters().get(0));
		this._range.add((double) this.parameters().size() - 1);

		this.calc_cog();
		this.calc_spread();
	}

	@Override
	public double calc_grade(double value) {
		if ((value >= this.parameters().get(0) && (value <= this.parameters()
				.get(1)))) {
			return (value - this.parameters().get(0))
					/ (this.parameters().get(1) - this.parameters().get(0));
		} else if ((value >= this.parameters().get(1))
				&& (value <= this.parameters().get(2))) {
			return this.y_axis;
		} else if ((value >= this.parameters().get(2))
				&& (value <= this.parameters().get(3))) {
			return (this.parameters().get(3) - value)
					/ (this.parameters().get(3) - this.parameters().get(2));
		}
		return 0;
	}

}
