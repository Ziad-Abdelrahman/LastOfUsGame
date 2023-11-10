package views;

import engine.Game;
import engine.Serializer;
import model.characters.Medic;
import model.world.Cell;

public class JavaFXMain {
	public static void main(String[] args) {
		Game.startGame(new Medic("hi",10,10,10));
		Serializer s = new Serializer();
		byte[] bytes = s.serialize(Game.map);
		System.out.println(bytes.length);
		Cell[][] cell = (Cell[][]) s.deserialize(bytes);
		System.out.println(cell);
	}
}
