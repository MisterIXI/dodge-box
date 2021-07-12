
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	private List<MovingRec> enemies;
	private static int enemyNum;
	private static int timeToNextEnemy;
	private static int score;

	private PlayerRec player;

	BorderPane border;

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
		border = new BorderPane();
		Scene mainScene = new Scene(border, 1300, 800);
		primaryStage.setTitle("DodgeBox");
		primaryStage.getIcons().add(new Image("icon.png"));

		player = new PlayerRec(1000, 500, 50, 50, true, Color.BLUE, this);

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

		spawnBoundaries();

		enemies = new ArrayList<>();
		fillEnemies();
		System.out.println(enemies);
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
				x.tick();
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
			if (timeToNextEnemy++ == 1000 && enemyNum < 50) {
				
				border.getChildren().add(spawnEnemy(EnemyType.Standard));
				for (MovingRec x : enemies) {
					x.setSpeed(enemies.get(0).speed + 0.01);
				}

				enemyNum++;
				timeToNextEnemy = 0;
				System.out.println(enemyNum);
			}
			score++;
		}
	}

	public void gameOver() {
		gameRunning = false;

		Stage secondStage = new Stage();
		BorderPane secondBorder = new BorderPane();
		Scene scoreScene = new Scene(secondBorder);
		secondStage.setWidth(200);
		secondStage.setHeight(150);
		Button btn_restart = new Button("Restart?");
		btn_restart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				gameObjects.clear();
				collisionList.clear();
				border.getChildren().clear();

				gameObjects.add(player);
				collisionList.add(player);
				player.setX(1000);
				player.setY(500);
				player.setFill(Color.BLUE);
				player.movingDir = new boolean[] { false, false, false, false };

				spawnBoundaries();

				// spawn all enemies back
				fillEnemies();

				// reset helper variables
				timeToNextEnemy = 0;
				score = 0;

				border.getChildren().addAll(collisionList);
				secondStage.close();
				gameRunning = true;
			}
		});
		secondBorder.setBottom(btn_restart);
		BorderPane.setMargin(btn_restart, new Insets(10));
		BorderPane.setAlignment(btn_restart, Pos.CENTER);

		Label scoreLabel = new Label("Score: " + score / 100);// TODO: Add score
		secondBorder.setCenter(scoreLabel);

		secondStage.getIcons().add(new Image("icon.png"));
		secondStage.setScene(scoreScene);
		secondStage.show();
	}

	private void fillEnemies() {
		if (enemies != null)
			enemies.clear();

		for (int i = 0; i < 7; i++) {
			spawnEnemy(EnemyType.Standard);
		}
		spawnEnemy(EnemyType.ChaserX);
		spawnEnemy(EnemyType.ChaserY);

	}

	private MovingRec spawnEnemy(EnemyType type) {
		double randDouble;
		MovingRec temp;

		boolean isStuck;
		do {
			isStuck = false;
			randDouble = Math.random() * 40 + 10;
			switch (type) {
			case ChaserX:
				temp = new ChaserEnemyXRec(50 + Math.random() * 1150, 50 + Math.random() * 650, 50, 50, player);
				break;
			case ChaserY:
				temp = new ChaserEnemyYRec(50 + Math.random() * 1150, 50 + Math.random() * 650, 50, 50, player);
				break;
			default:
			case Standard:
				temp = new EnemyRec(50 + Math.random() * 1150, 50 + Math.random() * 650, randDouble, randDouble);
				break;

			}

			for (int i = 0; i < collisionList.size(); i++) {// -1 to exclude the newly added temp rec
				if (collisionList.get(i).checkCollision(temp))
					isStuck = true;
			}
			Bounds pB = player.getBoundsInParent();// get player bounds

			// check if it intersects player, but with extra space around
			if (temp.intersects(pB.getMinX() - 150, pB.getMinY() - 150, 350, 350)) {
				isStuck = true;
				System.out.println("prevented Player Spawn");
			}
			if (isStuck) {
				System.out.println("Stuck spawn avoided");
			}

		} while (isStuck);
		enemies.add(temp);
		gameObjects.add(temp);
		collisionList.add(temp);
		enemyNum = enemies.size();
		return temp;
	}

	private void spawnBoundaries() {
		
		StillRec boundLeft = new StillRec(0, 0, 30, 800, true);
		StillRec boundTop = new StillRec(30, 0, 1270, 30, true);
		StillRec boundRight = new StillRec(1270, 30, 30, 770, true);
		StillRec boundBottom = new StillRec(30, 770, 1270, 30, true);

		collisionList.add(boundLeft);
		collisionList.add(boundTop);
		collisionList.add(boundRight);
		collisionList.add(boundBottom);

		double width = Math.random() * 100 + 50d;
		double height = Math.random() * 100 + 50d;
		double posX = Math.random() * 1300;
		double posY = Math.random() * 800;
		StillRec random = new StillRec(width, height, posX, posY, true);
		while (random.intersects(player.getBoundsInParent().getMinX() - 150, player.getBoundsInParent().getMinY() - 150,
				350, 350)) {
			width = Math.random() * 200 + 30d;
			height = Math.random() * 200 + 30d;
			posX = Math.random() * 1300;
			posY = Math.random() * 800;
			random = new StillRec(width, height, posX, posY, true);
			System.out.println("prevented bad obstacle Spawn");
		}
		collisionList.add(random);
		
	}

	enum EnemyType {
		Standard, ChaserX, ChaserY, StillRec
	}
}
