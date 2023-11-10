package model.collectibles;

import java.io.Serializable;

import engine.Game;
import model.characters.Hero;
import model.world.CharacterCell;

public class Vaccine implements Collectible,Serializable{
	public Vaccine() {
		
	}

	@Override
	public void pickUp(Hero h) {
		h.getVaccineInventory().add(this);	//Pick the Vaccine
	}
	
	@Override
	public void use(Hero h){
		
		h.getVaccineInventory().remove(this);//Remove Vaccine from inventory
		
		Game.zombies.remove(h.getTarget());//Remove Zombie from list
		
		h.setActionsAvailable(h.getActionsAvailable()-1);;//Update Actions Available
		
		//Update Heroes List
		int index = (int)(Math.random()*Game.availableHeroes.size());
		Hero curedHero = Game.availableHeroes.remove(index);
		Game.heroes.add(curedHero);
		
		//Update Locations
		curedHero.setLocation(h.getTarget().getLocation());
		h.getTarget().setLocation(null);
		
		//Setting Target to null
		for(Hero hero : Game.heroes)
			if(hero.getTarget() == h.getTarget() && hero != h)
				hero.setTarget(null);
		h.setTarget(null);
			
		//Update Map
		((CharacterCell)Game.map[curedHero.getLocation().x][curedHero.getLocation().y]).setCharacter(curedHero);	
	}
}
