import javafx.geometry.Bounds;
import javafx.scene.shape.*;

public abstract class BoundObject extends Rectangle implements GameObject {
	boolean isHarmless;

	public BoundObject(double x, double y, double width, double height, boolean isHarmless) {

		super(x, y, width, height);
		this.isHarmless = isHarmless;

	}

	public boolean checkCollision(BoundObject other) {
		Bounds o = other.getBoundsInParent();
		return this.intersects(other.getLayoutBounds());
	}

	@Override
	public abstract void Tick();

	public abstract void handleCollision(BoundObject other);
	
	public boolean getIsHarmless() {
		return isHarmless;
	}

}
