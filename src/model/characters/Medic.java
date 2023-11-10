package model.characters;

import java.io.Serializable;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;

public class Medic extends Hero implements Serializable{
	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name,maxHp,attackDmg,maxActions);
	}
	
	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		//Exceptions
		if(getTarget() == null)
			throw new InvalidTargetException("You havent chosen a target");
		else if(getTarget() instanceof Zombie)
			throw new InvalidTargetException("You cant heal a zombie");
		else if(!adjacentBlock().contains(getTarget().getLocation()) && !getTarget().getLocation().equals(this.getLocation()))
			throw new InvalidTargetException("Out of range");
		
		super.useSpecial();//Using Special
		getTarget().setCurrentHp(getTarget().getMaxHp());//Update HP
		setSpecialAction(false);//special action false as the special action ended
			
	}
}
