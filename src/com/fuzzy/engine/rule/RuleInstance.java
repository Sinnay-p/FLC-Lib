package com.fuzzy.engine.rule;

/**
 * @author Paskalis Giannis
 * @dateCreated 16 Dec 2014
 * @info in this class we define a single rule item - instance
 */

public class RuleInstance {

	private String _var;
	private String _memberset;

	/**
	 * 
	 * @param Variable Name
	 * @param Memberset Name
	 */
	public RuleInstance(String var, String mem) {
		this._var = var;
		this._memberset = mem;
	}

	public String get_var() {
		return this._var.trim();
	}

	public String get_memberset_val() {
		return this._memberset.trim();
	}

}
