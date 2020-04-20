package entity;

import entity.base.MovingEntity;
import logic.Direction;
import logic.GameController;
import logic.Sprites;

public class Pacman extends MovingEntity
{
	public Pacman() {
		setDirection(Direction.NONE);
	}
	@Override
	public int getSymbol()
	{
		if(GameController.isPowerUp()) {
			return Sprites.COMPACTOR_ON;
		}
		return Sprites.COMPACTOR_OFF;
	}
	

}
