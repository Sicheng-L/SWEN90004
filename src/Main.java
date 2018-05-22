import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Main Class - to initiate the program
 * 
 * @author SICHENG
 *
 */
public class Main {

	public static void main(String[] args) {
		// initialize the lands
		Land<ArrayList<Person>> personLand = Params.newPersonLand();
		Land<Patch> patchLand = Params.newPatchLand();
		
		Controller c = new Controller(personLand, patchLand);
		
		c.init();
		
		int index = 0;
		while(true) {
			index ++;
			c.tick(index);
		}
		

	}

}
