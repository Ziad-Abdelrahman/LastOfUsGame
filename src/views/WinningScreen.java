package views;

import engine.Game;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.world.Cell;

public class WinningScreen extends VBox{
	Label win = new Label("You Win!");
	Button back = new Button("MainMenu");
	FadeTransition fadeOut;
	public WinningScreen() {
		Game.map=new Cell[15][15];
 		Game.heroes.clear();
 		Game.zombies.clear();
 		Game.availableHeroes.clear();
		VBox.setMargin(win, new Insets(100,0,0,0));
		back.setFocusTraversable(false);
		this.setSpacing(200);
		this.setAlignment(Pos.CENTER);
		win.setStyle("-fx-font-size: 128px;-fx-text-fill: green;-fx-font-family: " + Frame.customFont.getName() + ";");
		back.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
		this.setStyle("-fx-background-color:black;");
		this.getChildren().addAll(win,back);
		back.setOnMouseEntered(e->{
			incSize(back);
		});
		back.setOnMouseExited(e->{
			decSize(back);
		});
		back.setOnMouseClicked(e->{
			Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
			fadeOut.play();
		});
		fadeOut = new FadeTransition(Duration.millis(150), this);
        fadeOut.setFromValue(10.0);
        fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> {
			Frame.rootPane.getChildren().add(Frame.mainmenu);
			FadeTransition fadeIn = new FadeTransition(Duration.millis(150),Frame.mainmenu);
            Frame.rootPane.getChildren().remove(this);
            fadeIn.setFromValue(0.5);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
	}
	public static void incSize(Button label) {
		 ScaleTransition growTransition = new ScaleTransition(Duration.millis(200), label);
	      growTransition.setToX(1.2);
	      growTransition.setToY(1.2);
	      growTransition.play();
	}
	public static void decSize(Button label) {
		ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), label);
       shrinkTransition.setToX(1.0);
       shrinkTransition.setToY(1.0);
       shrinkTransition.play();
	}

}
