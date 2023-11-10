package views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;

public class Details extends StackPane{
	public Details(GameScreen game,Hero selectedHero) {
		BoxBlur blur = new BoxBlur(5, 5, 4);
		game.setEffect(blur);
		game.setFocusTraversable(false);
		this.setFocusTraversable(true);
		Image img = new Image("help.png");
		ImageView view = new ImageView(img);
		VBox vbox = new VBox();
		vbox.setSpacing(20);
		Label playercard = createLabel("Hero Card");
		playercard.setStyle("-fx-font-size: 48px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		Label Name = createLabel("Name: " +selectedHero.getName());
		Label hp = createLabel("HP: "+selectedHero.getCurrentHp()+"/"+selectedHero.getMaxHp());
		Label Dmg = createLabel("Dmg: " +selectedHero.getAttackDmg());
		Label Actions = createLabel("Actions: " + selectedHero.getActionsAvailable() + "/" + selectedHero.getMaxActions());
		Label type = createLabel("Type: "+(selectedHero instanceof Fighter?"Fighter":selectedHero instanceof Medic?"Medic":"Explorer"));
		Button back = new Button("back");
		vbox.setAlignment(Pos.CENTER);
		back.setStyle("-fx-font-size: 28;-fx-text-fill: black;-fx-font-family: " + Frame.customFont.getName() + ";  -fx-background-color: transparent;");
		vbox.getChildren().addAll(playercard,Name,type,hp,Dmg,Actions,back);
		this.getChildren().addAll(view,vbox);
		back.setOnMouseEntered(e->{
			LosingScreen.incSize(back);
		});
		back.setOnMouseExited(e->{
			LosingScreen.decSize(back);
		});
		back.setOnMouseClicked(e->{
			Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
	    	Frame.rootPane.getChildren().remove(this);
			game.setFocusTraversable(true);
			game.setEffect(null);
			game.free = true;
		});
		Frame.rootPane.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER || e.getCode()== KeyCode.SPACE) {
				Frame.rootPane.getChildren().remove(this);
				game.setEffect(null);
				game.setFocusTraversable(true);
				game.free = true;
			}
				
		});
	}
	
	public Details(GameScreenMP game, Hero selectedHero) {
		BoxBlur blur = new BoxBlur(5, 5, 4);
		game.setEffect(blur);
		game.setFocusTraversable(false);
		this.setFocusTraversable(true);
		Image img = new Image("help.png");
		ImageView view = new ImageView(img);
		VBox vbox = new VBox();
		vbox.setSpacing(20);
		Label playercard = createLabel("Hero Card");
		playercard.setStyle("-fx-font-size: 48px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		Label Name = createLabel("Name: " +selectedHero.getName());
		Label hp = createLabel("HP: "+selectedHero.getCurrentHp()+"/"+selectedHero.getMaxHp());
		Label Dmg = createLabel("Dmg: " +selectedHero.getAttackDmg());
		Label Actions = createLabel("Actions: " + selectedHero.getActionsAvailable() + "/" + selectedHero.getMaxActions());
		Label type = createLabel("Type: "+(selectedHero instanceof Fighter?"Fighter":selectedHero instanceof Medic?"Medic":"Explorer"));
		Button back = new Button("back");
		vbox.setAlignment(Pos.CENTER);
		back.setStyle("-fx-font-size: 28;-fx-text-fill: black;-fx-font-family: " + Frame.customFont.getName() + ";  -fx-background-color: transparent;");
		vbox.getChildren().addAll(playercard,Name,type,hp,Dmg,Actions,back);
		this.getChildren().addAll(view,vbox);
		back.setOnMouseEntered(e->{
			LosingScreen.incSize(back);
		});
		back.setOnMouseExited(e->{
			LosingScreen.decSize(back);
		});
		back.setOnMouseClicked(e->{
			Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
	    	Frame.rootPane.getChildren().remove(this);
			game.setFocusTraversable(true);
			game.setEffect(null);
			game.free = true;
		});
		Frame.rootPane.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER || e.getCode()== KeyCode.SPACE) {
				Frame.rootPane.getChildren().remove(this);
				game.setEffect(null);
				game.setFocusTraversable(true);
				game.free = true;
			}
				
		});
	}

	private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
        return label;
    }
}
