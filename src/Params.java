import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 * Parameters that influence the behaviour of the system.
 * 
 * @author SICHENG
 *
 */

public class Params {
	
	// number of people on the land - range from 2-1000
	public final static int NUM_PEOPLE = 2;

	// max-vision of people - how far they can see
	// 4 directions instead of 8
	// range from 1-15
	public final static int MAX_VISION = 3;
	
	// max metabolism of people - the number of grain they eat at each tick
	// range from 1-25
	public final static int METABOLISM_MAX = 5;
	
	// max life expectancy - the number of ticks one person can live
	// range from 1-100
	public final static int LIFE_EXPECTANCY_MAX = 100;
	
	// min life expectancy
	// range from 1-100
	public final static int LIFE_EXPECTANCY_MIN = 1;
	
	// percentage of best land - land that has 
	// range from 5%-25%
	public final static double PERCENT_BEST_LAND = 0.05;
	
	// the maximum number of grains a patch can hold
	public final static int MAX_GRAIN = 50;
	
	// grain growth interval (measured in the number of ticks)
	// range from 1-10
	public final static int GRAIN_GROWTH_INTERVAL = 10;
	
	// number of grain grown after every interval
	// range from 1-10
	public final static int NUM_GRAIN_GROWN = 10;
	
	// size of the land
	public final static int LAND_SIZE = 50;
	
	
	public Params() {
	}
	
	/*
	 * Get a random vision ranging from 1 to MAX_VISION (inclusive)
	 */
	public static int randomVision() {
		Random random = new Random();
		return 1 + random.nextInt(MAX_VISION);
	}
	
	/*
	 * Get a random metabolism ranging from 1 to METABOLISM_MAX (inclusive)
	 */
	public static int randomMetabolism() {
		Random random = new Random();
		return 1 + random.nextInt(METABOLISM_MAX);
	}
	
	/*
	 * Get a random initial wealth ranging from metabolism to 
	 * metabolism + 50 (inclusive)
	 */
	public static int randomWealth(int metabolism) {
		Random random = new Random();
		return metabolism + random.nextInt(50);
	}
	
	/*
	 * Get a random life expectancy ranging from LIFE_EXPECTANCY_MIN to 
	 * LIFE_EXPECTANCY_MAX (inclusive)
	 */
	public static int randomLifeExpectancy() {
		Random random = new Random();
		return LIFE_EXPECTANCY_MIN + random.nextInt(LIFE_EXPECTANCY_MAX - 
				LIFE_EXPECTANCY_MIN);
	}
	
	/*
	 * Get the random coordinates for best lands
	 */
	public static Hashtable<Integer, Tuple> randomBestLand() {
		Hashtable<Integer, Tuple> bestLands = new Hashtable<Integer, Tuple>();
		int index = 0;
		while(index < (int)(LAND_SIZE * LAND_SIZE * PERCENT_BEST_LAND)) {
			Random random = new Random();
			Tuple t = new Tuple(random.nextInt(LAND_SIZE),
					random.nextInt(LAND_SIZE));
			while(bestLands.contains(t)){
				t = new Tuple(random.nextInt(LAND_SIZE),
						random.nextInt(LAND_SIZE));
			}
			bestLands.put(index, t);
			index++;
		}
		return bestLands;
	}
	

}
