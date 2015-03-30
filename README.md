# FLC-Lib
Fuzzy Logic Controller library.

This is a standart fuzzy controller library, currently in beta version. Supports COG (Center Of Gravity) defuzzification along with logical AND and OR implications.

Following is a example of how to appropriately use the library
  ```
  /* initialize the library*/
	Fuzzy fuzz = new Fuzzy();
  ```
  Initialize the Linguistic Variables by specifying, Name,Type and Range.
```
		fuzz.add_input("Density", "input", "0, 35");
		fuzz.add_input("Speed", "input", "0, 65");
		fuzz.add_input("Steer", "output", "-180, 180");
```
  Moving on we assign the desired memberset functions for each Linguistic Variable by specifying the type of the
  input, input name, the name of the memberset and its range.
  The range can be defined either by giving three values (Trimf - Triangle) seperated with comma (,) or four 
  (Trapezoid - Tramf)
  
```
		fuzz.add_memberset("input", "Density", "low", "-10, 0, 10, 15");
		fuzz.add_memberset("input", "Density", "mid", "10, 15, 25,30");
		fuzz.add_memberset("input", "Density", "high", "25, 30, 35, 40");
		fuzz.add_memberset("input", "Speed", "low", "-10, 0, 20, 41");
		fuzz.add_memberset("input", "Speed", "mid", "20, 41, 60");
		fuzz.add_memberset("input", "Speed", "high", "41, 60, 65, 70");
		fuzz.add_memberset("output", "Steer", "right", "10, 20, 30, 40");
		fuzz.add_memberset("output", "Steer", "zero", "40, 50, 60, 70");
		fuzz.add_memberset("output", "Steer", "left", "70, 80, 90, 100");
```
Finally we have to define the rules for our system. They are defined as simple strings in the format shown
	below.
	(IF "Input name" is "memberset name" <and>.... THEN "input name" is "memberset name").
	
```
		fuzz.add_rule("IF Density is low And Speed is high Then Steer is zero");
		fuzz.add_rule("IF Density is low And Speed is mid Then Steer is left");
		fuzz.add_rule("IF Density is low And Speed is low Then Steer is left");
		fuzz.add_rule("IF Density is mid And Speed is low Then Steer is left");
		fuzz.add_rule("IF Density is mid And Speed is high Then Steer is right");
		fuzz.add_rule("IF Density is high And Speed is low Then Steer is right");
		fuzz.add_rule("IF Density is high And Speed is high Then Steer is right");
```
In order to retrieve the crisp values generated by the controller we must call the calc_results() method
        which takes   a string as an argument. The string must contains the values for each input defined
        seperated with a comma (,).
        
```
		for(Double d : fuzz.calc_results("10,20"))
		{
			System.out.format("The output is %f%n",d);
		}
```
Subsequently, to retrieve the triggered rules, we should call the active_rules() method which returns a list
	of strings containing the triggered rules.
	
```
		for (String s : fuzz.active_rules())
		{
			System.out.println(s);
		}
```
