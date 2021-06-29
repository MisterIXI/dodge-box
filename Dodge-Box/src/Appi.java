
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Appi extends Application {

	private boolean gameRunning;

	public static void main(String[] args) {
		Application.launch(args);
		// getting started pö9i7uzgoifzgluizfuizfkuzft

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane border = new BorderPane();
		Scene mainScene = new Scene(border);

		Rectangle rec = new Rectangle(50, 50);

		mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					rec.setY(rec.getY() - 10);
					break;
				case DOWN:
					rec.setY(rec.getY() + 10);
					break;
				case LEFT:
					rec.setX(rec.getX() - 10);
					break;
				case RIGHT:
					rec.setX(rec.getX() + 10);
					break;
				default:
					break;

				}

			}

		});

		border.getChildren().add(rec);

		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	public void gameLoop() {
		while (gameRunning) {
			
		}
	}

}
