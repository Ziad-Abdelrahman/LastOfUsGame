package views;


import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Settings extends BorderPane{
	
    private Label selectedLabel;
	private Polygon arrow;
	private Label label1;
	private Label label2;
	private VBox vbox;
	public Slider total,music,fx;
	public static Pane PrevScene = Frame.mainmenu;
	public FadeTransition fadeOut;
	 public Settings() {
	        this.setStyle("-fx-background-color: blue;");
	        vbox = new VBox();
	        this.setCenter(vbox);

	        vbox.setAlignment(Pos.CENTER);
	        vbox.setStyle("-fx-background-color: black;");
	        vbox.setSpacing(30);
	        total = new Slider(0, 1, 1);
	        music = new Slider(0, 1, 1);
	        fx = new Slider(0, 1, 1);
	        HBox FirstSlider = createSliderBox("Overall Volume: ", total);
	        HBox SecondSlider = createSliderBox("Music Volume: ", music);
	        HBox ThirdSlider = createSliderBox("FX Volume: ", fx);
	        total.setOnMouseDragged(e->{
	        	for(MediaPlayer i : Frame.Overall)
	        		if((Frame.Music.contains(i) && total.getValue()<music.getValue()) || (Frame.FX.contains(i) && total.getValue()<fx.getValue()))
	        			i.setVolume(total.getValue());
	        	
	        
	        	
	        });
	        
	        music.setOnMouseDragged(e->{
	        	for(MediaPlayer i : Frame.Music) {
	        		if(music.getValue()<total.getValue())
	        			i.setVolume(music.getValue());
	        	}
	        });
	        
	        fx.setOnMouseDragged(e->{
	        	for(MediaPlayer i : Frame.FX) {
	        		if(fx.getValue()<total.getValue())
	        			i.setVolume(fx.getValue());
	        	}
	        });
	        
	        label1 = createLabel("Key Binds");
	        label2 = createLabel("Back");

	        vbox.getChildren().addAll(FirstSlider, SecondSlider, ThirdSlider, label1, label2);

	        // Set the initial selection
	        

	        
	        // Add keyboard navigation event handlers
	        
	        this.setOnKeyPressed(event -> {
	            if (event.getCode() == KeyCode.UP) {
	                selectPreviousLabel();
	                event.consume();
	            } else if (event.getCode() == KeyCode.DOWN) {
	                selectNextLabel();
	                event.consume();
	            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
	                if (selectedLabel == label1) {
	                	Frame.clickfx.seek(new Duration(0));
	        	    	Frame.clickfx.play();
	        	    	fadeOut.play();
	                	
	                } else if (selectedLabel == label2) {
	                	Frame.backfx.seek(new Duration(0));
	        	    	Frame.backfx.play();
	        	    	fadeOut.play();
	                }
	                event.consume();
	            }
	        });

	        fadeOut = new FadeTransition(Duration.millis(150), this);
	        fadeOut.setFromValue(10.0);
	        fadeOut.setToValue(0.0);
	        fadeOut.setOnFinished(e -> {
	        	FadeTransition fadeIn;
	        	
	        	if(selectedLabel == label1) {
                	Frame.rootPane.getChildren().add(Frame.KeyBinds);
	        		fadeIn = new FadeTransition(Duration.millis(150),Frame.KeyBinds);
	        	}
	        	else {
	        		 Frame.rootPane.getChildren().add(PrevScene);
	         	    fadeIn = new FadeTransition(Duration.millis(150),PrevScene );
	        	}
	            Frame.rootPane.getChildren().remove(this);
	            fadeIn.setFromValue(0.5);
	            fadeIn.setToValue(1.0);
	            fadeIn.play();
	        });
	    }

	    private HBox createSliderBox(String labelText, Slider slider) {
	        HBox sliderBox = new HBox();
	        Label label = new Label(labelText);
	        label.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
	        

	        slider.setMinSize(300, 10);
	        sliderBox.setAlignment(Pos.CENTER);
	        slider.setStyle("-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
	        sliderBox.getChildren().addAll(label, slider);
	        return sliderBox;
	       
	    }
	    

	    private Label createLabel(String text) {
	        Label label = new Label(text);
	        label.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");

	        label.setOnMouseEntered(event -> {
	            selectLabel(label);
	        });

	        label.setOnMouseClicked(event -> {
	               if(label==label1) {
	            	   Frame.selectfx.seek(new Duration(0));
	       	    		Frame.selectfx.play();
	       	    		fadeOut.play();
	               }
	               if(label==label2) {
	            	   Frame.backfx.seek(new Duration(0));
	       	    		Frame.backfx.play();
	            	  fadeOut.play();
	               }
	            
	        });

	        return label;
	    }

	    public void selectLabel(Label label) {
	    	Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
	        if (selectedLabel != null && !selectedLabel.equals(label)) {
	            ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), selectedLabel);
	            shrinkTransition.setToX(1.0);
	            shrinkTransition.setToY(1.0);
	            shrinkTransition.play();
	        }

	        ScaleTransition growTransition = new ScaleTransition(Duration.millis(200), label);
	        growTransition.setToX(1.2);
	        growTransition.setToY(1.2);
	        growTransition.play();

	        selectedLabel = label;
	        updateArrowPosition();
	    }

	    public void selectPreviousLabel() {
	        VBox menu = (VBox) this.getChildren().get(0);
	        if(selectedLabel != null) {
	        int selectedIndex = menu.getChildren().indexOf(selectedLabel);
	        int previousIndex = (selectedIndex - 1 + menu.getChildren().size()) % menu.getChildren().size();
	        if (menu.getChildren().get(previousIndex) instanceof Label)
	            selectLabel((Label) menu.getChildren().get(previousIndex));
	        }
	        else
	        	selectLabel(label1);
	    }

	    public void selectNextLabel() {
	        VBox menu = (VBox) this.getChildren().get(0);
	        if(selectedLabel != null) {
	        int selectedIndex = menu.getChildren().indexOf(selectedLabel);
	        int nextIndex = (selectedIndex + 1) % menu.getChildren().size();
	        if (menu.getChildren().get(nextIndex) instanceof Label)
	            selectLabel((Label) menu.getChildren().get(nextIndex));
	        }
	        else {
	        	selectLabel(label1);
	        }
	        
	    }

	    private void updateArrowPosition() {
	        if (arrow != null) {
	            this.getChildren().remove(arrow);
	        }

	        arrow = new Polygon(0, 0, -10, -5, -10, 5);
	        arrow.setFill(Color.WHITE);

	        double labelWidth = selectedLabel.getWidth();
	        double labelHeight = selectedLabel.getHeight();
	        double arrowX = selectedLabel.getLayoutX() -30;
	        double arrowY = selectedLabel.getLayoutY() + (labelHeight / 2)+vbox.getLayoutY();

	        arrow.setTranslateX(arrowX);
	        arrow.setTranslateY(arrowY);

	        this.getChildren().add(arrow);
	    } 
}
