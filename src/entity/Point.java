package entity;

import entity.base.Entity;
import entity.base.Interactable;
import logic.GameController;
import logic.Sprites;

public class Point extends Entity implements Interactable
{
	@Override
	public int getSymbol() {
		return Sprites.COIN;
	}
	
	public boolean interact(Entity e) {
		if(e instanceof Pacman) {
			this.remove();
			GameController.setScore(GameController.getScore()+1);
		}
		return true;
	}

}
