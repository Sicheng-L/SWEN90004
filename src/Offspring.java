/**
 * Extension: Instead of getting a random wealth when born, a person will get
 * a percentage of wealth of his parent as the initial wealth
 * 
 * @author: JIACHENG
 */
public class Offspring extends Person{
    private int age;
    private double wealth;

    private int LIFE_EXPECTANCY;
    private int METABOLISM;
    private int VISION;
    
    private boolean isDead;


    public Offspring(int legacy) {
        this.age = 0;
        this.LIFE_EXPECTANCY = Params.randomLifeExpectancy();
        this.METABOLISM = Params.randomMetabolism();
        this.wealth = Params.inheritWealth(this.METABOLISM, legacy);
        this.VISION = Params.randomVision();
        this.isDead = false;
    }
}
