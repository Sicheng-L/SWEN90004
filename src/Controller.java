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
	 * setup patches for initialization
	 */
	private void setupPatches() {
		Hashtable<Integer, Tuple> bestLands = Params.randomBestLand();
		Set<Integer> keys = bestLands.keySet();
        for(Integer key : keys) {
            patchLand.getValue(bestLands.get(key).getX(),
            		bestLands.get(key).getY()).setMaxGrain(Params.MAX_GRAIN);
            patchLand.getValue(bestLands.get(key).getX(),
            		bestLands.get(key).getY()).putGrain(Params.MAX_GRAIN);
        }
        
	}
	
	/*
	 * setup people for initialization
	 * make the three classes equally distributed
	 */
	private Land<ArrayList<Person>> setupPeople() {
		Hashtable<Integer, Tuple> peopleLand = Params.randomPeople();
		Set<Integer> keys = peopleLand.keySet();
		int[] classes = new int[3];
		Land<ArrayList<Person>> newPersonLand = new Land<ArrayList<Person>>();
		do {
			Land<ArrayList<Person>> testLand = Params.newPersonLand();
			for(Integer key : keys) {
				testLand.getValue(peopleLand.get(key).getX(),
						peopleLand.get(key).getY()).add(new Person());
			}
			classes = getClassDistribution(testLand);
			newPersonLand = testLand;
		} while(classes[0] < Params.NUM_PEOPLE * Params.CLASS_THRESHOLD 
				|| classes[1] < Params.NUM_PEOPLE * Params.CLASS_THRESHOLD 
				|| classes[2] < Params.NUM_PEOPLE * Params.CLASS_THRESHOLD);
		return newPersonLand;
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
	 * Spread the grain(best land) when initializing the process
	 */
	private Land<Patch> spreadGrainFirst() {
		Land<Patch> newPatchLand = Params.newPatchLand();
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				// spread the grain on the best land
				Patch patch = patchLand.getValue(i, j);
				if(patch.getMaxGrain() != 0) {
					// spread the grain
					patch.putGrain(patch.getMaxGrain());
					double diffusion = patch.getCurrentGrain() 
							* Params.DIFF_RATE / 8.0;
					patch.diff();
					newPatchLand.getValue(i, j)
						.setMaxGrain(patch.getMaxGrain());
					newPatchLand.getValue(i, j)
						.putGrain(patch.getCurrentGrain());
					newPatchLand.getValue(i, j)
						.diffed(patch.getCurrentGrain());;
					// all 8 adjacent blocks get the diffusion
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, (j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue(i, (j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE,
										(j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, j)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE, j)
							.diffed(diffusion);
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, (j - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue(i, (j - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE, 
							(j - 1 + Params.LAND_SIZE) % Params.LAND_SIZE)
							.diffed(diffusion);
				}
			}
		}
		return newPatchLand;
	}
	
	/*
	 * Spread the grain(spread the grain around some more)
	 */
	private Land<Patch> spreadGrainSecond() {
		Land<Patch> newPatchLand = Params.newPatchLand();
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				// spread the grain on the best land
				Patch patch = patchLand.getValue(i, j);
				if(patch.getDiffGrain() != 0) {
					// spread the grain
					double diffusion = patch.getDiffGrain() 
							* Params.DIFF_RATE / 8.0;
					patch.diff();
					newPatchLand.getValue(i, j)
							.diffed(patch.getDiffGrain());
					// all 8 adjacent blocks get the diffusion
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, (j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue(i, (j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE,
										(j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, j)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE, j)
							.diffed(diffusion);
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, (j - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue(i, (j - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE, 
							(j - 1 + Params.LAND_SIZE) % Params.LAND_SIZE)
							.diffed(diffusion);
				} else if (patch.getMaxGrain() != 0) {
					// spread the grain
					double diffusion = (patch.getCurrentGrain() 
							+ patch.getDiffGrain())
							* Params.DIFF_RATE / 8.0;
					newPatchLand.getValue(i, j)
						.setMaxGrain(Params.MAX_GRAIN);
					newPatchLand.getValue(i, j)
						.putGrain(((int) (patch.getCurrentGrain() 
								* (1 - Params.DIFF_RATE))));
					newPatchLand.getValue(i, j).diffed(patch.getDiffGrain());
					// all 8 adjacent blocks get the diffusion
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, (j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue(i, (j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE,
										(j + 1) % Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, j)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE, j)
							.diffed(diffusion);
					newPatchLand.getValue((i - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE, (j - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue(i, (j - 1 + Params.LAND_SIZE) 
							% Params.LAND_SIZE)
							.diffed(diffusion);
					newPatchLand.getValue((i + 1) % Params.LAND_SIZE, 
							(j - 1 + Params.LAND_SIZE) % Params.LAND_SIZE)
							.diffed(diffusion);
				}
			}
		}
		return newPatchLand;
	}
	
	private void roundupGrain() {
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				Patch patch = patchLand.getValue(i, j);
				if(patch.getDiffGrain() != 0 && patch.getMaxGrain() != 0) {
					patch.setMaxGrain((int)(patch.getCurrentGrain() 
							+ patch.getDiffGrain()));
					patch.putGrain((int)(patch.getCurrentGrain() 
							+ patch.getDiffGrain()));
				} else if(patch.getDiffGrain() != 0) {
					patch.setMaxGrain((int)(patch.getCurrentGrain() 
							+ patch.getDiffGrain()));
					patch.putGrain((int)(patch.getCurrentGrain() 
							+ patch.getDiffGrain()));
				}
			}
		}
	}
	
	/*
	 * All the people on the land harvest the grain 
	 * in the lands that they are standing on
	 */
	private void personHarvest() {
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
				if(people.size() != 0) {
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
	public void tick(int index){
		if(index == 20 || index == 50 || index == 80){
			System.out.print("Tick: "+ index + ".");
			int[] classes = getClassDistribution(this.personLand);
			System.out.print("Poor:" + classes[0] + "," 
					+ String.format("%.2f",
							(double)classes[0]/Params.NUM_PEOPLE * 100) + "%.");
			System.out.print("Middle:" + classes[1] + "," 
					+ String.format("%.2f",
							(double)classes[1]/Params.NUM_PEOPLE * 100) + "%.");
			System.out.print("Rich:" + classes[2] + "," 
					+ String.format("%.2f",
							(double)classes[2]/Params.NUM_PEOPLE * 100) + "%.");
			System.out.println("");
		}
		// harvest
		personHarvest();
		// move towards grain
		changePersonLand(personMoveTowardsGrain());
		// eat age die
		changePersonLand(personEatAgeDie());
		// grow grain
		if(index % Params.GRAIN_GROWTH_INTERVAL == 0) {
			growGrain();
		}
		
	}

	
	/*
	 * Actions for the initiation
	 */
	public void init(){
		setupPatches();
		changePersonLand(setupPeople());
		for(int i = 0; i < Params.FIRST_SPREAD; i++) {
			changePatchLand(spreadGrainFirst());
		}
		for(int i = 0; i < Params.FURTHER_SPREAD; i++) {
			changePatchLand(spreadGrainSecond());
		}
		roundupGrain();
//		for(int i = 0; i < Params.LAND_SIZE; i++) {
//			for(int j = 0; j < Params.LAND_SIZE; j++) {
//				System.out.print(patchLand.getValue(i, j).getMaxGrain() + ".");
//			}
//			System.out.println("");
//		}
	}
	
	/*
	 * Helper function to get the maximum wealth held by one person
	 */
	private int getMaxWealth(Land<ArrayList<Person>> personLand) {
		int maxWealth = 0;
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
				for(int k = 0; k < people.size(); k++) {
					if(maxWealth < people.get(k).getWealth()){
						maxWealth = people.get(k).getWealth();
					}
				}
			}
		}
		return maxWealth;
	}
	
	/*
	 * Helper function to get number of people in each class
	 */
	private int[] getClassDistribution(Land<ArrayList<Person>> personLand) {
		int[] classes = new int[3];
		int low = 0, middle = 0, high = 0;
		int maxWealth = getMaxWealth(personLand);
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				ArrayList<Person> people = personLand.getValue(i, j);
				for(int k = 0; k < people.size(); k++) {
					switch(people.get(k).getClass(maxWealth)){
						case "Low":
							low++;
							break;
						case "Middle":
							middle++;
							break;
						case "High":
							high ++;
							break;
					}
				}
			}
		}
		classes[0] = low;
		classes[1] = middle;
		classes[2] = high;
		return classes;
	}
	
	/*
	 * Change controlling personLand
	 */
	private void changePersonLand(Land<ArrayList<Person>> personLand) {
		this.personLand = personLand;
	}
	
	/*
	 * Change controlling patchLand
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
