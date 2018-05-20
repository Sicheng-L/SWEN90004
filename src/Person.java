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
	
	private final int LIFE_EXPECTANCY;
	private final int METABOLISM;
	private final int VISION;
	
	private boolean isDead = false;
	
	
	public Person() {
		this.age = 0;
		this.LIFE_EXPECTANCY = Params.randomLifeExpectancy();
		this.METABOLISM = Params.randomMetabolism();
		this.wealth = Params.randomWealth(this.METABOLISM);
		this.VISION = Params.randomVision();
	}
	
	/*
	 * Get the class of the person
	 */
	public String getClass(int maxWealth) {
		if(wealth <= maxWealth / 3.0) {
			return "Low";
		} else if (wealth <= ((double)maxWealth * 2.0 / 3.0)){
			return "Middle";
		} else {
			return "High";
		}
	}
	
	/*
	 * Get the wealth of the person
	 */
	public int getWealth() {
		return wealth;
	}
	
	/*
	 * Set the wealth of the person
	 */
	public void setWealth(int newWealth) {
		this.wealth = newWealth;
	}
	
	/*
	 * Get the view of the person
	 */
	public int getView() {
		return VISION;
	}
	
	/*
	 * Eat action
	 */
	public void eat() {
		if(wealth > METABOLISM) {
			wealth -= METABOLISM;
			age();
		} else {
			die();
		}
	}
	
	/*
	 * Age action
	 */
	public void age() {
		if(age < LIFE_EXPECTANCY) {
			age ++;
		} else {
			die();
		}
	}
	
	/*
	 * Die action
	 * re-initialize the person
	 */
	public void die() {
		isDead = true;
	}
	
	/*
	 * return whether the person 
	 */
	public boolean isDead() {
		return isDead;
	}

}
