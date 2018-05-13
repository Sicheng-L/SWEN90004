import java.util.Hashtable;

public class Land<T> extends Hashtable<Tuple, T>{
	
	public Land() {
		// TODO Auto-generated constructor stub
	}
	
	public T getValue(int x, int y){
		return get(new Tuple(x, y));
	}
	
	public void putPair(int x, int y, T T){
		put(new Tuple(x,y), T);
	}

}
