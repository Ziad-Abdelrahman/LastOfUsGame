package views;



import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class MainMenu extends BorderPane{
	 	public Label selectedLabel;
	    private Polygon arrow;
	    private VBox vbox;
	    public Label singleplayer;
	    public Label multiplayer;
	    public Label settings;
	    public Label exit;
	    public SelectHero select;   
	    public Multiplayer multi;
	    public FadeTransition fadeOut;
	public MainMenu() {
		Image image = new Image("title.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300); // set the desired width
        imageView.setFitHeight(200); // set the desired height
        imageView.fitWidthProperty().bind(Frame.stage.widthProperty().subtract(50));
        imageView.fitHeightProperty().bind(Frame.stage.heightProperty().subtract(250));
       
        
        
        singleplayer = createLabel("Single player");
        multiplayer = createLabel("Multi player");
        settings = createLabel("Settings");
        exit = createLabel("Exit");
        
        
        singleplayer.setOnMouseClicked(e->{
        	Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
        	fadeOut.play();
     		e.consume();
        });
        multiplayer.setOnMouseClicked(e->{
        	Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
        	fadeOut.play();
     		e.consume();
        });
        settings.setOnMouseClicked(e->{
        	Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
        	fadeOut.play();
        });
        exit.setOnMouseClicked(e->{
        	Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
        	Frame.stage.close();
        });

        
        
        
        vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        
        
        vbox.getChildren().addAll(imageView,singleplayer,multiplayer,settings,exit);
        
        vbox.setSpacing(10);
        VBox.setMargin(imageView, new Insets(0, 0, 30, 0));
        vbox.setStyle("-fx-background-color: black;");
        vbox.setMaxSize(400, 600);
        this.setCenter(vbox);
        this.setStyle("-fx-background-color: black;");
        this.setOnKeyPressed(event -> {
        	 
            if (event.getCode() == KeyCode.UP) {
                selectPreviousLabel();
              
            } else if (event.getCode() == KeyCode.DOWN) {
                selectNextLabel();
                
            } else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
            	Frame.clickfx.seek(new Duration(0));
    	    	Frame.clickfx.play();
             	if(selectedLabel == singleplayer || selectedLabel == multiplayer) {
             		fadeOut.play();	
             	}
             	else if(selectedLabel == settings) {
             		Settings.PrevScene=Frame.mainmenu;
             		fadeOut.play();
             	}
             	else if(selectedLabel == exit) {
             		Frame.stage.close();
             	}
            
        	 }
        	 
            
        	 
         });
        fadeOut = new FadeTransition(Duration.millis(150), this);
        fadeOut.setFromValue(10.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
        	FadeTransition fadeIn;
        	
        	if(selectedLabel == settings) {
        		Frame.rootPane.getChildren().add(Frame.settings);
        		fadeIn = new FadeTransition(Duration.millis(150), Frame.settings);
        	}
        	else if(selectedLabel == singleplayer){
        		select = new SelectHero();
         		Frame.rootPane.getChildren().add(select);
         	    fadeIn = new FadeTransition(Duration.millis(150), select);
        	}
        	else {
        		multi = new Multiplayer();
        		Frame.rootPane.getChildren().add(multi);
        		fadeIn = new FadeTransition(Duration.millis(150), multi);
        	}
            Frame.rootPane.getChildren().remove(this);
            fadeIn.setFromValue(0.5);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
		
	}
	 private Label createLabel(String text) {
	        Label label = new Label(text);
	        label.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");

	        label.setOnMouseEntered(event -> {
	            selectLabel(label);
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
	        	selectLabel(singleplayer);
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
	        	selectLabel(singleplayer);
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
	        double arrowX = selectedLabel.getLayoutX() +375;
	        double arrowY = selectedLabel.getLayoutY() + (labelHeight / 2)+vbox.getLayoutY();

	        arrow.setTranslateX(arrowX);
	        arrow.setTranslateY(arrowY);

	        this.getChildren().add(arrow);
	    } 

}
