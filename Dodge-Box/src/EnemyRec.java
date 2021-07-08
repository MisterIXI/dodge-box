import java.util.Arrays;
import java.util.Random;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class EnemyRec extends MovingRec {
	Random r;
	boolean handledCollision;
	Bounds lastPosition;
	private double hue;
	boolean active;

	public EnemyRec(double x, double y, double width, double height, boolean isHarmless) {
		super(x, y, width, height, isHarmless);
		speed = 0.1;
		movingDir[0] = true;
		movingDir[2] = true;
		r = new Random();
		handledCollision = false;
		lastPosition = getBoundsInParent();
		hue = Color.RED.getHue();
		active = true;
	}

	@Override
	public void Tick() {
		lastPosition = getBoundsInParent();
		move();
		handledCollision = false;
	}

	@Override
	public void handleCollision(BoundObject other) {
		if (!handledCollision && (other.getClass().getName() != "PlayerRec")) {

			//System.out.println("oof ouch ouwie");
			if (true) {
				do {

					movingDir[0] = r.nextBoolean();
					movingDir[1] = r.nextBoolean();
					movingDir[2] = r.nextBoolean();
					movingDir[3] = r.nextBoolean();
				} while (!(movingDir[0] != movingDir[1] || movingDir[2] != movingDir[3]));
				//System.out.println(Arrays.toString(movingDir));
				
				setX(lastPosition.getMinX());
				setY(lastPosition.getMinY());
//				movingDir[0] = !movingDir[0];
//				movingDir[1] = !movingDir[1];
//				movingDir[2] = !movingDir[2];
//				movingDir[3] = !movingDir[3];
			}
			handledCollision = true;
		}
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
		this.setFill(Color.hsb(hue, 1, speed));
	}

}
