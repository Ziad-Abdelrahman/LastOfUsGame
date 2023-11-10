package model.world;

import java.io.Serializable;

public class TrapCell extends Cell implements Serializable{
	private int trapDamage;
	public TrapCell() {
		super();
		trapDamage=(int)(Math.random()*3+1)*10;
	}
	public int getTrapDamage() {
		return trapDamage;
	}
}
