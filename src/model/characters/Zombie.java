package model.characters;

import java.awt.Point;
import java.io.Serializable;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

public class Zombie extends Character implements Serializable{
	private	static int ZOMBIES_COUNT ;
	public Zombie() {
			super("Zombie "+ ++ZOMBIES_COUNT ,40 ,10);
	}
	public static int getZOMBIES_COUNT() {
		return ZOMBIES_COUNT;
	}
	
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		//Setting Zombie Target
		if(!(this.getTarget()!=null && this.adjacentBlock().contains(getTarget().getLocation()))) {
			setTarget(null);
			for(Point i : this.adjacentBlock())
				if(Game.map[i.x][i.y] instanceof CharacterCell && ((CharacterCell)Game.map[i.x][i.y]).getCharacter() instanceof Hero) {
					this.setTarget(((CharacterCell)Game.map[i.x][i.y]).getCharacter());
					break;
				}
		}
		//Attacking if target isnt null
		if(getTarget()!=null)
			super.attack();
		
		//setting Target to Null after attack
		setTarget(null);
		}
	
	public void onCharacterDeath() {
		Game.zombies.remove(this);//Updating Zombies list
		if(!Game.mapFull())
			Game.spawnZombie();//Spawning new Zombie at a different location
		super.onCharacterDeath();//Updating map
		
		//Setting Target to Null
		for(Hero hero : Game.heroes)
			if(hero.getTarget() == this)
				hero.setTarget(null);
	}
	
}
