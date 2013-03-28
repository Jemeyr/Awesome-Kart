package World;

import Graphics.GraphicsComponent;
import Graphics.Light;

public interface Entity {
	public GraphicsComponent getGraphicsComponent();
	public Light getLight();
	public void update();
}
