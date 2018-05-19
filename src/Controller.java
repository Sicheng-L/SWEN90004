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
            		bestLands.get(key).getY()).setMaxGrain(Params.MAX_GRAIN);
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
					int north = patchLand.getValue(i, j).getCurrentGrain(),
						south = patchLand.getValue(i, j).getCurrentGrain(),
						east = patchLand.getValue(i, j).getCurrentGrain() ,
						west = patchLand.getValue(i, j).getCurrentGrain();
					for(int t = 0; t < people.get(k).getView(); t++) {
						// the block in the north
						int grainNorth = 
								patchLand
								.getValue(i, (j + k) % Params.LAND_SIZE)
								.getCurrentGrain();
						north += grainNorth;
						// the block in the south
						int grainSouth = 
								patchLand
								.getValue(i, (j - k + Params.LAND_SIZE) 
										% Params.LAND_SIZE)
								.getCurrentGrain();
						south += grainSouth;
						// the block in the east
						int grainEast = 
								patchLand
								.getValue((i + k) % Params.LAND_SIZE, j)
								.getCurrentGrain();
						east += grainEast;
						// the block in the west
						int grainWest = 
								patchLand
								.getValue((i - k + Params.LAND_SIZE) 
										% Params.LAND_SIZE, j)
								.getCurrentGrain();
						west += grainWest; 
					}
					// check the best direction and move towards it
					int best = 
							findBest(north, south, east, west);
					// move towards the right direction
					if(best == north) {
						nextPersonLand.getValue(i, (j + 1) % Params.LAND_SIZE)
						.add(people.get(k));	
					} else if (best == south) {
						nextPersonLand.getValue(i, (j - 1 + Params.LAND_SIZE)
								% Params.LAND_SIZE)
						.add(people.get(k));
					} else if (best == east) {
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
	 * add a new person at the same location)
	 */
	private Land<ArrayList<Person>> personEatAgeDie() {
		Land<ArrayList<Person>> nextPersonLand = Params.newPersonLand();
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
				for(int k = 0; k < people.size(); k++) {
					people.get(k).eat();
					if(people.get(k).isDead()) {
						nextPersonLand.getValue(i, j).add(new Person());
					} else {
						nextPersonLand.getValue(i, j).add(people.get(k));
					}
				}
			}
		}
		return nextPersonLand;
	}
	
	/*
	 * Spread the grain when initializing the process
	 */
	private Land<Patch> spreadGrain() {
		Land<Patch> newPatchLand = Params.newPatchLand();
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				// spread the grain on the best land
				double averageDiffFirst = 0;
				double averageDiffSecond = 0;
				for(int k = 0 ; k < Params.FIRST_SPREAD; k++) {
					Patch patch = patchLand.getValue(i, j);
					if(patch.getMaxGrain() != 0) {
						// spread the grain
						patch.putGrain(patch.getMaxGrain());
						averageDiffFirst +=
								patch.getCurrentGrain() * Params.DIFF_RATE / 8;
					}
				}
				
				for(int k = 0; k < Params.FURTHER_SPREAD; k++) {
					
				}
			}
		}
		return newPatchLand;
	}
	
	/*
	 * All the people on the land harvest the grain 
	 * in the lands that they are standing on
	 */
	private void personHarvest() {
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
				int averageGrain = (int)Math.floor(
						patchLand.getValue(i, j).getCurrentGrain() 
						/ people.size());
				for(int k = 0; k < people.size(); k++) {
					people.get(k).setWealth(
							people.get(k).getWealth() + averageGrain);;
				}
				patchLand.getValue(i, j)
				.harvestGrain();
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
	private void changePersonLand(Land<ArrayList<Person>> personLand) {
		this.personLand = personLand;
	}
	
	/*
	 * Change controlling patchland
	 */
	private void changePatchLand(Land<Patch> patchLand) {
		this.patchLand = patchLand;
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
