import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * A controller to control both patch land and person land
 * Make all people move towards the
 * @author SICHENG
 *
 */
public class Controller {
	
	private Land<ArrayList<Person>> personLand;
	private Land<Patch> patchLand;
	

	public Controller(Land<ArrayList<Person>> personLand,
			Land<Patch> patchLand) {
		this.personLand = personLand;
		this.patchLand = patchLand;
	}
	
	/*
	 * setup patches at the first tick
	 */
	private void setupPatches() {
		Hashtable<Integer, Tuple> bestLands = Params.randomBestLand();
		Set<Integer> keys = bestLands.keySet();
        for(Integer key : keys){
            patchLand.getValue(bestLands.get(key).getX(),
            		bestLands.get(key).getY()).putGrain(Params.MAX_GRAIN);
        }
        
	}
	
	/*
	 * Move the people to the adjacent block according to the 
	 * Judgment of their own
	 * 
	 * Judgment process: every person can foresee block areas (crosses)
	 * the crosses are of the size of the person's VISION
	 * 
	 */
	private Land<ArrayList<Person>> personMoveTowardsGrain() {
		Land<ArrayList<Person>> nextPersonLand = Params.newPersonLand();
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
				// iterate through the whole ArrayList
				for(int k = 0; k < people.size(); k++) {
					// check blocks within the person's view
					int bestNorth = 0, bestSouth = 0;
					int bestEast = 0 , bestWest = 0;
					for(int t = 0; t < people.get(k).getView(); t++) {
						// the block in the north
						int grainNorth = 
								patchLand
								.getValue(i, (j + k) % Params.LAND_SIZE)
								.getCurrentGrain();
						bestNorth = bestNorth > grainNorth ?
								bestNorth : grainNorth;
						// the block in the south
						int grainSouth = 
								patchLand
								.getValue(i, (j - k + Params.LAND_SIZE) 
										% Params.LAND_SIZE)
								.getCurrentGrain();
						bestSouth = bestSouth > grainSouth ?
								bestSouth : grainSouth;
						// the block in the east
						int grainEast = 
								patchLand
								.getValue((i + k) % Params.LAND_SIZE, j)
								.getCurrentGrain();
						bestEast = bestEast > grainEast ?
								bestEast : grainEast;
						// the block in the west
						int grainWest = 
								patchLand
								.getValue((i - k + Params.LAND_SIZE) 
										% Params.LAND_SIZE, j)
								.getCurrentGrain();
						bestWest = bestWest > grainWest ?
								bestWest : grainWest;
					}
					// check the best direction and move towards it
					int best = 
							findBest(bestNorth, bestSouth, bestEast, bestWest);
					// move towards the right direction
					if(best == bestNorth) {
						nextPersonLand.getValue(i, (j + 1) % Params.LAND_SIZE)
						.add(people.get(k));	
					} else if (best == bestSouth) {
						nextPersonLand.getValue(i, (j - 1 + Params.LAND_SIZE)
								% Params.LAND_SIZE)
						.add(people.get(k));
					} else if (best == bestEast) {
						nextPersonLand.getValue((i + 1) % Params.LAND_SIZE, j)
						.add(people.get(k));
					} else {
						nextPersonLand.getValue((i - 1 + Params.LAND_SIZE)
								% Params.LAND_SIZE, j)
						.add(people.get(k));
					}
				}
			}		
		}
		return nextPersonLand;
	}
	
	/*
	 * All people eats, age and possibly die (if they are dead, 
	 * remove them from the land and add a new person at the same location)
	 */
	private void personEatAgeDie() {
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
			}
		}
	}
	
	/*
	 * Spread the grain when initializing the process
	 */
	private void spreadGrain() {
		
	}
	
	/*
	 * All the people on the land harvest the grain 
	 * in the lands that they are standing on
	 */
	private void personHarvest() {
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
				int averageGrain = patchLand.getValue(i, j).getCurrentGrain() 
						/ people.size();
				for(int k = 0; k < people.size(); k++) {
					people.get(k).setWealth(
							people.get(k).getWealth() + averageGrain);;
				}
			}
		}
	}
	
	/*
	 * Grow grains of each land
	 */
	private void growGrain() {
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				patchLand.getValue(i, j).growGrain();
			}
		}
	}
	
	
	/*
	 * Actions for a general ticking
	 */
	public void tick(){
		
	}
	
	/*
	 * Actions for the first tick
	 */
	public void firstTick(){
		
	}
	
	/*
	 * Change controlling personLand
	 */
	private void changeState(Land<ArrayList<Person>> personLand) {
		this.personLand = personLand;
	}
	
	/*
	 * Helper function to find the maximum of 4 numbers 
	 * (representing the best land in each direction)
	 */
	private int findBest(int north, int south, int east, int west){
		int first = 0, second = 0, third = 0;
		first = north > south ? north : south;
		second = first > east ? first : east;
		third = second > west ? second : west;
		return third;
	}
}
