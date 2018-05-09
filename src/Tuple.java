/**
 * Class to represent the coordinates
 * x - horizontal coordinate
 * y - vertical coordinate
 * 
 * @author SICHENG
 *
 */

public class Tuple {
	
	private int x;
	private int y;

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
	
	private void setX(int x){
		this.x = x;
	}
	
	private void setY(int y){
		this.y = y;
	}
	
	public void setXY(int x, int y){
		setX(x);
		setY(y);
	}

}
