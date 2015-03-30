package com.fuzzy.engine.rule;

/**
 * @author Paskalis Giannis (J.Paskal@coding-labs.eu)
 * @dateCreated 16 Dec 2014
 * @lastRevision 18 Mar 2015 12:00
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
