import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class PlayerRec extends MovingRec{
	Bounds lastPosition;
	
	public PlayerRec(double x, double y, double width, double height, boolean isHarmless, Color color) {
		super(x, y, width, height, isHarmless, color);
		speed = 1;
		lastPosition = getLayoutBounds();
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
			DodgeBox.gameOver();
		}
		else {
			setX(lastPosition.getMinX());
			setY(lastPosition.getMinY());
		}
	}
}
