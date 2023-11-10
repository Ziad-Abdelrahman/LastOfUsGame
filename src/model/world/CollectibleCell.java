package model.world;

import java.io.Serializable;

import model.collectibles.Collectible;
public class CollectibleCell extends Cell implements Serializable{
	
	private Collectible collectible;
	
	public Collectible getCollectible() {
		return collectible;
	}

	public CollectibleCell(Collectible collectible) {
		super();
		this.collectible=collectible;
	}
}
