package views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class ExceptionMenu extends StackPane {
	
	public ExceptionMenu(String s,GameScreen game) {
		BoxBlur blur = new BoxBlur(5, 5, 4);
		game.setEffect(blur);
		game.setFocusTraversable(false);
		this.setFocusTraversable(true);
		Image img = new Image("exception.png");
		ImageView view = new ImageView(img);
		
		Label label = new Label(s);
		Button bt = new Button("OK");
		
		label.setStyle("-fx-font-size: 28px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		bt.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
		label.setTranslateY(-20);
		bt.setTranslateY(40);
		
		this.getChildren().addAll(view,label,bt);
		bt.setOnMouseClicked(e->{
			Frame.rootPane.getChildren().remove(this);
			game.setFocusTraversable(true);
			game.setEffect(null);
			game.free = true;
		});
		bt.setOnMouseEntered(e->{
			WinningScreen.incSize(bt);
		});
		bt.setOnMouseExited(e->{
			WinningScreen.decSize(bt);
		});
		
		Frame.rootPane.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER || e.getCode()==KeyCode.SPACE || e.getCode() == KeyCode.ESCAPE) {
				Frame.rootPane.getChildren().remove(this);
				game.setFocusTraversable(true);
				game.setEffect(null);
				game.free = true;
			}
		});
		
	}

	public ExceptionMenu(String s, GameScreenMP game) {
		BoxBlur blur = new BoxBlur(5, 5, 4);
		game.setEffect(blur);
		game.setFocusTraversable(false);
		this.setFocusTraversable(true);
		Image img = new Image("exception.png");
		ImageView view = new ImageView(img);
		
		Label label = new Label(s);
		Button bt = new Button("OK");
		
		label.setStyle("-fx-font-size: 28px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		bt.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
		label.setTranslateY(-20);
		bt.setTranslateY(40);
		
		this.getChildren().addAll(view,label,bt);
		bt.setOnMouseClicked(e->{
			Frame.rootPane.getChildren().remove(this);
			game.setFocusTraversable(true);
			game.setEffect(null);
			game.free = true;
		});
		bt.setOnMouseEntered(e->{
			WinningScreen.incSize(bt);
		});
		bt.setOnMouseExited(e->{
			WinningScreen.decSize(bt);
		});
		
		Frame.rootPane.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER || e.getCode()==KeyCode.SPACE || e.getCode() == KeyCode.ESCAPE) {
				Frame.rootPane.getChildren().remove(this);
				game.setFocusTraversable(true);
				game.setEffect(null);
				game.free = true;
			}
		});
	}
}
