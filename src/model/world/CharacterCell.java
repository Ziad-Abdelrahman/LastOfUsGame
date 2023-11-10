package model.world;
import java.io.Serializable;

import model.characters.Character;

public class CharacterCell extends Cell implements Serializable{
	private Character character;
	private boolean isSafe;
	public CharacterCell() {
		super();
	}
	public CharacterCell(Character character) {
		super();
		this.character=character;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public boolean isSafe() {
		return isSafe;
	}

	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}
		
}
