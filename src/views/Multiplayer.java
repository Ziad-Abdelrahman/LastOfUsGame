package views;

import engine.Client;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Multiplayer extends VBox{
	private boolean b,n;
	private FadeTransition fadeOut;
	public Client client;
	public WaitingScreen wait;
	public Multiplayer() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		this.setStyle("-fx-background-color:black;");
		TextArea textArea = new TextArea();
		textArea.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;-fx-font-family:"+ Frame.customFont.getName() + ";-fx-font-size: 28;");
		textArea.setPrefSize(400, 10);
		textArea.setMaxWidth(400);
		textArea.setMaxHeight(10);
		Button next = new Button("next");
		Button back = new Button("back");
		back.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";  -fx-background-color: transparent;");
		next.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";  -fx-background-color: transparent;");
		this.getChildren().addAll(textArea,next,back);
		
		back.setOnMouseEntered(e->{
			incSize(back);
		});
		back.setOnMouseExited(e->{
			decSize(back);
		});
		back.setOnAction(e->{
			b=true;
			Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
			fadeOut.play();
		});
		
		next.setOnMouseEntered(e->{
			incSize(next);
		});
		next.setOnMouseExited(e->{
			decSize(next);
		});
		next.setOnAction(e->{
			Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
	    	System.out.println(textArea.getText());
	    	client = new Client(textArea.getText(), "197.52.194.220", 1371);
	    	fadeOut.play();
			
		});
		fadeOut = new FadeTransition(Duration.millis(150), this);
        fadeOut.setFromValue(10.0);
        fadeOut.setToValue(0.0);
        
		fadeOut.setOnFinished(e -> {
			FadeTransition fadeIn;
			if(b) {
				Frame.rootPane.getChildren().add(Frame.mainmenu);
				 fadeIn = new FadeTransition(Duration.millis(150),Frame.mainmenu);
				 b=false;
			}
			else {
				wait = new WaitingScreen(client,textArea.getText());
				Frame.rootPane.getChildren().add(wait);
				 fadeIn = new FadeTransition(Duration.millis(150),wait);
			}
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