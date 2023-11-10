package model.characters;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
public abstract class Hero extends Character implements Serializable{
	private static final long serialVersionUID = 1L;
	private String playerName = "";
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;
	
	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable=maxActions;
		vaccineInventory = new ArrayList<Vaccine>();
		supplyInventory = new ArrayList<Supply>();
	}
	
	public void setVisibilityBlock() {
		//Looping on adjacentBlock and Setting Visible to true
		for(Point i : adjacentBlock()) 
				Game.map[i.x][i.y].setVisible(true);
		Game.map[getLocation().x][getLocation().y].setVisible(true);
	}
	
	public void move(int x,int y) throws MovementException {
		//Exceptions
		if(Game.outOfBounds(new Point(x,y)))
			throw new MovementException("Out of Bounds");
		else if(Game.map[x][y] instanceof CharacterCell && ((CharacterCell)Game.map[x][y]).getCharacter()!=null)
			throw new MovementException("Character in the way");
		
		//If Collectible Cell
		else if(Game.map[x][y] instanceof CollectibleCell)
			((CollectibleCell)Game.map[x][y]).getCollectible().pickUp(this);
		
		//if Trap Cell
		else if(Game.map[x][y] instanceof TrapCell)
			setCurrentHp(getCurrentHp()-((TrapCell)Game.map[x][y]).getTrapDamage());
		
		//set new Location
		setLocation(new Point(x,y));
		
		//Updating ActionsAvailable
		actionsAvailable--;
		
	}
	
	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		//actions available exception
		if(actionsAvailable <= 0)
			throw new NotEnoughActionsException("You dont have enough actions to move");
		
		//storing old location
		int x = getLocation().x;
		int y = getLocation().y;
		
		//moving in direction
		switch(d){
			case UP:move(x+1,y);break;
			
			case DOWN:move(x-1,y);break;
			
			case LEFT:move(x,y-1);break;
			
			case RIGHT:move(x,y+1);break;
		}
		
		//updating map
		((CharacterCell)Game.map[x][y]).setCharacter(null);
		Game.map[getLocation().x][getLocation().y] = new CharacterCell(this);
		
		//setting visibility
		if(getCurrentHp()==0) {
			Game.map[getLocation().x][getLocation().y].setVisible(true);
			onCharacterDeath();
		}
		else
			this.setVisibilityBlock();
	}
	
	public void cure() throws InvalidTargetException, NoAvailableResourcesException ,NotEnoughActionsException{
		//Exceptions
		if(actionsAvailable <= 0) 
			throw new NotEnoughActionsException("Not Enough actions to cure this zombie");
		else if(vaccineInventory.isEmpty())
			throw new NoAvailableResourcesException("You need Vaccine");
		else if(getTarget() == null)
			throw new InvalidTargetException("You havent chosen a target");
		else if(getTarget() instanceof Hero)
			throw new InvalidTargetException("He is alr cured");
		else if(!adjacentBlock().contains(getTarget().getLocation())) 
			throw new InvalidTargetException("Your Target is too far");
		
		else 
			vaccineInventory.get(0).use(this);//using the vaccine
	}
	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		//Exceptions
		if(supplyInventory.isEmpty())
			throw new NoAvailableResourcesException("You dont have any supply");
		
		else 
			supplyInventory.get(0).use(this);//use Supply
		
	}
	public void attack() throws NotEnoughActionsException, InvalidTargetException{
		//Exceptions
		if(actionsAvailable<=0 && !(this instanceof Fighter && this.isSpecialAction()))
			throw new NotEnoughActionsException("Not Enough Actions to perform this attack");
		else if(getTarget() == null)
			throw new InvalidTargetException("You havent chosen a target");
		else if(getTarget() instanceof Hero) 
			throw new InvalidTargetException("You cannt attack another hero");
		else if(!adjacentBlock().contains(getTarget().getLocation())) 
			throw new InvalidTargetException("Your Target is too far");
		
		if(!(this instanceof Fighter && this.isSpecialAction()))
			actionsAvailable--;//Updating Actions Available
		super.attack();//Attacking
	}
	
	public void onCharacterDeath() {
		Game.heroes.remove(this);//Updating Heroes list
		super.onCharacterDeath();//Updating Map
		for(Zombie zombie : Game.zombies)
			if(zombie.getTarget() == this)
				zombie.setTarget(null);
	}
	
	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}
	
}
