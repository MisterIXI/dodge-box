
public abstract class MovingRec extends BoundObject{
	
	public MovingRec(double x, double y, double width, double height, boolean isHarmless) {
		super(x, y, width, height, isHarmless);
		// TODO Auto-generated constructor stub
	}

	double speed;
	boolean movingDir[] = new boolean[4];

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	public void move() {
		if (movingDir[0])//UP
			this.setY(this.getY() - speed);
		if (movingDir[1])//DOWN
			this.setY(this.getY() + speed);
		if (movingDir[2])//LEFT
			this.setX(this.getX() - speed);
		if (movingDir[3])//RIGHT
			this.setX(this.getX() + speed);
	}
	
	public boolean getUp() {
		return movingDir[0];
	}
	
	public void switchUp(boolean shouldMove) {
		movingDir[0] = shouldMove;
	}
	
	public boolean getDOWN() {
		return movingDir[1];
	}
	
	public void switchDOWN(boolean shouldMove) {
		movingDir[1] = shouldMove;
	}
	
	public boolean getLEFT() {
		return movingDir[2];
	}
	
	public void switchLEFT(boolean shouldMove) {
		movingDir[2] = shouldMove;
	}
	
	public boolean getRIGHT() {
		return movingDir[3];
	}
	
	public void switchRIGHT(boolean shouldMove) {
		movingDir[3] = shouldMove;
	}
}
