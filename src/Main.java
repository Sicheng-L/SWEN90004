import java.util.ArrayList;
import java.util.Hashtable;

public class Main {

	public static void main(String[] args) {
		// initialize the lands
		Land<ArrayList<Person>> personLand = Params.newPersonLand();
		Land<Patch> patchLand = Params.newPatchLand();
		
		Controller c = new Controller(personLand, patchLand);
		
		while(true) {
			c.tick();
		}
		

	}

}
