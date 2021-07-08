
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DodgeBox extends Application {

	private static boolean gameRunning;
	private LinkedList<GameObject> gameObjects;
	private ArrayList<BoundObject> collisionList;
	private List<EnemyRec> enemies;
	private static int enemyNum;
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
		primaryStage.setTitle("DodgeBox");
		primaryStage.getIcons().add(new Image("icon.png"));

		PlayerRec player = new PlayerRec(1000, 500, 50, 50, true, Color.BLUE);

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
		StillRec boundTop = new StillRec(30, 0, 1270, 30, true);
		StillRec boundRight = new StillRec(1270, 30, 30, 770, true);
		StillRec boundBottom = new StillRec(30, 770, 1270, 30, true);

		collisionList.add(boundLeft);
		collisionList.add(boundTop);
		collisionList.add(boundRight);
		collisionList.add(boundBottom);

		enemies = new ArrayList<EnemyRec>(Arrays.asList(new EnemyRec(200, 200, 50, 50),
				new EnemyRec(250, 200, 50, 50), new EnemyRec(300, 200, 50, 50),
				new EnemyRec(350, 200, 50, 50), new EnemyRec(400, 200, 50, 50),
				new EnemyRec(450, 200, 50, 50), new EnemyRec(500, 200, 50, 50)));
		enemyNum = enemies.size();
		gameObjects.addAll(enemies);
		collisionList.addAll(enemies);

		ChaserEnemyXRec chaserX = new ChaserEnemyXRec(550, 200, 50, 50, player);
		gameObjects.add(chaserX);
		collisionList.add(chaserX);

		ChaserEnemyYRec chaserY = new ChaserEnemyYRec(700, 200, 50, 50, player);
		gameObjects.add(chaserY);
		collisionList.add(chaserY);

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
			if (timeToNextEnemy++ == 1000) {
				double randDouble = Math.random()*40 +10;
				EnemyRec temp = new EnemyRec(50 + Math.random() * 1150, 50 + Math.random() * 650, randDouble, randDouble);
				boolean isStuck = false;
				do {
					isStuck = false;

					for (int i = 0; i < collisionList.size(); i++) {// -1 to exclude the newly added temp rec
						if (collisionList.get(i).checkCollision(temp))
							isStuck = true;
					}
					Bounds pB = collisionList.get(0).getBoundsInParent();// get player bounds

					// check if it intersects player, but with extra space around
					if (temp.intersects(pB.getMinX() - 150, pB.getMinY() - 150, 350, 350)) {
						isStuck = true;
						System.out.println("prevented Player Spawn");
					}
					if (isStuck) {
						randDouble = Math.random()*40 +10;
						temp = new EnemyRec(50 + Math.random() * 1150, 50 + Math.random() * 650, randDouble, randDouble);
						System.out.println("Stuck spawn avoided");
					}

				} while (isStuck);

				enemies.add(temp);
				gameObjects.add(temp);
				collisionList.add(temp);
				border.getChildren().add(temp);
				for (EnemyRec x : enemies) {
					x.setSpeed(enemies.get(0).speed + 0.01);
				}

				enemyNum++;
				if (enemyNum >= 50)
					gameOver();
				timeToNextEnemy = 0;
				System.out.println(enemyNum);
			}
			score++;
		}
	}

	public static void gameOver() {
		gameRunning = false;
		Stage secondStage = new Stage();
		BorderPane border = new BorderPane();
		Scene scoreScene = new Scene(border);

		Label scoreLabel = new Label("Score: " + score / 100);// TODO: Add score
		border.setCenter(scoreLabel);
		secondStage.setScene(scoreScene);
		secondStage.show();
	}

}
