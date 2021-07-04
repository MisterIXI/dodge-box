import java.util.Random;

public class EnemyRec extends MovingRec {
	Random r;

	public EnemyRec(double x, double y, double width, double height, boolean isHarmless) {
		super(x, y, width, height, isHarmless);
		// TODO Auto-generated constructor stub
		speed = 5;
		movingDir[1] = true;
		movingDir[3] = true;
		r = new Random();
	}

	@Override
	public void Tick() {
		System.out.println(this.getBoundsInParent());
		move();
	}

	@Override
	public void handleCollision(boolean otherIsHarmless) {
		// TODO Auto-generated method stub
		if(otherIsHarmless) {
			movingDir[0] = r.nextBoolean();
			movingDir[1] = r.nextBoolean();
			movingDir[2] = !movingDir[2];
			movingDir[3] = !movingDir[3];
		}
	}

}
