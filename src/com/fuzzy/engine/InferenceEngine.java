package com.fuzzy.engine;

import java.util.ArrayList;
import java.util.List;

import com.fuzzy.engine.Settings.Implication;
import com.fuzzy.engine.objects.FuzzyNumbers;
import com.fuzzy.engine.objects.FuzzySet;
import com.fuzzy.engine.objects.LinguisticVariable;
import com.fuzzy.engine.rule.RuleEngine;
import com.fuzzy.engine.rule.RuleInstance;

/**
 * @author Paskalis Giannis
 * @dateCreated 16 Dec 2014
 */
public class InferenceEngine {

	List<RuleEngine> _rules;
	List<FuzzySet> _sets;
	Settings _settings;
	List<Integer> _fired_rules;

	public InferenceEngine(Settings set, List<RuleEngine> rules,
			List<FuzzySet> sets) {
		this._rules = rules;
		this._sets = sets;
		this._settings = set;
		this._fired_rules = new ArrayList<Integer>();
	}

	public List<Integer> fired_rules() {
		return this._fired_rules;
	}

	private FuzzySet get_var_inset(String var) {
		for(FuzzySet vr :_sets)
		{
			if (vr.get_linguistic_var().compareToIgnoreCase(var)==0)
				return vr;

		}
		return null;
	}

	private double get_min(List<Double> values) {
		double min = values.get(0);
		for (int i = 1; i < values.size(); i++) {
			if (values.get(i) < min) {
				min = values.get(i);
			}
		}
		return min;
	}

	private double get_prod(List<Double> values) {
		double max = 1;
		for (int i = 0; i < values.size(); i++) {
			max = values.get(i) * max;
		}
		return max;
	}

	private double implication(double a, double b) {
		return (a > b) ? a : b;
	}

	private double logic(List<Double> pts) {
		if (this._settings.operator() == Implication.MIN.value) {
			return this.get_min(pts);
		} else if (this._settings.operator() == Implication.PROD.value) {
			return this.get_prod(pts);
		}
		return 0;
	}

	private double get_rule_val(RuleEngine rule) {
		List<Double> rule_values = new ArrayList<Double>();

		List<RuleInstance> ins = rule.input_rules();

		for (int i = 0; i < ins.size(); i++) {
			String t = ins.get(i).get_var();
			FuzzySet set = this.get_var_inset(ins.get(i).get_var());
			if (set != null) {
				FuzzyNumbers num = set.search_by_memval(ins.get(i).get_memberset_val());
				if (num != null) {
					rule_values.add(num.get_fuzzy_value());
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		}
		return logic(rule_values);
	}

	public List<FuzzySet> evaluate_rules() {
		List<FuzzySet> output_sets = new ArrayList<FuzzySet>();

		for (int i = 0; i < _rules.size(); i++) {
			List<RuleInstance> outrules = this._rules.get(i).output_rules();
			double firing_strength = this.get_rule_val(_rules.get(i));
			if (firing_strength > 0) {
				this._fired_rules.add(i + 1);
				for (int j = 0; j < outrules.size(); j++) {
					String var = outrules.get(j).get_var();
					if (output_sets.contains(var)) {
						int index = output_sets.indexOf(var);
						String mem = outrules.get(j).get_memberset_val();
						if (output_sets.get(index).fuzzy_numbers()
								.contains(mem)) {
							int index2 = output_sets.get(index).fuzzy_numbers()
									.indexOf(mem);
							output_sets.get(index).fuzzy_numbers().get(index2).set_fuzzy_value(implication(output_sets.get(index)
													.fuzzy_numbers()
													.get(index2)
													.get_fuzzy_value(),
													firing_strength));
						} else {
							FuzzyNumbers num = new FuzzyNumbers(mem,
									firing_strength);
							output_sets.get(index).fuzzy_numbers().add(num);
						}
					} else {
						FuzzySet newset = new FuzzySet(var);
						newset.fuzzy_numbers().add(
								new FuzzyNumbers(outrules.get(j)
										.get_memberset_val(), firing_strength));
						output_sets.add(newset);
					}
				}
			}
		}
		return output_sets;

	}
}
