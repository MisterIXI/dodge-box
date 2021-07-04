
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	private List<EnemyRec> enemies;
	private static int timeToNextEnemy;
	private static int score;

	public static void main(String[] args) {
		Application.launch(args);

	}

	public DodgeBox() {
		gameObjects = new LinkedList<>();
		collisionList = new ArrayList<>();
		gameRunning = true;
		timeToNextEnemy = 0;
		score = 0;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane border = new BorderPane();
		Scene mainScene = new Scene(border, 1300, 800);

		Rectangle rec = new Rectangle(50, 50);
		Pane x = new Pane();

		PlayerRec player = new PlayerRec(1000, 500, 50, 50, true);


		Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(1.6), event -> {
			gameLoop(border);
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

		StillRec boundLeft = new StillRec(0, 0, 30, 800, true);
		StillRec boundTop = new StillRec(30, 0, 1270,30, true);
		StillRec boundRight = new StillRec(1270, 30, 30, 770, true);
		StillRec boundBottom = new StillRec(30,770,1270,30, true);
		
		collisionList.add(boundLeft);
		collisionList.add(boundTop);
		collisionList.add(boundRight);
		collisionList.add(boundBottom);
		
		enemies = Arrays.asList(
				new EnemyRec(200, 200, 50, 50, false),
				new EnemyRec(250, 200, 50, 50, false),
				new EnemyRec(300, 200, 50, 50, false),
				new EnemyRec(350, 200, 50, 50, false),
				new EnemyRec(400, 200, 50, 50, false),
				new EnemyRec(450, 200, 50, 50, false),
				new EnemyRec(500, 200, 50, 50, false)
				);
		gameObjects.addAll(enemies);
		collisionList.addAll(enemies);
		
		player.setFill(Color.BLUE);
		border.getChildren().addAll(collisionList);
		primaryStage.setScene(mainScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		System.out.println(mainScene.getHeight());
		System.out.println(mainScene.getWidth());
	}

	public void gameLoop(BorderPane border) {
		if (gameRunning) {
			for (GameObject x : gameObjects) {
				x.Tick();
			}
			for (int i = 0; i < collisionList.size(); i++) {
				for (int j = 0; j < collisionList.size(); j++) {
					if (i != j) {
						if (collisionList.get(i).checkCollision(collisionList.get(j))) {
							collisionList.get(i).handleCollision(collisionList.get(j));
						}
					}
				}
			}
			if(timeToNextEnemy++ == 1000) {
				EnemyRec temp = new EnemyRec(500, 200, 50, 50, false);
				enemies.add(temp);
				gameObjects.add(temp);
				collisionList.add(temp);
				border.getChildren().add(temp);
				timeToNextEnemy = 0;
				System.out.println("new enemy");
			}
			score++;
		}
	}
	
	public static void gameOver() {
		gameRunning = false;
		Stage secondStage = new Stage();
		BorderPane border = new BorderPane();
		Scene scoreScene = new Scene(border);
		
		Label scoreLabel = new Label("Score: " + score/100);//TODO: Add score
		border.setCenter(scoreLabel);
		secondStage.setScene(scoreScene);
		secondStage.show();
	}

}
