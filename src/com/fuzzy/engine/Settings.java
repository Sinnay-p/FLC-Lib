package com.fuzzy.engine;

/**
 * @author Paskalis Giannis (J.Paskal@coding-labs.eu)
 * @dateCreated 16 Dec 2014
 * @lastRevision 18 Mar 2015 12:00
 */
public class Settings {

	private Implication _implication;
	private Operator _operator;
	private Defuzzification _defuz;

	public Settings(Implication imp, Operator op) {
		this._implication = imp;
		this._operator = op;
	}

	public int implication() {
		return this._implication.value;
	}

	public void set_implication(Implication val) {
		this._implication = val;
	}

	public int operator() {
		return this._operator.value;
	}

	public void set_operator(Operator val) {
		this._operator = val;
	}

	public int defuzzification() {
		return this._defuz.value;
	}

	public void set_defuzzification(Defuzzification val) {
		this._defuz = val;
	}

	public enum Defuzzification {
		CENTROID(0), BISECTOR(1), MODHEIGHT(2);

		public final int value;

		Defuzzification(final int val) {
			this.value = val;
		}
	}

	public enum Operator {
		MIN(0), PROD(1);

		public final int value;

		Operator(final int val) {
			this.value = val;
		}
	}

	public enum Implication {
		MIN(0), PROD(1);

		public final int value;

		Implication(final int val) {
			this.value = val;
		}
	}

}
