import java.util.ArrayList;
import java.util.Hashtable;

public class Main {

	public static void main(String[] args) {
		// initialize the lands
		Land<ArrayList<Person>> personLand = new Land<ArrayList<Person>>();
		Land<Patch> patchLand = new Land<Patch>();
		
		for(int i = 0; i < Params.LAND_SIZE; i++) {
			for(int j = 0; j < Params.LAND_SIZE; j++) {
				personLand.putPair(i, j, new ArrayList<Person>());
				patchLand.putPair(i, j , new Patch());
			}
		}
		
		Controller c = new Controller(personLand, patchLand);
		
		while(true) {
			c.tick();
		}
		

	}

}
