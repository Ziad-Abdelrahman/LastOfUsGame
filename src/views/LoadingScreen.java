package views;

import java.util.LinkedList;

import engine.Client;
import engine.Game;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.characters.Hero;
import model.world.Cell;

public class LoadingScreen extends VBox implements Runnable{
	private Button back = new Button("Back");
	private FadeTransition fadeOut;
	public boolean joined;
	public Timeline timeline;
	public Client client;
	public GameScreenMP game;
	public boolean running;
	public Thread run,listen;
	public String s;
	public Hero h;
	boolean flag = true;
	String name;
	public LoadingScreen(String s,Hero h,Client client,String name) {
		this.client=client;
		this.name=name;
		this.h = h;
		this.s = s;
		running = true;
		run = new Thread(this, "Running");
		run.start();
		
		
		this.setStyle("-fx-background-color:black;");
		this.setAlignment(Pos.CENTER);
		this.setSpacing(150);
		Label label = new Label("Waiting for players");
		label.setStyle(getAccessibleHelp());
		label.setStyle("-fx-font-size: 40px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		
		
		ScaleTransition scaleTransition1 = new ScaleTransition(Duration.seconds(1), label);
        scaleTransition1.setFromX(1.0);
        scaleTransition1.setFromY(1.0);
        scaleTransition1.setToX(1.5);
        scaleTransition1.setToY(1.5);
        
        // Create the scale transition for the label to decrease its size
        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(1), label);
        scaleTransition2.setFromX(1.5);
        scaleTransition2.setFromY(1.5);
        scaleTransition2.setToX(1.0);
        scaleTransition2.setToY(1.0);
        
        // Combine the scale transitions into a sequential transition
        SequentialTransition sequentialTransition = new SequentialTransition(scaleTransition1, scaleTransition2);
        sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);
        
        // Create the fade transition for the label
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        
        // Combine the sequential transition and fade transition into a parallel transition
        SequentialTransition parallelTransition = new SequentialTransition(sequentialTransition, fadeTransition);
        parallelTransition.setCycleCount(SequentialTransition.INDEFINITE);
        
        
        parallelTransition.play();
        parallelTransition.setOnFinished(e->{
        	parallelTransition.play();
        });
        
		back.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";  -fx-background-color: transparent;");
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
		this.getChildren().addAll(label,back);
	
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


@Override
	public void run() {
		listen();
		byte[] data = Client.heroToBytes(h);
		System.out.println("data sent: "+data);
		client.send(data);
	}
	public void listen() {
		listen = new Thread() {
			public void run() {
				while (running) {
					
					if(flag) {
						
						Game.map = (Cell[][])client.receive();

						flag = false;
					}
					else {
						running=false;
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								Frame.rootPane.getChildren().clear();
								game = new GameScreenMP(s,client,name);
								Frame.rootPane.getChildren().add(game);
								
							}
							
						});
					}
					
				
					
				};
			}
		};
		listen.start();
	}
}



