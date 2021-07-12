import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class PlayerRec extends MovingRec{
	Bounds lastPosition;
	DodgeBox game;
	
	public PlayerRec(double x, double y, double width, double height, boolean isHarmless, Color color, DodgeBox game) {
		super(x, y, width, height, isHarmless, color);
		speed = 1;
		lastPosition = getLayoutBounds();
		this.game = game;
	}

	@Override
	public void tick() {
		lastPosition = getLayoutBounds();
		move();
	}

	@Override
	public void handleCollision(BoundObject other) {
		if(!other.getIsHarmless()) {
			setFill(Color.LIGHTGRAY);
			game.gameOver();
		}
		else {
			setX(lastPosition.getMinX());
			setY(lastPosition.getMinY());
		}
	}
	
	public void dodgeMove() {
		lastPosition = getLayoutBounds();
		int dodgeRange = 250;
		if(movingDir[0])
			setY(getY() - dodgeRange);
		if(movingDir[1])
			setY(getY() + dodgeRange);
		if(movingDir[2])
			setX(getX() - dodgeRange);
		if(movingDir[3])
			setX(getX() + dodgeRange);
	}
	
	public void resetDodge() {
		setX(lastPosition.getMinX());
		setY(lastPosition.getMinY());
	}
}
