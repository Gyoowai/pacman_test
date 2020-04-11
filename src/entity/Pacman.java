package entity;

import entity.base.Entity;
import logic.Direction;
import logic.GameController;
import logic.Sprites;

public class Pacman extends Entity
{
	private Direction dir;
	
	@Override
	public int getSymbol()
	{
		if(GameController.isPowerUp()) {
			return Sprites.COMPACTOR_ON;
		}
		return Sprites.COMPACTOR_OFF;
	}
	
	public Direction getDirection() {
		return dir;
	}
	
	public boolean step(Direction direction) {
		this.move(direction);
		
		return true;
	}

}
