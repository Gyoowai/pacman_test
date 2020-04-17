package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Cell;
import logic.Direction;
import logic.GameController;

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
		
		primaryStage.setTitle("ProgMeth is You");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run()
			{
				while(true) {
				// TODO Auto-generated method stub
				GameController.movePacman(GameController.getPacmanDirection());
				GameController.moveGhost1();
				GameController.moveGhost2();
				ArrayList<Entity> allEntity = GameController.getCurrentMap().getAllEntity();
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						drawGameBoard(gc);
					}
				});
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			}
		});
		t1.start();

		
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
					//GameController.IntializeMap(gameMap, 9, 15); // Reset Map
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

			//ArrayList<Entity> allEntity = GameController.getCurrentMap().getAllEntity();
			// update here
			//drawGameBoard(gc);

		});
	}

}
