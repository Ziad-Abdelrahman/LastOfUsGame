package model.characters;
import java.awt.Point;
import java.io.Serializable;
import java.util.*;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

public abstract class Character implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	public Character(String name, int maxHp, int attackDmg){
		this.name=name;
		this.maxHp=maxHp;
		this.attackDmg=attackDmg;
		currentHp=maxHp;
	}	
	
	public ArrayList<Point> adjacentBlock() {
		//Getting Adjacent Cells to Character
		int x = location.x;
		int y = location.y;
		ArrayList<Point> adjacentBlock = new ArrayList<Point>();
		for (int dx = (x > 0 ? -1 : 0); dx <= (x < 14 ? 1 : 0);++dx) 
			for (int dy = (y > 0 ? -1 : 0);dy <= (y < 14 ? 1 : 0); ++dy) 
				if (dx != 0 || dy != 0) 
					adjacentBlock.add(new Point(x + dx,y + dy));
		return adjacentBlock;
	}
	
	public void attack() throws NotEnoughActionsException,InvalidTargetException{	
		target.setCurrentHp(target.getCurrentHp()-attackDmg);//Attacking Target
		target.defend(this);//Defending
		
		//If Target is Dead
		if(target.getCurrentHp()==0) {
			target.onCharacterDeath();
		}
	}
	public void defend(Character c) {
		c.setCurrentHp(c.getCurrentHp()-(attackDmg/2));//Defending
		this.setTarget(c);
		//if c is dead
		if(c.getCurrentHp()==0) 
			c.onCharacterDeath();
			
	}
	public void onCharacterDeath() {
		((CharacterCell)Game.map[this.location.x][this.location.y]).setCharacter(null);//Updating Map
	}
	
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public int getCurrentHp() {
		return currentHp;
	}
	public void setCurrentHp(int currentHp) {
		if(currentHp<=0) 
			this.currentHp=0;
		else if(currentHp<=maxHp)
			this.currentHp = currentHp;
		else
			this.currentHp = maxHp;
	}
	public Character getTarget() {
		return target;
	}
	public void setTarget(Character target) {
		this.target = target;
	}
	public String getName() {
		return name;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public int getAttackDmg() {
		return attackDmg;
	}
	
}
