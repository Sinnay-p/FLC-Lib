package com.fuzzy.engine.rule;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.fuzzy.engine.Settings.Operator;

/**
 * @author Paskalis Giannis
 * @dateCreated 16 Dec 2014
 * @info this is the Rule Engine where each Rule instance is used
 */
public class RuleEngine {

	private List<RuleInstance> _input_rule;
	private List<RuleInstance> _output_rule;
	private Connector _con;

	public RuleEngine(List<RuleInstance> inpt, List<RuleInstance> outpt,
			Connector con) {
		this._input_rule = inpt;
		this._output_rule = outpt;
		this._con = con;
	}

	public RuleEngine() {
		this._input_rule = new ArrayList<RuleInstance>();
		this._output_rule = new ArrayList<RuleInstance>();
		this._con = Connector.AND;
	}

	public List<RuleInstance> input_rules() {
		return this._input_rule;
	}

	public List<RuleInstance> output_rules() {
		return this._output_rule;
	}

	public void add_input_rule(RuleInstance rl) {
		this._input_rule.add(rl);
	}

	public void add_output_rule(RuleInstance rl) {
		this._output_rule.add(rl);
	}

	/**
	 * Override the default toString method so we get an automatic rule
	 * representation
	 */
	@Override
	public String toString() {
		String output = "IF ";

		for (java.util.Iterator<RuleInstance> itr = this._input_rule.iterator(); itr
				.hasNext();) {
			RuleInstance r = itr.next();
			output += r.get_var() + " is "
					+ r.get_memberset_val();
			if (itr.hasNext()) {
				output += " And ";
			}
		}
		output += " Then ";
		for (java.util.Iterator<RuleInstance> itr = this._output_rule
				.iterator(); itr.hasNext();) {
			RuleInstance r = itr.next();
			output += r.get_var() + " is "
					+ r.get_memberset_val();
			if (itr.hasNext()) {
				output += " And ";
			}
		}
		return output;

	}

	public enum Connector {
		AND(0), OR(1);
		private final int val;

		private Connector(final int val) {
			this.val = val;
		}
	}

}
