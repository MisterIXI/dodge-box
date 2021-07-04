import javafx.geometry.Bounds;
import javafx.scene.shape.*;

public abstract class BoundObject extends Rectangle implements GameObject {
	boolean isHarmless;

	public BoundObject(double x, double y, double width, double height, boolean isHarmless) {

		super(x, y, width, height);
		this.isHarmless = isHarmless;

	}

	public boolean checkCollision(BoundObject other) {
		Bounds o = other.getBoundsInLocal();
		return this.intersects(o.getMinX() + 10, o.getMinY() + 10, o.getMaxX() -20, o.getMaxY() -20);
	}

	@Override
	public abstract void Tick();

	public abstract void handleCollision(boolean otherIsHarmless);

}
