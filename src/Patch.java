/**
 * a class representing the "patch"
 * the patch can self-grow grains
 * 
 * @author SICHENG
 *
 */

public class Patch {

	private int currentGrain;
	
	private int MAX_GRAIN;
	
	// a double to store diffusion of grain
	// will be rounded to integer after diffusion
	private double diffGrain;
	
	public Patch() {
		this.currentGrain = 0;
		this.MAX_GRAIN = 0;
		this.diffGrain = 0;
	}
	
	/*
	 * Get the diffusion grain
	 */
	public double getDiffGrain() {
		return diffGrain;
	}
	
	/*
	 * Diffuse process
	 */
	public void diff() {
		diffGrain -= diffGrain * Params.DIFF_RATE;
	}
	
	/*
	 * Get grain from the diffusion
	 */
	public void diffed(double newGrain) {
		diffGrain += newGrain;
	}
	
	/*
	 * Get the current number of grains on the patch
	 */
	public int getCurrentGrain() {
		return currentGrain;
	}
	
	/*
	 * Set the max grain this patch can hold
	 */
	public void setMaxGrain(int max_grain) {
		this.MAX_GRAIN = max_grain;
	}
	
	/*
	 * Get the number of grains this patch can hold
	 */
	public int getMaxGrain() {
		return MAX_GRAIN;
	}
	
	/*
	 * Put grain on the patch,
	 * but the number of grains cannot be over MAX_GRAIN
	 */
	public void putGrain(int grain) {
		currentGrain += grain;
		if(currentGrain > MAX_GRAIN) {
			currentGrain = MAX_GRAIN;
		}
	}

	/*
	 * Grow grain on the patch,
	 * but the number of grains cannot be over MAX_GRAIN
	 */
	public void growGrain(){
		currentGrain += Params.NUM_GRAIN_GROWN;
		if(currentGrain > MAX_GRAIN) {
			currentGrain = MAX_GRAIN;
		}
	}
	
	/*
	 * Let people harvest grain
	 */
	public void harvestGrain() {
		currentGrain = 0;
	}
}
