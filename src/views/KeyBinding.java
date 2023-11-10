package views;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class KeyBinding extends VBox {
	public static KeyCode Up = KeyCode.W;
	public static KeyCode Down = KeyCode.S;
	public static KeyCode Right = KeyCode.D;
	public static KeyCode Left = KeyCode.A;
	public static KeyCode Attack = KeyCode.E;
	public static KeyCode Cure = KeyCode.Q;
	public static KeyCode EndTurn = KeyCode.C;
	public static KeyCode Special = KeyCode.R;
	public Label UpL = new Label("UP: ");
	public Label DownL = new Label("Down: ");
	public Label RightL= new Label("Right: ");
	public Label LeftL = new Label("Left: ");
	public Label AttackL = new Label("Attack: ");
	public Label CureL = new Label("Cure: ");
	public Label EndTurnL = new Label("EndTurn: ");
	public Label SpecialL = new Label("Special: ");
	public static ImageView Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M,UP,DOWN,LEFT,RIGHT;
	public static ArrayList<ImageView>buttonsImg = new ArrayList<>();
	public static ArrayList<KeyCode>buttonsCode = new ArrayList<>();
	public static ArrayList<KeyCode>UsedCodes = new ArrayList<>();
	public Button back;
	public boolean up,down,right,left,attack,cure,endturn,special;
	public DropShadow hue = new DropShadow();
	public FadeTransition fadeOut;
	public KeyBinding() {
		Q=createImage("q.png");
		W=createImage("w.png");
		E=createImage("e.png");
		R=createImage("r.png");
		T=createImage("t.png");
		Y=createImage("y.png");
		U=createImage("u.png");
		I=createImage("i.png");
		O=createImage("o.png");
		P=createImage("p.png");
		A=createImage("a.png");
		S=createImage("s.png");
		D=createImage("d.png");
		F=createImage("f.png");
		G=createImage("g.png");
		H=createImage("h.png");
		J=createImage("j.png");
		K=createImage("k.png");
		L=createImage("l.png");
		Z=createImage("z.png");
		X=createImage("x.png");
		C=createImage("c.png");
		V=createImage("v.png");
		B=createImage("b.png");
		N=createImage("n.png");
		M=createImage("m.png");
		RIGHT=createImage("right.png");
		LEFT=createImage("left.png");
		UP=createImage("up.png");
		DOWN=createImage("down.png");
		loadarray();
		
		hue.setColor(Color.WHITE);
		back = new Button("Back");
		back.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
		
		back.setOnMouseClicked(e->{
			Frame.backfx.seek(new Duration(0));
	    	Frame.backfx.play();
	    	fadeOut.play();
			
		});
		back.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
			incSize(back);
		});
		back.setOnMouseExited(e->{
			decSize(back);
		});
		
		
		
		
		this.setAlignment(Pos.CENTER);
		this.setSpacing(40);
		UpL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		DownL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		RightL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		LeftL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		CureL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		AttackL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		EndTurnL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		SpecialL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		HBox FirstRow = createHbox(UpL,DownL,Up,Down);
		HBox SecondRow = createHbox(RightL,LeftL,Right,Left);
		HBox ThirdRow = createHbox(AttackL,CureL,Attack,Cure);
		HBox FourthRow = createHbox(EndTurnL,SpecialL,EndTurn,Special);
		this.setStyle("-fx-background-color:black;");
		this.getChildren().addAll(FirstRow,SecondRow,ThirdRow,FourthRow,back);
		
		this.setOnKeyReleased(e->{
			
			if(!(!up&&!down&&!right&&!left&&!attack&&!cure&&!endturn&&!special)) {
				
				if(buttonsCode.contains(e.getCode()) && !UsedCodes.contains(e.getCode())) {
					if(up) {
						Up = e.getCode();
					}
					if(down) {
						Down = e.getCode();
					}
					if(right) {
						Right = e.getCode();
					}
					if(left) {
						Left = e.getCode();
					}
					if(attack) {
						Attack = e.getCode();
					}
					if(cure) {
						Cure = e.getCode();
					}
					if(endturn) {
						EndTurn = e.getCode();
					}
					if(special) {
						Special = e.getCode();
					}
					
				}
				else if(!buttonsCode.contains(e.getCode())) {
					Frame.errorfx.seek(new Duration(0));
			    	Frame.errorfx.play();
					notification("Unavailable!");
				}
				else {
					Frame.errorfx.seek(new Duration(0));
			    	Frame.errorfx.play();
					notification("Taken!");
				
				}
			}
			up = false;
			down=false;
			right = false;
			left = false;
			attack = false;
			cure = false;
			endturn = false;
			special = false;
			removeffects();
			updatescreen();
			updateList();
		
			
		});
		fadeOut = new FadeTransition(Duration.millis(150), this);
        fadeOut.setFromValue(10.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
        	FadeTransition fadeIn;
        	Frame.rootPane.getChildren().add(Frame.settings);
        	fadeIn = new FadeTransition(Duration.millis(150),Frame.settings);
        	
            Frame.rootPane.getChildren().remove(this);
            fadeIn.setFromValue(0.5);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
	}
	public HBox createHbox(Label name1,Label name2,KeyCode key1,KeyCode key2) {
		int index1 = buttonsCode.indexOf(key1);
		int index2 = buttonsCode.indexOf(key2);
		HBox hbox = new HBox();
		hbox.setSpacing(150);
		HBox firstp = new HBox();
		HBox secondp = new HBox();
		name1.setPrefWidth(200);
		name2.setPrefWidth(200);
		hbox.setAlignment(Pos.CENTER);
		firstp.getChildren().addAll(name1,buttonsImg.get(index1));
		secondp.getChildren().addAll(name2,buttonsImg.get(index2));
		hbox.getChildren().addAll(firstp,secondp);
		return hbox;
	}
	public ImageView createImage(String filePath) {
		Image temp = new Image(filePath);
		ImageView view = new ImageView(temp);
		view.setPreserveRatio(true);
        view.setFitWidth(50); // set the desired width
        view.setFitHeight(50);
        view.setOnMouseClicked(e->{
        	if(!up&&!down&&!right&&!left&&!attack&&!cure&&!endturn&&!special) {
        		Frame.selectfx.seek(new Duration(0));
    	    	Frame.selectfx.play();
        		int index = buttonsImg.indexOf(view);
        		KeyCode code = buttonsCode.get(index);
        		int availindex = UsedCodes.indexOf(code);
        		switch(availindex) {
        		case 0: up = true;break;
        		case 1: down = true;break;
        		case 2: right = true;break;
        		case 3: left = true;break;
        		case 4: attack = true;break;
        		case 5: cure = true;break;
        		case 6: endturn = true;break;
        		case 7: special = true;break;
        		}
        		incSize(view);
        		view.setEffect(hue);
        	}
        });
		return view;
	}
	public void loadarray() {
		ImageView[] tmp1 = {Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M,UP,DOWN,LEFT,RIGHT};
		for(ImageView i : tmp1) {
			buttonsImg.add(i);
		}
		KeyCode[] tmp2 = {KeyCode.Q,KeyCode.W,KeyCode.E,KeyCode.R,KeyCode.T,KeyCode.Y,KeyCode.U,KeyCode.I,KeyCode.O,KeyCode.P,KeyCode.A,KeyCode.S,KeyCode.D,KeyCode.F,KeyCode.G,KeyCode.H,KeyCode.J,KeyCode.K,KeyCode.L,KeyCode.Z,KeyCode.X,KeyCode.C,KeyCode.V,KeyCode.B,KeyCode.N,KeyCode.M,KeyCode.UP,KeyCode.DOWN,KeyCode.LEFT,KeyCode.RIGHT};
		for(KeyCode i : tmp2) {
			buttonsCode.add(i);
		}
		KeyCode[] tmp3 = {Up,Down,Right,Left,Attack,Cure,EndTurn,Special};
		for(KeyCode i : tmp3) {
			UsedCodes.add(i);
		}
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
	public static void incSize(ImageView label) {
		 ScaleTransition growTransition = new ScaleTransition(Duration.millis(200), label);
	      growTransition.setToX(1.2);
	      growTransition.setToY(1.2);
	      growTransition.play();
	}public static void decSize(ImageView label) {
		ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), label);
	      shrinkTransition.setToX(1.0);
	      shrinkTransition.setToY(1.0);
	      shrinkTransition.play();
		}

	public void updatescreen() {
		this.getChildren().clear();
		HBox FirstRow = createHbox(UpL,DownL,Up,Down);
		HBox SecondRow = createHbox(RightL,LeftL,Right,Left);
		HBox ThirdRow = createHbox(AttackL,CureL,Attack,Cure);
		HBox FourthRow = createHbox(EndTurnL,SpecialL,EndTurn,Special);
		this.getChildren().addAll(FirstRow,SecondRow,ThirdRow,FourthRow,back);
	}
	public void updateList() {
		UsedCodes.clear();
		KeyCode[] tmp3 = {Up,Down,Right,Left,Attack,Cure,EndTurn,Special};
		for(KeyCode i : tmp3) {
			UsedCodes.add(i);
		}
	}
	public void notification(String s) {
		Label label = new Label(s);
		label.setTranslateY(-335);
		label.setStyle("-fx-font-size: 24;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		
        showNotification(label);
   
        
        
    }
	
	private void showNotification(Label notificationLabel) {
		
		Frame.rootPane.getChildren().add(notificationLabel);
		 Timeline removeTimeline = new Timeline();
	        removeTimeline.getKeyFrames().add(
	                new KeyFrame(Duration.seconds(1), event -> Frame.rootPane.getChildren().remove(notificationLabel))
	        );
	        removeTimeline.setCycleCount(1);
	        removeTimeline.play();
}
	public void removeffects() {
		for(ImageView i:buttonsImg) {
			if(i.getEffect()!=null) {
				i.setEffect(null);
				decSize(i);
			}
		}
	}

}
