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
		
	}
	
	/*
	 * Grow grains of each land
	 */
	private void growGrain(){
		
	}
	
	
	/*
	 * Actions for a general ticking
	 */
	public void tick(){
		
	}

}
