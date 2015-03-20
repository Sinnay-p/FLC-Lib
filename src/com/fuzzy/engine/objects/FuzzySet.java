package com.fuzzy.engine.objects;

import java.util.ArrayList;
import java.util.List;

import com.fuzzy.objects.MembersetFunction;

/**
 * @author Paskalis Giannis
 * @dateCreated 16 Dec 2014
 */
public class FuzzySet {

	private List<FuzzyNumbers> _nums;
	private String _ling_var;

	public FuzzySet(List<FuzzyNumbers> num, String ling_var) {
		this._nums = num;
		this._ling_var = ling_var.trim();
	}

	public FuzzySet(String ling_var) {
		this._ling_var = ling_var;
		this._nums = new ArrayList<FuzzyNumbers>();
	}

	public List<FuzzyNumbers> fuzzy_numbers() {
		return this._nums;
	}

	public String get_linguistic_var() {
		return this._ling_var.trim();
	}

	public void set_linguistic_var(String ling) {
		this._ling_var = ling.trim();
	}

	public FuzzyNumbers search_by_memval(String value) {
		for(FuzzyNumbers fn : _nums)
		{
			if(fn.get_memberset().compareToIgnoreCase(value)==0)
			  return fn;
		}
		return null;
	}
}
