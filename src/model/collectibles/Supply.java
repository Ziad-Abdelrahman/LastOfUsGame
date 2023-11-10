package model.collectibles;

import java.io.Serializable;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;

public class Supply implements Collectible, Serializable{
	public Supply() {
		
	}

	@Override
	public void pickUp(Hero h) {
		h.getSupplyInventory().add(this);//Pick Supply 
	}

	@Override
	public void use(Hero h) {
		h.getSupplyInventory().remove(this);//Update Supply inventory
		h.setSpecialAction(true);//Set ActionsAvailable True
		
	}
}
