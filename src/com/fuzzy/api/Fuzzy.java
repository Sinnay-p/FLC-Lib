package com.fuzzy.api;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

import com.fuzzy.engine.FuzzyLogicController;
import com.fuzzy.engine.InferenceEngine;
import com.fuzzy.engine.Settings;
import com.fuzzy.engine.Settings.Defuzzification;
import com.fuzzy.engine.Settings.Implication;
import com.fuzzy.engine.Settings.Operator;
import com.fuzzy.engine.objects.FuzzySet;
import com.fuzzy.engine.objects.LinguisticVariable;
import com.fuzzy.engine.rule.RuleEngine;
import com.fuzzy.engine.rule.RuleInstance;
import com.fuzzy.exceptions.FuzzyLibraryException;
import com.fuzzy.objects.MembersetFunction;
import com.fuzzy.objects.Tramf;
import com.fuzzy.objects.Trimf;

/**
 * @author Paskalis Giannis (j.paskal@coding-labs.eu)
 *@dateCreated 18 Mar 2015
 *@lastRevision 18 Mar 2015 12:00
 * This is the api's wrapper class. It performs all necessary actions regarding the initialization and trigger,
 * of the fuzzy system.
 */
public class Fuzzy {
	private List<com.fuzzy.engine.objects.LinguisticVariable> input_vars;
	private List<com.fuzzy.engine.objects.LinguisticVariable> output_vars;
	private List<com.fuzzy.engine.rule.RuleEngine> rules;
	private Settings settings;
	private RuleEngine rule_engine;
	private List<Integer> triggered_rules;
	
	public Fuzzy()
	{
		input_vars = new ArrayList<com.fuzzy.engine.objects.LinguisticVariable>();
		output_vars = new ArrayList<com.fuzzy.engine.objects.LinguisticVariable>();
		rules = new ArrayList<com.fuzzy.engine.rule.RuleEngine>();
		settings = new Settings(Implication.MIN,Operator.MIN);
		settings.set_defuzzification(Defuzzification.CENTROID);
	}
	
	
	/**
	 * This method adds a new input and stores it at the Input_vars Array List.
	 * @param name Input name;
	 * @param type Input Type (input,output).
	 * @param range Input's range separated with comma (,). 
	 */
	public void add_input(String name, String type,String range)
	{
		String[] tmp = range.split(",");

		ArrayList<Double> vlr = new ArrayList<Double>();
		try {
			for (String t : tmp) {
				vlr.add(Double.parseDouble(t));
			}
		} catch (NumberFormatException e) {
			System.out.println("Error Variable Range is not correct. Please Use only digits");
		}
		LinguisticVariable var = new LinguisticVariable(name,(type.toLowerCase()=="input" ? LinguisticVariable.Vartype.INPUT:LinguisticVariable.Vartype.OUTPUT));
		var.range().add(vlr.get(0));
		var.range().add(vlr.get(1));
		if(type.toLowerCase()=="input")
		{
			this.input_vars.add(var);
		}
		else if(type.toLowerCase()=="output")
		{
			this.output_vars.add(var);
		}
		else
		{
			throw new FuzzyLibraryException("No such output type. Please specify the correct output type (input or output)");
		}
	}
	

	/**
	 *  This methods adds a membersetfunction into the given input.
	 * @param type The Variable type (input,output).
	 * @param input The input in which the memberset should be inserted.
	 * @param memset_name The membersets name.
	 * @param points The memberset points, separated with comma (,)
	 * 
	 */
	public void add_memberset(String type, String input,String memset_name,String points)
	{	
		MembersetFunction mf;
		String[] tmp = points.split(",");

			ArrayList<Double> vlr = new ArrayList<Double>();
			try {
				for (String t : tmp) {
					vlr.add(Double.parseDouble(t));
				}
			} catch (NumberFormatException e) {
				throw new FuzzyLibraryException("Error Memberset Range is not correct. Please Use only digits");
			}
			
			if(vlr.size() == 3)
			{
			   mf = new Trimf(memset_name,vlr.get(0),vlr.get(1),vlr.get(2));
			}
			else if(vlr.size()==4)
			{
				mf = new Tramf(memset_name,vlr.get(0),vlr.get(1),vlr.get(2),vlr.get(3));
			}
			else
			{
				throw new FuzzyLibraryException("Memberset Function length is wrong.");
			}
		
			
		if(type.trim().compareToIgnoreCase("input") == 0)
		{
			for(int i=0;i<this.input_vars.size();i++)
			{
				if(this.input_vars.get(i).get_name().compareToIgnoreCase(input.trim())==0)
					   this.input_vars.get(i).add_memfunc(mf);
			}
		}
		else if(type.trim().compareToIgnoreCase("output") == 0)
		{
			for(int i=0;i<this.output_vars.size();i++)
			{
				if(this.output_vars.get(i).get_name().compareToIgnoreCase(input.trim())==0)
					this.output_vars.get(i).add_memfunc(mf);
			}
		}
		else{
			throw new FuzzyLibraryException("Variable type does not exist");
		}
	}
	
	/**
	 * Returns an ArrayList with the specified linguistic variables.
	 * @param type Linguistic variable type to be returned.
	 * @return List of linguistic variables.
	 */
	public List<LinguisticVariable> get_linguistic_vars(String type)
	{
		return type.toLowerCase()=="input" ? this.input_vars : this.output_vars; 
	}
	
	
	/**
	 * This method takes a String argument which is the rule. 
	 * The rule should be in the following format.
	 * <i> IF temperature is LOW and humidity HIGH then AIRCONDITION is on</i>
	 * @param rule
	 */
	public void add_rule(String rule) {
		String[] split = rule.toLowerCase().split("then");
		String[] inpt = split[0].replace("if", "").split("and");
		String[] outpt = split[1].split("and");
		List<RuleInstance> inpt_rule = new ArrayList<RuleInstance>();
		List<RuleInstance> outpt_rule = new ArrayList<RuleInstance>();
		for (String e : inpt) {
				String[] tmp = e.split("is");
				inpt_rule.add(new RuleInstance(tmp[0], tmp[1]));
			}
		for (String e : outpt) {
			String[] tmp = e.split("is");
			outpt_rule.add(new RuleInstance(tmp[0], tmp[1]));
		}
		this.rules.add(new RuleEngine(inpt_rule, outpt_rule,
				RuleEngine.Connector.AND));
	}
	
	/**
	 * Method that calculates the crisp result, practically performs the defuzzification.
	 * @param values The input values.
	 * @return List<Double> Returns a list with the output values.
	 */
	public List<Double>  calc_results(String values) {
	    List<Double> crisp_values = new ArrayList<Double>();
		FuzzyLogicController fuzzy_controller = new FuzzyLogicController(
				this.settings);

		int inpt_num = this.input_vars.size();
		String[] val = values.split(",");
		if (val.length == inpt_num) {
			ArrayList<Double> conv_val = new ArrayList<Double>();
			List<FuzzySet> input_sets = new ArrayList<FuzzySet>();
			try {
				for (String v : val) {
					conv_val.add(Double.parseDouble(v));
				}
				for (int i = 0; i < this.input_vars.size(); i++) {
					input_sets.add(new FuzzySet(fuzzy_controller.Fuzzification(
							conv_val.get(i), this.input_vars.get(i)),
							this.input_vars.get(i).get_name()));
				}

				InferenceEngine engine = new InferenceEngine(this.settings,
						this.rules, input_sets);
				List<FuzzySet> rule_results = engine.evaluate_rules();
				
				triggered_rules = engine.fired_rules();
		    for (LinguisticVariable var :this.output_vars)
		    {
		    	crisp_values.add(fuzzy_controller.DeFuzzification(rule_results, var));
		    }
		    
			} catch (NumberFormatException e) {
				throw new FuzzyLibraryException("Incorrect values. Separate values with comma(,)");
			}
		} else {
			throw new FuzzyLibraryException("You have " + inpt_num
					+ " inputs you should supply the same amount of values!");
		}
		return crisp_values;
	}
	
	public List<String> active_rules()
	{
		ArrayList<String> trig_rl = new ArrayList<String>();
		for(int i=0;i<this.triggered_rules.size();i++)
		{
			trig_rl.add("Rule "+i+": "+this.rules.get(this.triggered_rules.get(i)-1).toString());
		}
		return trig_rl;
	}
	

}
