/**
 * Class to represent the 'person'
 * age - the current age (measured in ticks) of the person
 * wealth - the number of grains this person holds
 * 
 * @author SICHENG
 *
 */
public class Person {
	
	private int age;
	private int wealth;
	
	private final int LIFEEXPECTANCY;
	
	public Person(int lifeExpectancy) {
		this.age = 0;
		this.wealth = 0;
		this.LIFEEXPECTANCY = lifeExpectancy;
	}

}
