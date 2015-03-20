package com.fuzzy.engine.objects;

import java.util.ArrayList;
import java.util.List;

import com.fuzzy.objects.MembersetFunction;

/**
 * @author Paskalis Giannis
 * @dateCreated 16 Dec 2014
 * @info Definition of Linguistic Variable
 */
public class LinguisticVariable {

	private String _label;
	private List<Double> _range;
	private List<MembersetFunction> _funcs;
	private Vartype _type;

	public LinguisticVariable(String name, Vartype type) {
		this._label = name;
		this._range = new ArrayList<Double>(2);
		this._funcs = new ArrayList<MembersetFunction>();
		this._type = type;
	}

	public String get_name() {
		return _label;
	}

	public void set_name(String value) {
		this._label = value;
	}

	public List<Double> range() {
		return this._range;
	}

	public List<MembersetFunction> memberset_function() {
		return this._funcs;
	}

	public void set_range(double start, double end) {
		this._range.add(start);
		this._range.add(end);
	}

	public MembersetFunction get_memf_byname(String name) {
		for (MembersetFunction f : this._funcs) {
			if (f.get_label().equals(name)) {
				return f;
			}
		}
		return null;
	}

	public void add_memfunc(MembersetFunction mf) {
		if (mf != null) {
			_funcs.add(mf);
		}
	}

	public boolean remove_memfunc(MembersetFunction mf) {
		if (mf != null) {
			return _funcs.remove(mf);
		}
		return false;
	}

	@Override
	public String toString() {
		return this._label;

	}

	public enum Vartype {
		INPUT(0), OUTPUT(1);
		private final int val;

		private Vartype(final int val) {
			this.val = val;
		}
	}
}
