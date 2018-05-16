/**
 * a class representing the "patch"
 * the patch can self-grow grains
 * 
 * @author SICHENG
 *
 */

public class Patch {

	private int currentGrain;
	
	// maximum number of grain that a patch can hold
	private final int MAX_GRAIN = 50;
	
	public Patch() {
		this.currentGrain = 0;
	}
	
	public int getCurrentGrain() {
		return currentGrain;
	}
	
	public void putGrain(int grain) {
		currentGrain += grain;
	}

	public void growGrain(){
		currentGrain += Params.NUM_GRAIN_GROWN;
	}
}
