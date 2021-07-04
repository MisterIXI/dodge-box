import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class PlayerRec extends MovingRec{
	Bounds lastPosition;
	
	public PlayerRec(double x, double y, double width, double height, boolean isHarmless) {
		super(x, y, width, height, !isHarmless);
		speed = 10;
		lastPosition = getBoundsInParent();
	}

	@Override
	public void Tick() {
		lastPosition = getBoundsInParent();
		move();
	}

	@Override
	public void handleCollision(boolean otherIsHarmless) {
		if(!otherIsHarmless) {
			setFill(Color.RED);
			DodgeBox.gameOver();
		}
		else {
			setX(lastPosition.getMinX());
			setY(lastPosition.getMinY());
		}
	}
}
