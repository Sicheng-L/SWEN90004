import java.util.Arrays;

/**
 * Class to represent the coordinates
 * x - horizontal coordinate
 * y - vertical coordinate
 * 
 * @author SICHENG
 *
 */

public class Tuple {
	
	private final int x;
	private final int y;

	public Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
    public int hashCode () {
		int[] hash= new int[2];
		hash[0] = x;
		hash[1] = y;
        return Arrays.hashCode(hash);
    }

    @Override
    public boolean equals (Object other) {
        if(!(other instanceof Tuple)) return false;
        Tuple tupleo = (Tuple) other;
        return this.x == tupleo.getX() && this.y == tupleo.getY();
    }

	

}
