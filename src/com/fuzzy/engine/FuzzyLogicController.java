package com.fuzzy.engine;

import java.util.ArrayList;
import java.util.List;

import com.fuzzy.engine.Settings.Defuzzification;
import com.fuzzy.engine.Settings.Implication;
import com.fuzzy.engine.objects.FuzzyNumbers;
import com.fuzzy.engine.objects.FuzzySet;
import com.fuzzy.engine.objects.LinguisticVariable;
import com.fuzzy.objects.MembersetFunction;

/**
 * @author Paskalis Giannis
 * @dateCreated 16 Dec 2014
 */
public class FuzzyLogicController {

	Settings _settings;

	public FuzzyLogicController(Settings set) {
		_settings = set;
	}

	private double Centroid(List<FuzzyNumbers> nums, LinguisticVariable variable) {
		double stepN = 17;
		double start = variable.range().get(0);
		double end = variable.range().get(1);
		double step = (end - start) / (stepN - 1);
		double uppersum = 0;
		double lowersum = 0;

		for (int i = 0; i < nums.size(); i++) {
			MembersetFunction mf = variable.get_memf_byname(nums.get(i)
					.get_memberset());
			for (double j = start; j < end; j = step + j) {
				double value = mf.calc_grade(j);
				if (value > 0) {
					if (value < nums.get(i).get_fuzzy_value()) {
						uppersum = value * j + uppersum;
						lowersum = value + lowersum;
					} else {
						uppersum = nums.get(i).get_fuzzy_value() * j + uppersum;
						lowersum = nums.get(i).get_fuzzy_value() + lowersum;
					}
				}
			}
		}
		return (uppersum / lowersum);
	}

	private double get_implication(double value1, double value2) {
		if (this._settings.implication() == Implication.MIN.value) {
			if (value1 < value2) {
				return value1;
			} else {
				return value2;
			}
		} else if (this._settings.implication() == Implication.PROD.value) {
			return value1 * value2;
		}
		return 0;
	}
	
	public List<FuzzyNumbers> Fuzzification(double value, LinguisticVariable variable)
    {
        List<FuzzyNumbers> set = new ArrayList<FuzzyNumbers>();
        List<MembersetFunction> mfs = variable.memberset_function();
        for (int i = 0; i < mfs.size(); i++)
        {
            double v = mfs.get(i).calc_grade(value);
            if (v > 0)
            {
                set.add(new FuzzyNumbers(mfs.get(i).get_label(), v));
            }
        }
        return set;
    }
	
	
	 public double DeFuzzification(List<FuzzySet> sets, LinguisticVariable variable)
     {
         double value = 0;
         for (int i = 0; i < sets.size(); i++)
         {
        	 String n = sets.get(i).get_linguistic_var();
             if (sets.get(i).get_linguistic_var().trim().compareToIgnoreCase(variable.get_name()) == 0)
             {
                 if (this._settings.defuzzification() == Defuzzification.MODHEIGHT.value)
                 {
                     //value = MaxHeightDeFuzzifcation(sets.get(i).fuzzy_numbers(), variable);
                 }
                 else if (this._settings.defuzzification() == Defuzzification.CENTROID.value)
                 {
                     value = Centroid(sets.get(i).fuzzy_numbers(), variable);
                 }
             }
         }
         return value;
     }

}
