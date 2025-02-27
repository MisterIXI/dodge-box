import java.util.Random;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class ChaserEnemyYRec extends MovingRec {
	Random r;
	boolean handledCollision;
	Bounds lastPosition;
    PlayerRec player;

    public ChaserEnemyYRec(double x, double y, double width, double height, PlayerRec player) {
        super(x, y, width, height, false, Color.BLUEVIOLET);
        speed = 0.1;
        movingDir[0] = true;
        movingDir[2] = true;
        r = new Random();
        handledCollision = false;
        lastPosition = getBoundsInParent();
        this.player = player;
    }
    
	@Override
	public void tick() {
		lastPosition = getBoundsInParent();
		move();
		handledCollision = false;

        if ( !((this.getY() <= player.getY() +20) && (this.getY() >= player.getY() -20))) {
            movingDir[2] = false;
            movingDir[3] = false;
            if (player.getY() < this.getY()) {
                movingDir[0] = true;    // up
                movingDir[1] = false;
            }
            else {
                movingDir[0] = false;
                movingDir[1] = true;    // down
            }
        }
        else {  // Y matches
            movingDir[0] = false;
            movingDir[1] = false;
            if (player.getX() < this.getX()) {
                movingDir[2] = true;    // left
                movingDir[3] = false;
            }
            else {
                movingDir[2] = false;
                movingDir[3] = true;    // right
            }
        }
	}

	@Override
	public void handleCollision(BoundObject other) {
		if (!handledCollision) {

			//System.out.println("oof ouch ouwie");
			if (true) {
				do {
                    
                    
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
}
