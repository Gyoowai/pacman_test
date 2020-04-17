package logic;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import entity.Ghost;
import entity.Pacman;
import javafx.application.Platform;
import javafx.geometry.Point2D;

public class GameController
{

	private static GameMap gameMap;

	private static Pacman pacman;
	private static Ghost ghost1;
	private static Ghost ghost2;

	private static int score;

	private static boolean win;

	private static boolean lose;

	private static boolean PowerUp;

	public static final int MAX_COOLDOWN_TIME = 11;

	public static void IntializeMap(String[][] map, int px, int py, int g1x, int g1y, int g2x, int g2y)
	{
		pacman = new Pacman();
		ghost1 = new Ghost();
		ghost2 = new Ghost();

		setScore(0);
		setGameWin(false);
		setPowerUp(false);

		gameMap = new GameMap(map);

		gameMap.addEntity(pacman, px, py);
		gameMap.addEntity(ghost1, g1x, g1y);
		gameMap.addEntity(ghost2, g2x, g2y);
	}

	public static void printMap()
	{
		gameMap.printMap();
	}

	public static GameMap getCurrentMap()
	{
		return gameMap;
	}

	public static void movePacman(Direction dir)
	{
		pacman.move(dir);
	}
	public static void moveGhost1() {
		if(ghost1.getX()==pacman.getX()) {
			if(ghost1.getY()>pacman.getY()) {
				setGhost1Direction(Direction.UP);
			}else {
				setGhost1Direction(Direction.DOWN);
			}
		}else if(ghost1.getY()==pacman.getY()) {
			if(ghost1.getX()>pacman.getX()) {
				setGhost1Direction(Direction.LEFT);
			}else {
				setGhost1Direction(Direction.RIGHT);
			}
		}
		moveGhost1R();
	}
	public static void moveGhost1R()
	{
		Random r = new Random();
		if (!ghost1.movePossible(getGhost1Direction()))
		{
			if (r.nextInt(4) == 0)
			{
				setGhost1Direction(Direction.UP);
			} else if (r.nextInt(4) == 1)
			{
				setGhost1Direction(Direction.DOWN);
			} else if (r.nextInt(4) == 2)
			{
				setGhost1Direction(Direction.LEFT);
			} else if (r.nextInt(4) == 3)
			{
				setGhost1Direction(Direction.RIGHT);
			}
		}ghost1.move(getGhost1Direction());

	}
	public static void moveGhost2() {
		if(ghost2.getX()==pacman.getX()) {
			if(ghost2.getY()>pacman.getY()) {
				setGhost2Direction(Direction.UP);
			}else {
				setGhost2Direction(Direction.DOWN);
			}
		}else if(ghost2.getY()==pacman.getY()) {
			if(ghost2.getX()>pacman.getX()) {
				setGhost2Direction(Direction.LEFT);
			}else {
				setGhost2Direction(Direction.RIGHT);
			}
		}
		moveGhost2R();
	}
	public static void moveGhost2R()
	{
		Random r = new Random();
		if (!ghost2.movePossible(getGhost2Direction()))
		{
			if (r.nextInt(4) == 0)
			{
				setGhost2Direction(Direction.DOWN);
			} else if (r.nextInt(4) == 1)
			{
				setGhost2Direction(Direction.UP);
			} else if (r.nextInt(4) == 2)
			{
				setGhost2Direction(Direction.LEFT);
			} else if (r.nextInt(4) == 3)
			{
				setGhost2Direction(Direction.RIGHT);
			}
		}
		ghost2.move(getGhost1Direction());
	}

	public static Direction getPacmanDirection()
	{
		return pacman.getDirection();
	}

	public static void setPacmanDirection(Direction dir)
	{	
		if(pacman.movePossible(dir)) {
			pacman.setDirection(dir);
		}
	}

	public static Direction getGhost1Direction()
	{
		return ghost1.getDirection();
	}

	public static void setGhost1Direction(Direction dir)
	{
		ghost1.setDirection(dir);
	}

	public static Direction getGhost2Direction()
	{
		return ghost2.getDirection();
	}

	public static void setGhost2Direction(Direction dir)
	{
		ghost2.setDirection(dir);
	}

	public static int getScore()
	{
		return score;
	}

	public static void setScore(int score)
	{
		GameController.score = score;
	}

	public static boolean isGameWin()
	{
		return win;
	}

	public static void setGameWin(boolean is_win)
	{
		GameController.win = is_win;
	}

	public static boolean isGameLose()
	{
		return lose;
	}

	public static void setGameLose(boolean is_lose)
	{
		GameController.lose = is_lose;
	}

	public static boolean isPowerUp()
	{
		return PowerUp;
	}

	public static void setPowerUp(boolean PowerUp)
	{
		GameController.PowerUp = PowerUp;
	}

}
