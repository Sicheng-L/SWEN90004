import java.util.ArrayList;

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
	private void setupPatches(){
		
	}
	
	/*
	 * Move the people to the adjacent block according to the 
	 * judgement of their own
	 * 
	 * Judgement process: every person can foresee block areas (crosses)
	 * the crosses are of the size of the person's VISION
	 * 
	 */
	private void personMoveTowardsGrain(){
		
	}
	
	/*
	 * All people eats, age and possibly die (if they are dead, 
	 * remove them from the land and add a new person at the same location)
	 */
	private void personEatAgeDie(){
		
	}
	
	/*
	 * Spread the grain when initializing the process
	 */
	private void spreadGrain(){
		
	}
	
	/*
	 * All the people on the land harvest the grain 
	 * in the lands that they are standing on
	 */
	private void personHarvest(){
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
	private void growGrain(){
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
}
