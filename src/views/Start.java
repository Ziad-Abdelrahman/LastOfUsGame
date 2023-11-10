package views;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Start extends StackPane{
	FadeTransition fadeOut;
	public Start() {
		
		ImageView background = new ImageView(new Image("start.jpeg"));
		background.setFitHeight(Frame.frameheight);
		background.setFitWidth(Frame.framewidth);
		Label label = new Label("Click anywhere to start");
		label.setStyle(getAccessibleHelp());
		label.setStyle("-fx-font-size: 40px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		StackPane.setMargin(label,new Insets(0,0,30,0));
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
		
		StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
		
		this.setFocusTraversable(true);
		this.setOnMouseClicked(e->{
			gomainmenu();
		});
		this.setOnKeyPressed(e->{
			gomainmenu();
		});
		this.getChildren().addAll(background,label);
		
		
		fadeOut = new FadeTransition(Duration.millis(150), this);
        fadeOut.setFromValue(10.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
        	Frame.rootPane.getChildren().add(Frame.mainmenu);
            Frame.rootPane.getChildren().remove(this);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(150), Frame.mainmenu);
            fadeIn.setFromValue(0.5);
            fadeIn.setToValue(1.0);
            
            fadeIn.play();
            
            
        });
       
       
	}
	
	public void gomainmenu() {
		fadeOut.play();
		
	}
}
