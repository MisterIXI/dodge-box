
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DodgeBox extends Application {

	private static boolean gameRunning;
	private LinkedList<GameObject> gameObjects;
	private ArrayList<BoundObject> collisionList;

	public static void main(String[] args) {
		Application.launch(args);

	}

	public DodgeBox() {
		gameObjects = new LinkedList<>();
		collisionList = new ArrayList<>();
		gameRunning = true;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane border = new BorderPane();
		Scene mainScene = new Scene(border, 1300, 800);

		Rectangle rec = new Rectangle(50, 50);
		Pane x = new Pane();

		PlayerRec player = new PlayerRec(50, 50, 50, 50, true);


		Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(16), event -> {
			gameLoop();
		}));
		gameLoop.setCycleCount(-1);
		gameLoop.play();
		mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					player.movingDir[0] = true;
					break;
				case DOWN:
					player.movingDir[1] = true;
					break;
				case LEFT:
					player.movingDir[2] = true;
					break;
				case RIGHT:
					player.movingDir[3] = true;
					break;
				default:
					break;
				}
			}
		});
		mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					player.movingDir[0] = false;
					break;
				case DOWN:
					player.movingDir[1] = false;
					break;
				case LEFT:
					player.movingDir[2] = false;
					break;
				case RIGHT:
					player.movingDir[3] = false;
					break;
				default:
					break;
				}
			}
		});
		
		gameObjects.add(player);
		collisionList.add(player);
		Rectangle renderPlayer = new Rectangle(50, 50);

		StillRec boundLeft = new StillRec(0, 0, 30, 800, true);
		StillRec boundTop = new StillRec(30, 0, 1270,30, true);
		StillRec boundRight = new StillRec(1270, 30, 30, 770, true);
		StillRec boundBottom = new StillRec(30,770,1270,30, true);
		
		collisionList.add(boundLeft);
		collisionList.add(boundTop);
		collisionList.add(boundRight);
		collisionList.add(boundBottom);
		
		EnemyRec enemy1 = new EnemyRec(200, 200, 50, 50, false);
		gameObjects.add(enemy1);
		collisionList.add(enemy1);
		
		player.setFill(Color.BLUE);
		border.getChildren().addAll(collisionList);
		primaryStage.setScene(mainScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		System.out.println(mainScene.getHeight());
		System.out.println(mainScene.getWidth());
	}

	public void gameLoop() {
		if (gameRunning) {
			for (GameObject x : gameObjects) {
				x.Tick();
			}
			for (int i = 0; i < collisionList.size(); i++) {
				for (int j = 0; j < collisionList.size(); j++) {
					if (i != j) {
						if (collisionList.get(i).checkCollision(collisionList.get(j))) {
							collisionList.get(i).handleCollision(collisionList.get(j).isHarmless);
						}
					}
				}
			}
		}
	}
	
	public static void gameOver() {
		gameRunning = false;
	}

}
