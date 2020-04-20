package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Cell;
import logic.Direction;
import logic.GameController;
import logic.Sprites;

import java.util.ArrayList;

import application.DrawUtil;
import entity.base.Entity;
//import entity.base.Updatable;

public class Main extends Application
{

	private int board_width;
	private int board_height;

	private int draw_originx;
	private int draw_originy;

	private String[][] gameMap;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		gameMap = CSVParser.readCSV("level.csv");

		GameController.IntializeMap(gameMap, 9, 15, 8, 9, 10, 9);
		board_width = GameController.getCurrentMap().getWidth() * 24;
		board_height = GameController.getCurrentMap().getHeight() * 24;

		draw_originx = 427 - board_width / 2;
		draw_originy = 240 - board_height / 2;

		StackPane root = new StackPane();

		Scene scene = new Scene(root, 854, 480);

		Canvas canvas = new Canvas(854, 480);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawGameBoard(gc);
		root.getChildren().add(canvas);

		// Register Event
		addEventListener(scene, gc);

//		primaryStage.setTitle("PacmanZa");
//		primaryStage.setScene(scene);
//		primaryStage.show();

		Thread t1 = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					GameController.movePacman();
					GameController.moveGhost1();
					GameController.moveGhost2();
					ArrayList<Entity> allEntity = GameController.getCurrentMap().getAllEntity();
					Platform.runLater(new Runnable()
					{
						@Override
						public void run()
						{
							drawGameBoard(gc);
						}
					});
					try
					{
						Thread.sleep(200);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t1.start();

		////////////menu////////////////
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		Text text = new Text("Fucking Ghost Eat My Coin Whyyyyyyyyyy????");
		text.setFont(Font.font("Cordia", FontWeight.NORMAL, 40));
		Button startButton = new Button("Start");
		startButton.setPrefSize(100, 40);
		Button exitButton = new Button("Exit");
		exitButton.setPrefSize(100, 40);
		vbox.getChildren().addAll(text, startButton, exitButton);

		startButton.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				primaryStage.setScene(scene);
				GameController.IntializeMap(gameMap, 9, 15, 8, 8, 10, 8);
			}
		});

		exitButton.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				Platform.exit();
				System.exit(0);
			}
		});

		Scene menu = new Scene(vbox, 854, 480);

		//////// GameOver//////////
		VBox over = new VBox(5);
		over.setAlignment(Pos.CENTER);
		Text overText = new Text("Game Over!!!!!!!!!");
		Text restartText = new Text("Press any key to restart");
		overText.setFont(Font.font("Cordia", FontWeight.NORMAL, 40));
		restartText.setFont(Font.font("Cordia", FontWeight.NORMAL, 20));
		over.getChildren().addAll(overText, restartText);

		Scene GameOver = new Scene(over, 854, 480);
		
		primaryStage.setTitle("Pacman Za");
		primaryStage.setResizable(false);
		primaryStage.setScene(menu);
		primaryStage.show();

	}

	public static void main(String[] args)
	{
		launch(args);
	}

	private void drawGameBoard(GraphicsContext gc)
	{

		// Draw Background
		gc.setFill(Color.rgb(21, 24, 31));
		gc.fillRect(0, 0, 854, 480);

		// Draw Playable Field Background
		gc.setFill(Color.BLACK);
		gc.fillRect(draw_originx, draw_originy, board_width, board_height);

		Cell[][] gameBoard = GameController.getCurrentMap().getMap();

		int x = 0;
		int y = 0;

		for (Cell[] row : gameBoard)
		{
			x = 0;
			for (Cell c : row)
			{
				if (!c.IsEmpty())
				{
					DrawUtil.drawSprite(gc, draw_originx + x * 24, draw_originy + y * 24, c.getSymbol());
				}
				x += 1;
			}
			y += 1;
		}
		DrawUtil.drawSprite(gc, draw_originx + GameController.getGhost1X() * 24,
				draw_originy + GameController.getGhost1Y() * 24, GameController.getGhost1Sprite());
		DrawUtil.drawSprite(gc, draw_originx + GameController.getGhost2X() * 24,
				draw_originy + GameController.getGhost2Y() * 24, GameController.getGhost2Sprite());
		DrawUtil.drawSprite(gc, draw_originx + GameController.getPacmanX() * 24,
				draw_originy + GameController.getPacmanY() * 24, GameController.getPacmanSprite());
		// If win, draw Congrats
		if (GameController.isGameWin())
		{
			// Darken the Screen
			gc.setGlobalAlpha(0.8);
			gc.setFill(Color.BLACK);
			gc.fillRect(draw_originx, draw_originy, board_width, board_height);
			// Revert the Alpha
			gc.setGlobalAlpha(1);
			// Draw Congratulations
			DrawUtil.drawCongrats(gc, 427, 240);
		}
	}

	private void addEventListener(Scene s, GraphicsContext gc)
	{
		s.setOnKeyPressed((event) ->
		{
			// System.out.println("KeyPressed : " + event.getCode().toString());
			KeyCode keycode = event.getCode();
			if (!GameController.isGameWin())
			{
				switch (keycode)
				{
				case A:
					GameController.setPacmanDirection(Direction.LEFT);
					break;
				case D:
					GameController.setPacmanDirection(Direction.RIGHT);
					break;
				case W:
					GameController.setPacmanDirection(Direction.UP);
					break;
				case S:
					GameController.setPacmanDirection(Direction.DOWN);
					break;
				case R:
					// GameController.IntializeMap(gameMap, 9, 15); // Reset Map
					break;
				default:
					System.out.println("Invalid Key.");
					break;
				}
			} else
			{
				Platform.exit();
				System.exit(0);
			}

			// ArrayList<Entity> allEntity = GameController.getCurrentMap().getAllEntity();
			// update here
			// drawGameBoard(gc);

		});
	}

}
