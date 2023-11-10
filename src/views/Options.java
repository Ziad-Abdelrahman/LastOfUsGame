package views;

import engine.Game;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.world.Cell;

public class Options extends StackPane{
		FadeTransition fadeOut;
		boolean c,b;
		public Options(GameScreen game) {
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(20);
			BoxBlur blur = new BoxBlur(5, 5, 4);
			game.setEffect(blur);
			game.setFocusTraversable(false);
			this.setFocusTraversable(true);
			Image img = new Image("options.png");
			ImageView view = new ImageView(img);
			Button settings = new Button("Settings");
			Button mainmenu = new Button("Main Menu");
			Button help = new Button("Help");
			settings.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
			mainmenu.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
			help.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
			vbox.getChildren().addAll(help,settings,mainmenu);
			this.getChildren().addAll(view,vbox);
			Frame.rootPane.getChildren().add(this);
			Frame.rootPane.setOnKeyPressed(e->{
				if(e.getCode() == KeyCode.ENTER || e.getCode()== KeyCode.SPACE) {
					Frame.rootPane.getChildren().remove(this);
					game.setEffect(null);
					game.setFocusTraversable(true);
					game.free = true;
				}
					
			});
			settings.setOnMouseClicked(e->{
				Frame.clickfx.seek(new Duration(0));
		    	Frame.clickfx.play();
				game.setEffect(null);
				game.setFocusTraversable(true);
				game.free = true;
				c=true;
				fadeOut.play();
			});
			settings.setOnMouseEntered(e->{
				Frame.selectfx.seek(new Duration(0));
		    	Frame.selectfx.play();
				WinningScreen.incSize(settings);
			});
			settings.setOnMouseExited(e->{
				WinningScreen.decSize(settings);
			});
			
			
			help.setOnMouseClicked(e->{
				Frame.clickfx.seek(new Duration(0));
		    	Frame.clickfx.play();
				Frame.rootPane.getChildren().remove(this);
				help.setEffect(null);
				
				Frame.rootPane.getChildren().add(new Help(game));
				
				
				
			});
			help.setOnMouseEntered(e->{
				Frame.selectfx.seek(new Duration(0));
		    	Frame.selectfx.play();
				WinningScreen.incSize(help);
			});
			help.setOnMouseExited(e->{
				WinningScreen.decSize(help);
			});
			
			
			mainmenu.setOnMouseClicked(e->{
				Frame.clickfx.seek(new Duration(0));
		    	Frame.clickfx.play();
		    	Frame.gametheme.stop();
				Frame.mainmenutheme.seek(new Duration(0));
		    	Frame.mainmenutheme.play();
				game.setFocusTraversable(true);
				game.setEffect(null);
				game.free = true;
				b=true;
				fadeOut.play();
				Settings.PrevScene=Frame.mainmenu;
         		Game.map=new Cell[15][15];
         		Game.heroes.clear();
         		Game.zombies.clear();
         		Game.availableHeroes.clear();
         		
			});
			mainmenu.setOnMouseEntered(e->{
				Frame.selectfx.seek(new Duration(0));
		    	Frame.selectfx.play();
				WinningScreen.incSize(mainmenu);
			});
			mainmenu.setOnMouseExited(e->{
				WinningScreen.decSize(mainmenu);
			});
			
			
			
			 fadeOut = new FadeTransition(Duration.millis(150), this);
		        fadeOut.setFromValue(10.0);
		        fadeOut.setToValue(0.0);
		        fadeOut.setOnFinished(e -> {
		        	FadeTransition fadeIn = null;
		        	if(c) {
		        		Settings.PrevScene=game;
		         		Frame.rootPane.getChildren().remove(game);
		         		Frame.rootPane.getChildren().add(Frame.settings);
		        		fadeIn = new FadeTransition(Duration.millis(150),Frame.settings);
		        		c=false;
		        	}
		        	else if(b) {
		        		
		        		Settings.PrevScene = Frame.mainmenu;
		         		Frame.rootPane.getChildren().remove(game);
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
		public Options(GameScreenMP game) {
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(20);
			BoxBlur blur = new BoxBlur(5, 5, 4);
			game.setEffect(blur);
			game.setFocusTraversable(false);
			this.setFocusTraversable(true);
			Image img = new Image("options.png");
			ImageView view = new ImageView(img);
			Button settings = new Button("Settings");
			Button mainmenu = new Button("Main Menu");
			Button help = new Button("Help");
			settings.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
			mainmenu.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
			help.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
			vbox.getChildren().addAll(help,settings,mainmenu);
			this.getChildren().addAll(view,vbox);
			Frame.rootPane.getChildren().add(this);
			Frame.rootPane.setOnKeyPressed(e->{
				if(e.getCode() == KeyCode.ENTER || e.getCode()== KeyCode.SPACE) {
					Frame.rootPane.getChildren().remove(this);
					game.setEffect(null);
					game.setFocusTraversable(true);
					game.free = true;
				}
					
			});
			settings.setOnMouseClicked(e->{
				Frame.clickfx.seek(new Duration(0));
		    	Frame.clickfx.play();
				game.setEffect(null);
				game.setFocusTraversable(true);
				game.free = true;
				c=true;
				fadeOut.play();
			});
			settings.setOnMouseEntered(e->{
				Frame.selectfx.seek(new Duration(0));
		    	Frame.selectfx.play();
				WinningScreen.incSize(settings);
			});
			settings.setOnMouseExited(e->{
				WinningScreen.decSize(settings);
			});
			
			
			help.setOnMouseClicked(e->{
				Frame.clickfx.seek(new Duration(0));
		    	Frame.clickfx.play();
				Frame.rootPane.getChildren().remove(this);
				help.setEffect(null);
				
				Frame.rootPane.getChildren().add(new Help(game));
				
				
				
			});
			help.setOnMouseEntered(e->{
				Frame.selectfx.seek(new Duration(0));
		    	Frame.selectfx.play();
				WinningScreen.incSize(help);
			});
			help.setOnMouseExited(e->{
				WinningScreen.decSize(help);
			});
			
			
			mainmenu.setOnMouseClicked(e->{
				Frame.clickfx.seek(new Duration(0));
		    	Frame.clickfx.play();
		    	Frame.gametheme.stop();
				Frame.mainmenutheme.seek(new Duration(0));
		    	Frame.mainmenutheme.play();
				game.setFocusTraversable(true);
				game.setEffect(null);
				game.free = true;
				b=true;
				fadeOut.play();
				Settings.PrevScene=Frame.mainmenu;
         		Game.map=new Cell[15][15];
         		Game.heroes.clear();
         		Game.zombies.clear();
         		Game.availableHeroes.clear();
         		
			});
			mainmenu.setOnMouseEntered(e->{
				Frame.selectfx.seek(new Duration(0));
		    	Frame.selectfx.play();
				WinningScreen.incSize(mainmenu);
			});
			mainmenu.setOnMouseExited(e->{
				WinningScreen.decSize(mainmenu);
			});
			
			
			
			 fadeOut = new FadeTransition(Duration.millis(150), this);
		        fadeOut.setFromValue(10.0);
		        fadeOut.setToValue(0.0);
		        fadeOut.setOnFinished(e -> {
		        	FadeTransition fadeIn = null;
		        	if(c) {
		        		Settings.PrevScene=game;
		         		Frame.rootPane.getChildren().remove(game);
		         		Frame.rootPane.getChildren().add(Frame.settings);
		        		fadeIn = new FadeTransition(Duration.millis(150),Frame.settings);
		        		c=false;
		        	}
		        	else if(b) {
		        		
		        		Settings.PrevScene = Frame.mainmenu;
		         		Frame.rootPane.getChildren().remove(game);
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
}
