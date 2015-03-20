package com.fuzzy.unitest;
import java.util.List;

import com.fuzzy.api.Fuzzy;
import com.fuzzy.engine.objects.LinguisticVariable;
import com.fuzzy.objects.MembersetFunction;
/**
 * @author Paskalis Giannis
 *@dateCreated 19 Mar 2015
 */
public class FuzzyLibraryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Fuzzy fuzz = new Fuzzy();
		
		/*
		 * Initialize the Linguistic Variables by specifying, Name,Type,Range.
		 */
		fuzz.add_input("Density", "input", "0, 35");
		fuzz.add_input("Speed", "input", "0, 65");
		fuzz.add_input("Steer", "output", "-180, 180");

		
		/*
		 * Add the memberset functions for each Variable
		 */
		fuzz.add_memberset("input", "Density", "low", "-10, 0, 10, 15");
		fuzz.add_memberset("input", "Density", "mid", "10, 15, 25,30");
		fuzz.add_memberset("input", "Density", "high", "25, 30, 35, 40");
		fuzz.add_memberset("input", "Speed", "low", "-10, 0, 20, 41");
		fuzz.add_memberset("input", "Speed", "mid", "20, 41, 60");
		fuzz.add_memberset("input", "Speed", "high", "41, 60, 65, 70");
		fuzz.add_memberset("output", "Steer", "right", "10, 20, 30, 40");
		fuzz.add_memberset("output", "Steer", "zero", "40, 50, 60, 70");
		fuzz.add_memberset("output", "Steer", "left", "70, 80, 90, 100");

		
		/*
		 * Define the rules
		 */
		fuzz.add_rule("IF Density is low And Speed is high Then Steer is zero");
		fuzz.add_rule("IF Density is low And Speed is mid Then Steer is left");
		fuzz.add_rule("IF Density is low And Speed is low Then Steer is left");
		fuzz.add_rule("IF Density is mid And Speed is low Then Steer is left");
		fuzz.add_rule("IF Density is mid And Speed is high Then Steer is right");
		fuzz.add_rule("IF Density is high And Speed is low Then Steer is right");
		fuzz.add_rule("IF Density is high And Speed is high Then Steer is right");


		/*
		 * Get the Crisp values. calc_results take a string as an argument separating
		 * input values with a comma (,). The string should contains values equal to the number of inputs.
		 */
		for(Double d : fuzz.calc_results("10,20"))
		{
			System.out.format("The output is %f%n",d);
		}
		System.out.println("Rules triggered--------------------");
		for (String s : fuzz.active_rules())
		{
			System.out.println(s);
		}
		

	}

}
