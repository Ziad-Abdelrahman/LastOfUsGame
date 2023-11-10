package views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class EndTurnWait extends StackPane {
	public EndTurnWait(GameScreenMP game) {
		BoxBlur blur = new BoxBlur(5, 5, 4);
		game.free=false;
		game.setEffect(blur);
		game.setFocusTraversable(false);
		this.setFocusTraversable(true);
		Label label = new Label("Waiting for other player to end turn");
		label.setStyle("-fx-font-size: 28px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		this.getChildren().addAll(label);
	
	}
}
