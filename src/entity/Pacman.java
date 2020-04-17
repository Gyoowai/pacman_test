package entity;

import entity.base.Entity;
import logic.Direction;
import logic.GameController;
import logic.Sprites;

public class Pacman extends Entity
{
	@Override
	public int getSymbol()
	{
		if(GameController.isPowerUp()) {
			return Sprites.COMPACTOR_ON;
		}
		return Sprites.COMPACTOR_OFF;
	}
	

}
