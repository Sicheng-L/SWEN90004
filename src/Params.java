import java.util.Random;

/**
 * Parameters that influence the behaviour of the system.
 * 
 * @author SICHENG
 *
 */

public class Params {
	
	// number of people on the land - range from 2-1000
	public final int NUM_PEOPLE;

	// max-vision of people - how far they can see
	// 4 directions instead of 8
	// range from  1-15
	public final int MAX_VISION;
	
	// max metabolism of people - the number of grain they eat at each tick
	public final int METABOLISM_MAX;
	
	// max life expectancy - the number of ticks one person can live
	public final int LIFE_EXPECTANCY_MAX;
	
	// min life expectancy
	public final int LIFE_EXPECTANCY_MIN;
	
	// percentage of best land - land that has 
	public final double PERCENT_BEST_LAND;
	
	// the maximum number of grains a patch can hold
	public static final int MAX_GRAIN = 50;
	
	// grain growth interval (measured in the number of ticks)
	public final int GRAIN_GROWTH_INTERVAL;
	
	// number of grain grown after every interval
	public final int NUM_GRAIN_GROWN;
	
	
	public Params(int num_people, int max_vision, int metabolism_max,
			int life_expectancy_max, int life_expectancy_min, 
			double percent_best_land, int grain_growth_interval, 
			int num_grain_grown) {
		this.NUM_PEOPLE = num_people;
		this.MAX_VISION = max_vision;
		this.METABOLISM_MAX = metabolism_max;
		this.LIFE_EXPECTANCY_MAX = life_expectancy_max;
		this.LIFE_EXPECTANCY_MIN = life_expectancy_min;
		this.PERCENT_BEST_LAND = percent_best_land;
		this.GRAIN_GROWTH_INTERVAL = grain_growth_interval;
		this.NUM_GRAIN_GROWN = num_grain_grown;
	}
	
	/*
	 * Get a random vision ranging from 1 to MAX_VISION (inclusive)
	 */
	public int randomVision() {
		Random random = new Random();
		return 1 + random.nextInt(MAX_VISION);
	}
	
	/*
	 * Get a random metabolism ranging from 1 to METABOLISM_MAX (inclusive)
	 */
	public int randomMetabolism() {
		Random random = new Random();
		return 1 + random.nextInt(METABOLISM_MAX);
	}
	
	/*
	 * Get a random initial wealth ranging from metabolism to 
	 * metabolism + 50 (inclusive)
	 */
	public int randomWealth(int metabolism) {
		Random random = new Random();
		return metabolism + random.nextInt(50);
	}
	
	/*
	 * Get a random life expectancy ranging from LIFE_EXPECTANCY_MIN to 
	 * LIFE_EXPECTANCY_MAX (inclusive)
	 */
	public int randomLifeExpectancy() {
		Random random = new Random();
		return LIFE_EXPECTANCY_MIN + random.nextInt(LIFE_EXPECTANCY_MAX - 
				LIFE_EXPECTANCY_MIN);
	}
	

}
