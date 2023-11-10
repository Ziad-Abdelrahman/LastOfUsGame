package views;

import java.util.ArrayList;

import engine.Client;
import engine.Game;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;

public class SelectHeroMp extends VBox{
	public static ImageView arrowleft,zombie,arrowright,bill,david,ellie,henry,joel,riley,tess,tommy,border;
	ArrayList<ImageView> heroesPics = new ArrayList<>();
	StackPane borderpane = new StackPane();
	int selectedindex = 0;
	Label welcome;
	HBox heroselect = new HBox();
	HBox nextback = new HBox();
	Label back;
	Label next;
	public Hero h;
	public SelectMapMP selectmap;
	public static DropShadow hue = new DropShadow();
	public FadeTransition fadeOut;
	public boolean c,b;
	public Client client;
	public Thread heroes;
	public String s;
	public SelectHeroMp(Client client,String s) {
		this.s = s;
		this.client = client;
		heroes = new Thread() {
			public void run() {
				while(Game.heroesmp.isEmpty()) {
					Game.heroesmp=(ArrayList<Hero>) client.receive();
				}
				System.out.println("Other Player Heroes: " + Game.heroesmp);
			}
		};
		heroes.start();
		hue.setColor(Color.ORANGE);
		
		   fadeOut = new FadeTransition(Duration.millis(150), this);
	        fadeOut.setFromValue(10.0);
	        fadeOut.setToValue(0.0);
	        fadeOut.setOnFinished(e -> {
	        	FadeTransition fadeIn = null;
	        	if(c) {
	        		h.setPlayerName(s);
	        		selectmap = new SelectMapMP(h,client,s);
	        		Frame.rootPane.getChildren().add(selectmap);
	        		fadeIn = new FadeTransition(Duration.millis(150),selectmap);
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
		this.setFocusTraversable(true);
		this.setStyle("-fx-background-color:black");
		this.setAlignment(Pos.CENTER);
		if(!Game.availableHeroes.isEmpty()) {
			Game.availableHeroes.clear();
		}
		
		try {
			Game.loadHeroes("Heros.csv");
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		welcome = new Label("Select A Hero");
		welcome.setStyle("-fx-font-size: 48px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		arrowleft = createImage("arrowleft.png");
		arrowright = createImage("arrowright.png");
		bill = createImage("bill.png");
		david = createImage("david.png");
		ellie = createImage("ellie.png");
		henry = createImage("henry.png");
		joel = createImage("joel.png");
		riley = createImage("riley.png");
		tess = createImage("tess.png");
		tommy = createImage("tommy.png");
		zombie = createImage("zombie.png");
		Image border1 = new Image("border.png");
		border =new ImageView(border1);
		border.setPreserveRatio(true);
		border.setFitWidth(700); // set the desired width
        border.setFitHeight(700);
        arrowleft.setFitWidth(75); // set the desired width
        arrowright.setFitHeight(75);
		for(Hero i : Game.availableHeroes) {
			switch(i.getName()) {
			case "Joel Miller":heroesPics.add(joel);break;
			case "Ellie Williams":heroesPics.add(ellie);break;
			case "Tess":heroesPics.add(tess);break;
			case "Riley Abel":heroesPics.add(riley);break;
			case "Tommy Miller":heroesPics.add(tommy);break;
			case "Bill":heroesPics.add(bill);break;
			case "David":heroesPics.add(david);break;
			case "Henry Burell":heroesPics.add(henry);break;
			}
		}
		back = new Label("Back");
		back.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		next = new Label("Select");
		next.setStyle("-fx-font-size: 32px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		nextback.getChildren().addAll(back,welcome,next);
		nextback.setAlignment(Pos.CENTER);
		nextback.setSpacing(300);
		heroselect.getChildren().addAll(arrowleft,heroesPics.get(selectedindex),arrowright);
		heroselect.setSpacing(150);
		heroselect.setAlignment(Pos.CENTER);
		this.setSpacing(100);
		this.getChildren().addAll(nextback,heroselect,borderpane);
		
		displayInfo();
		
		
		next.setOnMouseClicked(e->{
			Frame.startgamefx.seek(new Duration(0));
	 		Frame.startgamefx.play();
			SelectHeroMp.this.generateGame();
		});
		
		back.setOnMouseClicked(e->{
			Frame.backfx.seek(new Duration(0));
	    	Frame.backfx.play();
			SelectHeroMp.this.back();
		});
		
		next.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
			SelectHero.incSize(next);
		});
		next.setOnMouseExited(e->{
			SelectHero.decSize(next);
		});
		
		back.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
			SelectHero.incSize(back);
		});
		back.setOnMouseExited(e->{
			SelectHero.decSize(back);
		});
		
		arrowleft.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
			SelectHero.incSize(arrowleft);
			
		});
		arrowleft.setOnMouseExited(e->{
			SelectHero.decSize(arrowleft);
		});
		
		arrowright.setOnMouseEntered(e->{
			Frame.selectfx.seek(new Duration(0));
	    	Frame.selectfx.play();
			SelectHero.incSize(arrowright);
		});
		arrowright.setOnMouseExited(e->{
			SelectHero.decSize(arrowright);
		});
		
		
		
		arrowleft.setOnMouseClicked(e->{
			SelectHeroMp.this.selectLeft();
			SelectHero.pic(arrowleft);
			e.consume();
		});
		arrowright.setOnMouseClicked(e->{
			SelectHeroMp.this.selectRight();
			SelectHero.pic(arrowright);
			e.consume();
			
		});
		this.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
				SelectHeroMp.this.selectRight();
			}
			if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
				
				SelectHeroMp.this.selectLeft();
			}
			if(e.getCode() == KeyCode.ESCAPE || e.getCode() == KeyCode.BACK_SPACE) {
				Frame.backfx.seek(new Duration(0));
	    		Frame.backfx.play();
				SelectHeroMp.this.back();
			}
			if(e.getCode()==KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
				SelectHeroMp.this.generateGame();
			}
			e.consume();
		});
		}
	public static ImageView createImage(String filepath) {
		Image image = new Image(filepath);
		ImageView imageView =new ImageView(image);
		imageView.setPreserveRatio(true);
        imageView.setFitWidth(300); // set the desired width
        imageView.setFitHeight(200); // set the desired height
      
		return imageView;
	}
	public void displayInfo() {
		Label Name = createLabel("Name: " + Game.availableHeroes.get(selectedindex).getName());
		Label dmg = createLabel("Dmg: " +Game.availableHeroes.get(selectedindex).getAttackDmg());
		Label actions = createLabel("Actions: " +Game.availableHeroes.get(selectedindex).getMaxActions());
		Label hp = createLabel("HP: " +Game.availableHeroes.get(selectedindex).getMaxHp());
		Label type = createLabel("Type: " +(Game.availableHeroes.get(selectedindex) instanceof Fighter?"Fighter":Game.availableHeroes.get(selectedindex) instanceof Medic?"Medic":"Explorer"));
		Name.setPrefWidth(280);
		type.setPrefWidth(280);
		hp.setPrefWidth(280);
		borderpane.getChildren().addAll(border,Name,dmg,actions,hp,type);
		
		Name.setTranslateX(-120);
		Name.setTranslateY(-50);
		
		
		type.setTranslateX(-120);
		type.setTranslateY(0);
		
		hp.setTranslateX(-120);
		hp.setTranslateY(50);
		
		actions.setTranslateX(125);
		actions.setTranslateY(-50);
		
		dmg.setTranslateX(103);
		dmg.setTranslateY(0);
		
	}
	public Label createLabel(String desc) {
		Label label = new  Label(desc);
		 label.setStyle("-fx-font-size: 28px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
		return label;
	}
	public void selectRight() {
		Frame.swapfx.seek(new Duration(0));
    	Frame.swapfx.play();
		ImageView currentHero = heroesPics.get(selectedindex);
	    ImageView nextHero;

	    SelectHeroMp.this.heroselect.getChildren().remove(currentHero);
	    selectedindex++;
	    if (selectedindex > Game.availableHeroes.size() - 1)
	        selectedindex = 0;
	    nextHero = heroesPics.get(selectedindex);
	    SelectHeroMp.this.heroselect.getChildren().add(1,nextHero);
	    borderpane.getChildren().clear();
	    displayInfo();

	    // Animation to slide to the right
	    double slideDistance = currentHero.getBoundsInLocal().getWidth() * 1.2;
	    TranslateTransition slideTransition = new TranslateTransition(Duration.millis(200), currentHero);
	    slideTransition.setFromX(0);
	    slideTransition.setToX(slideDistance);

	    // Animation to slide from the left
	    nextHero.setTranslateX(-slideDistance);
	    TranslateTransition slideInTransition = new TranslateTransition(Duration.millis(200), nextHero);
	    slideInTransition.setFromX(-slideDistance);
	    slideInTransition.setToX(0);

	    // Scale animations
	    ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), currentHero);
	    shrinkTransition.setToX(1.0);
	    shrinkTransition.setToY(1.0);
	    ScaleTransition growTransition = new ScaleTransition(Duration.millis(200), nextHero);
	    growTransition.setToX(1.2);
	    growTransition.setToY(1.2);

	    // Play all animations together
	    ParallelTransition transition = new ParallelTransition(slideTransition, slideInTransition, shrinkTransition, growTransition);
	    transition.play();
	}
	public void selectLeft() {
			Frame.swapfx.seek(new Duration(0));
			Frame.swapfx.play();
		    ImageView currentHero = heroesPics.get(selectedindex);
		    ImageView nextHero;

		    this.heroselect.getChildren().remove(currentHero);
		    selectedindex--;
		    if (selectedindex < 0)
		        selectedindex = Game.availableHeroes.size() - 1;
		    nextHero = heroesPics.get(selectedindex);
		    this.heroselect.getChildren().add(1,nextHero);
		    borderpane.getChildren().clear();
		    displayInfo();

		    // Animation to slide to the left
		    double slideDistance = currentHero.getBoundsInLocal().getWidth() * 1.2;
		    TranslateTransition slideTransition = new TranslateTransition(Duration.millis(200), currentHero);
		    slideTransition.setFromX(0);
		    slideTransition.setToX(-slideDistance);

		    // Animation to slide from the right
		    nextHero.setTranslateX(slideDistance);
		    TranslateTransition slideInTransition = new TranslateTransition(Duration.millis(200), nextHero);
		    slideInTransition.setFromX(slideDistance);
		    slideInTransition.setToX(0);

		    // Scale animations
		    ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), currentHero);
		    shrinkTransition.setToX(1.0);
		    shrinkTransition.setToY(1.0);
		    ScaleTransition growTransition = new ScaleTransition(Duration.millis(200), nextHero);
		    growTransition.setToX(1.2);
		    growTransition.setToY(1.2);

		    // Play all animations together
		    ParallelTransition transition = new ParallelTransition(slideTransition, slideInTransition, shrinkTransition, growTransition);
		    transition.play();
		    
		    
		 
		 
	}
	public static void incSize(Label label) {
		 ScaleTransition growTransition = new ScaleTransition(Duration.millis(200), label);
	      growTransition.setToX(1.2);
	      growTransition.setToY(1.2);
	      growTransition.play();
	}
	public static void decSize(Label label) {
		ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), label);
        shrinkTransition.setToX(1.0);
        shrinkTransition.setToY(1.0);
        shrinkTransition.play();
	}
	public static void incSize(ImageView image) {
		 ScaleTransition growTransition = new ScaleTransition(Duration.millis(200), image);
	      growTransition.setToX(1.2);
	      growTransition.setToY(1.2);
	      growTransition.play();
	      image.setEffect(hue);
	}
	public static void decSize(ImageView image) {
		ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(200), image);
       shrinkTransition.setToX(1.0);
       shrinkTransition.setToY(1.0);
       shrinkTransition.play();
       image.setEffect(null);
	}
	public static void pic(ImageView img) {
		ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(50),img);
	    shrinkTransition.setToX(1.0);
	    shrinkTransition.setToY(1.0);
	    ScaleTransition growTransition = new ScaleTransition(Duration.millis(50), img);
	    growTransition.setToX(1.2);
	    growTransition.setToY(1.2);
	    SequentialTransition sequentialTransition = new SequentialTransition(shrinkTransition, growTransition);
	    sequentialTransition.play();
	}
	public void back() {
		b=true;
		fadeOut.play();
	}
	public void generateGame() {
		c=true;
		h = Game.availableHeroes.remove(selectedindex);
		h.setPlayerName(s);
		Game.heroes.add(h);
		client.send(Game.heroes);
		System.out.println("Heroes: " + Game.heroes);
		fadeOut.play();
		
 		
	}

}
