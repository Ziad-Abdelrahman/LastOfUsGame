package views;

import engine.Client;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.characters.Hero;

public class SelectMapMP extends VBox{
	Image m1,m2,m3;
	ImageView map1,map2,map3;
	public LoadingScreen load;
	public DropShadow hue = new DropShadow();
	public FadeTransition fadeOut;
	public boolean b,c;
	public Client client;
	public Hero hero;
	public String s;
	public String name;
	public SelectMapMP(Hero hero,Client client,String name) {
		this.name=name;
		this.client = client;
		this.hero = hero;
		hue.setColor(Color.WHITE);
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color:black;");
		
		Label label = new Label("Select a Map");
		label.setStyle("-fx-font-size: 48px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		
		Button back = new Button("Back");
		
		m1 = new Image("map1.png");
		m2 = new Image("map2.png");
		m3 = new Image("map3.png");
		map1 = new ImageView(m1);
		map2 = new ImageView(m2);
		map3 = new ImageView(m3);
		
		map1.setPreserveRatio(true);
        map1.setFitWidth(300);
        map1.setFitHeight(300);
        
        map2.setPreserveRatio(true);
        map2.setFitWidth(300);
        map2.setFitHeight(300);
        
        map3.setPreserveRatio(true);
        map3.setFitWidth(300);
        map3.setFitHeight(300);
        
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(150);
		hbox.getChildren().addAll(map1,map2,map3);
		this.setSpacing(100);
		this.getChildren().addAll(label,hbox,back);
		
		
		back.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";  -fx-background-color: transparent;");
		
		map1.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	 		Frame.selectfx.play();
	 		SelectHero.incSize(map1);
	 		map1.setEffect(hue);
		});
		map1.setOnMouseExited(e->{
			SelectHero.decSize(map1);
			map1.setEffect(null);
		});
		
		
		
		map2.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	 		Frame.selectfx.play();
	 		SelectHero.incSize(map2);
	 		map2.setEffect(hue);
		});
		
		map2.setOnMouseExited(e->{
			SelectHero.decSize(map2);
			map2.setEffect(null);
		});
		
		map3.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	 		Frame.selectfx.play();
	 		SelectHero.incSize(map3);
	 		map3.setEffect(hue);
		});
		
		map3.setOnMouseExited(e->{
			SelectHero.decSize(map3);
			map3.setEffect(null);
		});
		
		
		
		
		
		map1.setOnMouseClicked(e->{
			generateGame("map1.png");
		});
		
		map2.setOnMouseClicked(e->{
			generateGame("map2.png");
		});
		map3.setOnMouseClicked(e->{
			generateGame("map3.png");
		});
		
		back.setOnMouseEntered(e->{
			LosingScreen.incSize(back);
		});
		back.setOnMouseExited(e->{
			LosingScreen.decSize(back);
		});
		back.setOnMouseClicked(e->{
			Frame.backfx.seek(new Duration(0));
	    	Frame.backfx.play();
			b=true;
			fadeOut.play();
		});
		 fadeOut = new FadeTransition(Duration.millis(150), this);
	        fadeOut.setFromValue(10.0);
	        fadeOut.setToValue(0.0);
	        fadeOut.setOnFinished(e -> {
	        	FadeTransition fadeIn = null;
	        	if(c) {
	        		load = new LoadingScreen(s,hero,client,name);
	        		Frame.rootPane.getChildren().add(load);
	        		fadeIn = new FadeTransition(Duration.millis(150),load);
	        		c=false;
	        	}
	        	else if(b) {
	    			Frame.rootPane.getChildren().add(Frame.mainmenu);
	        		fadeIn = new FadeTransition(Duration.millis(150),Frame.mainmenu);
	        		b=false;
	        	}
	            Frame.rootPane.getChildren().remove(this);
	            fadeIn.setFromValue(0.5);
	            fadeIn.setToValue(1.0);
	            fadeIn.play();
	        });
		
	}
	public void generateGame(String s) {
		Frame.startgamefx.seek(new Duration(0));
 		Frame.startgamefx.play();
 		c=true;
 		this.s=s;
 		fadeOut.play();
		
 		
	}
}

