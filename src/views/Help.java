package views;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Help extends StackPane {
	public Label UpL = new Label("UP: ");
	public Label DownL = new Label("Down: ");
	public Label RightL= new Label("Right: ");
	public Label LeftL = new Label("Left: ");
	public Label AttackL = new Label("Attack: ");
	public Label CureL = new Label("Cure: ");
	public Label EndTurnL = new Label("EndTurn: ");
	public Label SpecialL = new Label("Special: ");
	public Label leftc = new Label("Set target: ");
	public Label rightc = new Label("Switch hero: ");
	public static ArrayList<ImageView>buttonsImg = new ArrayList<>();
	public static ImageView Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M,UP,DOWN,LEFT,RIGHT,leftclick,rightclick;
	public Button back;
	public Help(GameScreen game) {
		
		
		leftclick = createImage("leftc.png");
		rightclick = createImage("rightc.png");
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
		ImageView[] tmp1 = {Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M,UP,DOWN,LEFT,RIGHT};
		for(ImageView i : tmp1) {
			buttonsImg.add(i);
		}
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(25);
		back = new Button("Back");
		back.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
		
		back.setOnMouseClicked(e->{
			Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
            Frame.rootPane.getChildren().remove(this);
            game.setFocusTraversable(true);
			game.free = true;
            game.setEffect(null);
			
		});
		back.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
			LosingScreen.incSize(back);
		});
		back.setOnMouseExited(e->{
			LosingScreen.decSize(back);
		});
		
		leftc.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		rightc.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		UpL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		DownL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		RightL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		LeftL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		CureL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		AttackL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		EndTurnL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		SpecialL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		HBox ZeroRow = createHbox(leftc,rightc,leftclick,rightclick);
		ZeroRow.setSpacing(100);
		HBox.setMargin(rightc,new Insets(0,0,0,-200) );
		HBox FirstRow = createHbox(UpL,DownL,KeyBinding.Up,KeyBinding.Down);
		HBox SecondRow = createHbox(RightL,LeftL,KeyBinding.Right,KeyBinding.Left);
		HBox ThirdRow = createHbox(AttackL,CureL,KeyBinding.Attack,KeyBinding.Cure);
		HBox FourthRow = createHbox(EndTurnL,SpecialL,KeyBinding.EndTurn,KeyBinding.Special);
		vbox.getChildren().addAll(ZeroRow,FirstRow,SecondRow,ThirdRow,FourthRow,back);
		this.getChildren().addAll(vbox);
		
		
	}
	public Help(GameScreenMP game) {

		leftclick = createImage("leftc.png");
		rightclick = createImage("rightc.png");
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
		ImageView[] tmp1 = {Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M,UP,DOWN,LEFT,RIGHT};
		for(ImageView i : tmp1) {
			buttonsImg.add(i);
		}
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(25);
		back = new Button("Back");
		back.setStyle("-fx-font-size: 32;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";-fx-background-color: transparent;");
		
		back.setOnMouseClicked(e->{
			Frame.clickfx.seek(new Duration(0));
	    	Frame.clickfx.play();
            Frame.rootPane.getChildren().remove(this);
            game.setFocusTraversable(true);
			game.free = true;
            game.setEffect(null);
			
		});
		back.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
			LosingScreen.incSize(back);
		});
		back.setOnMouseExited(e->{
			LosingScreen.decSize(back);
		});
		
		leftc.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		rightc.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		UpL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		DownL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		RightL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		LeftL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		CureL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		AttackL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		EndTurnL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		SpecialL.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		HBox ZeroRow = createHbox(leftc,rightc,leftclick,rightclick);
		ZeroRow.setSpacing(100);
		HBox.setMargin(rightc,new Insets(0,0,0,-200) );
		HBox FirstRow = createHbox(UpL,DownL,KeyBinding.Up,KeyBinding.Down);
		HBox SecondRow = createHbox(RightL,LeftL,KeyBinding.Right,KeyBinding.Left);
		HBox ThirdRow = createHbox(AttackL,CureL,KeyBinding.Attack,KeyBinding.Cure);
		HBox FourthRow = createHbox(EndTurnL,SpecialL,KeyBinding.EndTurn,KeyBinding.Special);
		vbox.getChildren().addAll(ZeroRow,FirstRow,SecondRow,ThirdRow,FourthRow,back);
		this.getChildren().addAll(vbox);
		
	}
	public HBox createHbox(Label name1,Label name2,KeyCode key1,KeyCode key2) {
		int index1 = KeyBinding.buttonsCode.indexOf(key1);
		int index2 = KeyBinding.buttonsCode.indexOf(key2);
		HBox hbox = new HBox();
		name1.setPrefWidth(200);
		name2.setPrefWidth(200);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(100);
		HBox.setMargin(KeyBinding.buttonsImg.get(index1), new Insets(0,200,0,0));
		hbox.getChildren().addAll(name1,buttonsImg.get(index1),name2,buttonsImg.get(index2));
		return hbox;
	}
	
	public HBox createHbox(Label name1,Label name2,ImageView key1,ImageView key2) {
		
		HBox hbox = new HBox();
		name1.setPrefWidth(200);
		name2.setPrefWidth(200);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(100);
		HBox.setMargin(key1, new Insets(0,200,0,0));
		hbox.getChildren().addAll(name1,key1,name2,key2);
		return hbox;
	}
	public ImageView createImage(String filePath) {
		Image temp = new Image(filePath);
		ImageView view = new ImageView(temp);
		view.setPreserveRatio(true);
        view.setFitWidth(50); // set the desired width
        view.setFitHeight(50);
		return view;
	}
}
