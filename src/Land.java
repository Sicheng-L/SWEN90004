import java.util.Hashtable;

/**
 * Class representing the land
 * Use Hashtable
 * key: Tuple(the coordinate), value: either ArrayList<Person> or Patch
 * 
 * @author SICHENG
 *
 * @param <T>
 */
public class Land<T> extends Hashtable<Tuple, T>{
	
	public Land() {
	}
	
	public T getValue(int x, int y){
		return get(new Tuple(x, y));
	}
	
	public void putPair(int x, int y, T T){
		put(new Tuple(x,y), T);
	}

}
