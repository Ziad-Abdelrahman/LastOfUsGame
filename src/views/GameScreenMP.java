package views;

import java.util.ArrayList;

import engine.Client;
import engine.Game;
import engine.Serializer;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;

public class GameScreenMP extends BorderPane implements Runnable{
	ImageView bill,david,ellie,henry,joel,riley,tess,tommy;
	Image clicker,vaccine,box,background,boom;
	GridPane gameScreen;
	VBox heroInfoScreen;
	VBox targetInfoScreen;
	Hero selectedHero;
	ColumnConstraints column1;
	RowConstraints row1;
	StackPane gameholder = new StackPane();
	GridPane gameBackground = new GridPane();
	ImageView optionsbutton ;
	BorderPane rightpart = new BorderPane();
	HBox aliveheroes = new HBox();
	GridPane gameeffects = new GridPane();
	public static boolean free;
	public int gamecell = Frame.frameheight/15;
	public static int killcount = 1;
	public static int eastereggcount = 10;
	public boolean running = true;
	public Thread run,listen;
	Client client;
	String name;
	int herosize;
	int herompsize;
	boolean explorer = false;
	EndTurnWait end;
	public GameScreenMP(String s,Client client,String name) {
		this.name=name;
		this.client=client;
		run = new Thread(this, "Running");
		run.start();
		if(((Hero)((CharacterCell)Game.map[0][0]).getCharacter()).getPlayerName().equals(name)) {
			Game.heroes.clear();
			Game.heroes.add(((Hero)((CharacterCell)Game.map[0][0]).getCharacter()));
			System.out.println("Changed Hero 0 0");
		}
		if(((Hero)((CharacterCell)Game.map[0][14]).getCharacter()).getPlayerName().equals(name)) {
			Game.heroes.clear();
			Game.heroes.add(((Hero)((CharacterCell)Game.map[0][14]).getCharacter()));
			System.out.println("Changed Hero 0 14");
		}
		
		System.out.println("B4 Heroes Size: "+Game.heroes.size());
		System.out.println("B4 Enemy Heroes Size: "+Game.heroesmp.size());
		for(int i = 0;i<15;i++) {
			for(int j = 0;j<15;j++) {
				if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie)
					Game.zombies.add((Zombie) ((CharacterCell)Game.map[i][j]).getCharacter());
			}
		}
		selectedHero = Game.heroes.get(0);
		
		Image tmp = new Image("optionsbutton.png");		
		optionsbutton = new ImageView(tmp);
		optionsbutton.setFitHeight(30);
		optionsbutton.setFitWidth(30);
		optionsbutton.setOnMouseClicked(e->{
			free = false;
			new Options(this);
		});
		rightpart.setStyle("-fx-background-color:black;");
		
		Frame.mainmenutheme.stop();
		Frame.gametheme.seek(new Duration(0));
    	Frame.gametheme.play();
    	  Frame.gametheme.setOnEndOfMedia(new Runnable() {
              @Override
              public void run() {
                 Frame.gametheme.seek(Frame.gametheme.getStartTime());
                  Frame.gametheme.play();
              }
          });
    	
    	
		free=true;
		this.setFocusTraversable(true);
		bill = createImage("billr.png");
		david = createImage("davidr.png");
		ellie = createImage("ellier.png");
		henry = createImage("henryr.png");
		joel = createImage("joelr.png");
		riley = createImage("rileyr.png");
		tess = createImage("tessr.png");
		tommy = createImage("tommyr.png");
		clicker = new Image("clicker1.png");
		vaccine = new Image("vaccine2.png");
		box = new Image("box.png");
		
		boom = new Image("boom.png");
		ImageView boome = new ImageView(boom);
		boome.setFitHeight(gamecell);
		boome.setFitWidth(gamecell);
		ImageView bullet = new ImageView(new Image("bullet.png"));
		bullet.setFitHeight(gamecell/4);
		bullet.setFitWidth(gamecell/4);
		ImageView revive = new ImageView(new Image("revive.png"));
		revive.setFitHeight(gamecell);
		revive.setFitWidth(gamecell);
		ImageView pickup = new ImageView(new Image("pickup.png"));
		pickup.setPreserveRatio(true);
		pickup.setFitHeight(gamecell/2);
		pickup.setFitWidth(gamecell/2);
		
		gameBackground.setStyle("-fx-background-image:url("+s+");-fx-background-repeat: stretch;-fx-background-size: "+gamecell*15 +" "+ gamecell*15+";-fx-background-position: center center;");
		gameholder.setStyle("-fx-background-color: black;");
		
		column1 = new ColumnConstraints(gamecell);
	    row1 = new RowConstraints(gamecell);
		gameScreen = new GridPane();
		gameScreen.setAlignment(Pos.CENTER);
		gameBackground.setAlignment(Pos.CENTER);
		gameeffects.setAlignment(Pos.CENTER);
		gameholder.setAlignment(Pos.CENTER);
		
		gameBackground.setGridLinesVisible(true);
		this.setCenter(gameholder);
		gameholder.getChildren().addAll(gameBackground,gameScreen);
		heroInfoScreen = new VBox();
		
		heroInfoScreen.setMinWidth(300);
		heroInfoScreen.setStyle("-fx-background-color:black;");
		heroInfoScreen.setAlignment(Pos.CENTER);
		this.setLeft(heroInfoScreen);
		for(int i = 0;i<15;i++) {
			gameeffects.getRowConstraints().add(row1);
			gameeffects.getColumnConstraints().add(column1);
			gameScreen.getRowConstraints().add(row1);
			gameScreen.getColumnConstraints().add(column1);
			gameBackground.getRowConstraints().add(row1);
			gameBackground.getColumnConstraints().add(column1);
		}
		targetInfoScreen = new VBox();
		targetInfoScreen.setMinWidth(300);
		targetInfoScreen.setStyle("-fx-background-color:black;");
		targetInfoScreen.setAlignment(Pos.CENTER);
		this.setRight(rightpart);
		
		heroInfoScreen.setSpacing(20);
		targetInfoScreen.setSpacing(40);
		update();
		
		VBox alivehero = new VBox();
		alivehero.setAlignment(Pos.CENTER);
		Label aliveheroeslabel = createLabel("Alive   Heroes");
		alivehero.getChildren().addAll(aliveheroeslabel,aliveheroes);
		aliveheroes.setSpacing(10);
		aliveheroes.setAlignment(Pos.CENTER);
		
		
		rightpart.setCenter(targetInfoScreen);
		rightpart.setTop(optionsbutton);
		rightpart.setBottom(alivehero);
		
		
		BorderPane.setAlignment(optionsbutton, Pos.TOP_RIGHT);
		BorderPane.setMargin(optionsbutton,new Insets(0,25,0,0));
	
		
		this.setOnKeyReleased(e ->{
			try {
			if(free) {
			int prevhp = selectedHero.getCurrentHp();
			int prevsupcount = selectedHero.getSupplyInventory().size();
			int prevaccount = selectedHero.getVaccineInventory().size();
			if(e.getCode() == KeyBinding.Up) {
				
				selectedHero.move(Direction.UP);
				Frame.movefx.seek(new Duration(0));
		    	Frame.movefx.play();
		    	sendMap();
			}
			if(e.getCode() == KeyBinding.Down) {
				
				selectedHero.move(Direction.DOWN);
				Frame.movefx.seek(new Duration(0));
		    	Frame.movefx.play();
		    	sendMap();
			}
			if(e.getCode() == KeyBinding.Right) {
				
				selectedHero.move(Direction.RIGHT);
				Frame.movefx.seek(new Duration(0));
		    	Frame.movefx.play();
				int spaceIndex = selectedHero.getName().indexOf(" ");
				String firstName = selectedHero.getName();
				if(spaceIndex!=-1)
					firstName = selectedHero.getName().substring(0, spaceIndex);
				firstName = firstName.toLowerCase() +"r.png";
				changeHero(selectedHero.getName(),firstName);
				sendMap();
				
			}
			if(e.getCode() == KeyBinding.Left) {
				
				selectedHero.move(Direction.LEFT);
				Frame.movefx.seek(new Duration(0));
		    	Frame.movefx.play();
				int spaceIndex = selectedHero.getName().indexOf(" ");
				String firstName = selectedHero.getName();
				if(spaceIndex!=-1)
					firstName = selectedHero.getName().substring(0, spaceIndex);
				firstName = firstName.toLowerCase() +"l.png";
				changeHero(selectedHero.getName(),firstName);
				sendMap();
			}
			if(selectedHero.getCurrentHp()!=prevhp) {
				if(!gameholder.getChildren().contains(gameeffects)) {
					gameeffects.add(boome,selectedHero.getLocation().y ,14-selectedHero.getLocation().x );
					gameholder.getChildren().add(gameeffects);
					removeeffect(boome);
				}
				Frame.trapfx.seek(new Duration(0));
				Frame.trapfx.play();
				notification("TRAP!");
				
				
				
			}
			if(selectedHero.getVaccineInventory().size()!=prevaccount) {
				notification("You picked a vaccine");
				if(!gameholder.getChildren().contains(gameeffects)) {
					gameeffects.add(pickup,selectedHero.getLocation().y ,14-selectedHero.getLocation().x );
					GridPane.setHalignment(pickup, HPos.CENTER);
					GridPane.setValignment(pickup, VPos.TOP);
					gameholder.getChildren().add(gameeffects);
					removeeffect(pickup);
				}
			}
			if(selectedHero.getSupplyInventory().size()!=prevsupcount) {
				notification("You picked a supply");
				if(!gameholder.getChildren().contains(gameeffects)) {
					gameeffects.add(pickup,selectedHero.getLocation().y ,14-selectedHero.getLocation().x );
					GridPane.setHalignment(pickup, HPos.CENTER);
					GridPane.setValignment(pickup, VPos.TOP);
					gameholder.getChildren().add(gameeffects);
					removeeffect(pickup);
				}
			}
			if(e.getCode() == KeyBinding.Attack) {
				selectedHero.attack();
				if(selectedHero.getTarget()!=null) {
					Frame.attackfx.seek(new Duration(0));
			    	Frame.attackfx.play();
					notification("You have attacked a zombie!");
					if(!gameholder.getChildren().contains(gameeffects)) {
						gameeffects.add(bullet,selectedHero.getTarget().getLocation().y ,14-selectedHero.getTarget().getLocation().x );
						GridPane.setHalignment(bullet, HPos.CENTER);
						gameholder.getChildren().add(gameeffects);
						removeeffect(bullet);
					}
				}
				else {
					if(killcount%eastereggcount==0) {
						Frame.eastereggfx.seek(new Duration(0));
				    	Frame.eastereggfx.play();
					}
					else {
					Frame.killfx.seek(new Duration(0));
			    	Frame.killfx.play();
					}
					killcount++;
					notification("You have killed a zombie!");
				}
				sendMap();
			}
			if(e.getCode() == KeyBinding.Cure) {
				int y = 0;
				int x = 0;
				if(selectedHero.getTarget()!=null) {
					y = selectedHero.getTarget().getLocation().y;
					x = selectedHero.getTarget().getLocation().x;
				}
				selectedHero.cure();
				((Hero)((CharacterCell)Game.map[x][y]).getCharacter()).setPlayerName(name);
				notification("You have cured a zombie!");
				if(!gameholder.getChildren().contains(gameeffects)) {
					gameeffects.add(revive,y ,14-x );
					GridPane.setHalignment(revive, HPos.CENTER);
					gameholder.getChildren().add(gameeffects);
					removeeffect(revive);
				}
				Frame.curefx.seek(new Duration(0));
		    	Frame.curefx.play();
		    	sendMap();
			}
			if(e.getCode() == KeyBinding.EndTurn) {
				Serializer ser = new Serializer();
		    	herosize = Game.heroes.size();
		    	herompsize = Game.heroesmp.size();
				notification("You have ended your turn!");
				client.send(ser.serialize(new ArrayList()));
				Frame.endturnfx.seek(new Duration(0));
		    	Frame.endturnfx.play();
		    	end = new EndTurnWait(this);
		    	Frame.rootPane.getChildren().add(end);
		    	
			}
			if(e.getCode() == KeyBinding.Special) {
				
				notification("You have used your special!");
				selectedHero.useSpecial();
				if(selectedHero instanceof Explorer) {
					explorer = true;
				}
				Frame.specialfx.seek(new Duration(0));
		    	Frame.specialfx.play();
		    	sendMap();
			}
			}
			}
			catch(Exception e1) {
				e1.printStackTrace();
				free = false;
				Frame.rootPane.getChildren().add(new ExceptionMenu(e1.getMessage(),this));
				
				
			}
		});
		
		
		displayMap();
		
	}
	public void sendMap() {
		Serializer s = new Serializer();
		byte[] data = s.serialize(Game.map);
		client.send(data);

	}
	public void displayMap() {
		gameScreen.getChildren().clear();
		Game.heroes.clear();
		Game.heroesmp.clear();
		Game.zombies.clear();
		for(int i = 0;i<15;i++) {
			for(int j = 0;j<15;j++) {
				if(!Game.map[i][j].isVisible() && !explorer) {
					Rectangle rectangle = new Rectangle();
					rectangle.setWidth(gamecell);
					rectangle.setHeight(gamecell);
					rectangle.setStyle("-fx-background-color:black;");
					rectangle.setOpacity(0.9 );
					gameScreen.add(rectangle, j, 14-i);
				}
				else if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero) {
					ImageView hero = null;
					switch(((CharacterCell)Game.map[i][j]).getCharacter().getName()) {
					case "Joel Miller":if(gameScreen.getChildren().contains(joel)) hero=createImage("joelr.png"); else hero=joel;break;
					case "Ellie Williams": if(gameScreen.getChildren().contains(ellie)) hero=createImage("ellier.png"); else hero=ellie; break;
					case "Tess":if(gameScreen.getChildren().contains(tess)) hero=createImage("tessr.png"); else hero=tess;break;
					case "Riley Abel":if(gameScreen.getChildren().contains(riley)) hero=createImage("rileyr.png"); else hero=riley;break;
					case "Tommy Miller": if(gameScreen.getChildren().contains(tommy)) hero=createImage("tommyr.png"); else hero=tommy;break;
					case "Bill": if(gameScreen.getChildren().contains(bill)) hero=createImage("billr.png"); else hero=bill;break;
					case "David": if(gameScreen.getChildren().contains(david)) hero=createImage("davidr.png"); else hero=david;break;
					case "Henry Burell": if(gameScreen.getChildren().contains(henry)) hero=createImage("henryr.png"); else hero=henry;break;
					}
					Hero herodata = (Hero) ((CharacterCell)Game.map[i][j]).getCharacter();
					 Tooltip tooltip = new Tooltip("Name: "+herodata.getName() +"\nType: "+(herodata instanceof Fighter?"Fighter":herodata instanceof Medic?"Medic":"Explorer") +"\nHP: "+herodata.getCurrentHp()+"/"+herodata.getMaxHp()+"\nDMG: "+herodata.getAttackDmg()+"\nActions: "+herodata.getActionsAvailable()+"/"+herodata.getMaxActions());
				     Tooltip.install(hero, tooltip);
					GridPane.setHalignment(hero, HPos.CENTER);
					gameScreen.add(hero, j, 14-i);
					if(herodata.getPlayerName().equals(name)) {
						Game.heroes.add(herodata);
						if(selectedHero.getName().equals(herodata.getName())) {
							selectedHero = herodata;
							updateHeroData();
						}
					}
					else {
						Game.heroesmp.add(herodata);
					}
					hero.setOnMouseClicked(e->{
						if(e.getButton() == MouseButton.PRIMARY) {
							selectedHero.setTarget(herodata);
							Frame.clickfx.seek(new Duration(0));
					    	Frame.clickfx.play();
							update();
						}
						if(e.getButton() == MouseButton.SECONDARY) {
							if(herodata.getPlayerName().equals(name)) {
							selectedHero=herodata;
							Frame.clickfx.seek(new Duration(0));
					    	Frame.clickfx.play();
							update();
						}
						}
					});
					
					
				}
				else if(Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie){
					ImageView view = new ImageView(clicker);
					view.setFitWidth(gamecell); // set the desired width
			        view.setFitHeight(gamecell);
					gameScreen.add(view, j, 14-i);
					Zombie zombie = (Zombie) ((CharacterCell)Game.map[i][j]).getCharacter();
					Game.zombies.add(zombie);
					Tooltip tooltip = new Tooltip("Name: " +zombie.getName() + "\nHP: " +zombie.getCurrentHp() +"/" +zombie.getMaxHp()+"\nDmg: "+zombie.getAttackDmg());
					Tooltip.install(view, tooltip);
					view.setOnMouseClicked(e->{
						if(e.getButton() == MouseButton.PRIMARY) {
							selectedHero.setTarget(zombie);
							Frame.clickfx.seek(new Duration(0));
					    	Frame.clickfx.play();
							update();
						}
					});
				}
				else if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine){
					ImageView view = new ImageView(vaccine);
					GridPane.setHalignment(view, HPos.CENTER);
					view.setFitWidth(40); 
			        view.setFitHeight(40);
					gameScreen.add(view, j, 14-i);
				}
				else if(Game.map[i][j] instanceof CollectibleCell && ((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Supply){
					ImageView view = new ImageView(box);
					GridPane.setHalignment(view, HPos.CENTER);
					
					view.setFitWidth(30); 
			        view.setFitHeight(30);
					gameScreen.add(view, j, 14-i);
				}
			}
		}
		update();
		System.out.println("Heroes Size: "+Game.heroes.size());
		System.out.println("Enemy Heroes Size: "+Game.heroesmp.size());
		if(!Game.checkGameOverOnline()) {
			boolean flag = true;
			for(Hero h : Game.heroes)
				if(selectedHero.getName().equals(h.getName()))
					flag = false;
			if(flag)
				selectedHero = Game.heroes.get(0);
			
			
		}
		else {
			
			Frame.gametheme.stop();
			Frame.mainmenutheme.seek(new Duration(0));
	    	Frame.mainmenutheme.play();
			if(Game.heroes.size()>Game.heroesmp.size()) {
				Frame.winfx.seek(new Duration(0));
		    	Frame.winfx.play();
				Frame.rootPane.getChildren().remove(this);
				Frame.rootPane.getChildren().add(new WinningScreen());
			}
			else if(Game.heroes.size()==Game.heroesmp.size()) {
				Frame.winfx.seek(new Duration(0));
		    	Frame.winfx.play();
				Frame.rootPane.getChildren().remove(this);
				Frame.rootPane.getChildren().add(new DrawScreen());
			}
			else {
				Frame.losefx.seek(new Duration(0));
		    	Frame.losefx.play();
				Frame.rootPane.getChildren().remove(this);
				Frame.rootPane.getChildren().add(new LosingScreen());
			}
		}
		
	}
	public ImageView createInv(String filepath) {
		Image image = new Image(filepath);
		ImageView imageView =new ImageView(image);
		
        imageView.setFitWidth(30); // set the desired width
        imageView.setFitHeight(30); // set the desired height
      
		return imageView;
	}
	public ImageView createImage(String filepath) {
		Image image = new Image(filepath);
		ImageView imageView =new ImageView(image);
		
        imageView.setFitWidth(gamecell); // set the desired width
        imageView.setFitHeight(gamecell); // set the desired height
      
		return imageView;
	}
	public void update() {
		updateHeroData();
		updateTargetData();
		updateAliveHeroes();
	}
	public void updateHeroData(){
		heroInfoScreen.getChildren().clear();
		Label selectedherolabel = createLabel("Selected Hero");
		ImageView img = choosePortrait(selectedHero.getName());
		Label Name = createLabel("Name: " +selectedHero.getName());
		HBox hpbox = new HBox();
		hpbox.setAlignment(Pos.CENTER);
		hpbox.setSpacing(20);
		Label hp = createLabel("HP: ");
		Label curhp = new Label(selectedHero.getCurrentHp() +"/"+selectedHero.getMaxHp());
		curhp.setStyle("-fx-font-size: 10;-fx-text-fill: black;-fx-font-family: " + Frame.customFont.getName() + ";");
		StackPane stackpane = new StackPane();
		ProgressBar progressBar = new ProgressBar((double) selectedHero.getCurrentHp() / selectedHero.getMaxHp());
		progressBar.setPrefWidth(100);
	    progressBar.setStyle("-fx-accent: #AA0808;");
	    stackpane.getChildren().addAll(progressBar,curhp);
		hpbox.getChildren().addAll(hp,stackpane);
		HBox vacbox = new HBox();
		vacbox.setAlignment(Pos.CENTER);
		HBox supbox = new HBox();
		supbox.setAlignment(Pos.CENTER);
		for(int i = 0;i<selectedHero.getVaccineInventory().size();i++) {
			vacbox.getChildren().add(createInv("vaccine2.png"));
		}
		for(int i = 0;i<selectedHero.getSupplyInventory().size();i++) {
			supbox.getChildren().add(createInv("box.png"));
		}
		Label Dmg = createLabel("Dmg: " +selectedHero.getAttackDmg());
		Label Actions = createLabel("Actions: " + selectedHero.getActionsAvailable() + "/" + selectedHero.getMaxActions());
		Label type = createLabel("Type: "+(selectedHero instanceof Fighter?"Fighter":selectedHero instanceof Medic?"Medic":"Explorer"));
		
		heroInfoScreen.getChildren().addAll(selectedherolabel,img,Name,type,hpbox,Dmg,Actions,vacbox,supbox);
		
		
	}
	public void updateTargetData() {
		targetInfoScreen.getChildren().clear();
		if(selectedHero.getTarget() == null) {
			Label label = createLabel("No Target Selected");
			targetInfoScreen.getChildren().addAll(label);
		}
		else {
			Label selectedtargetlabel = createLabel("Selected Target");
			ImageView img = choosePortrait(selectedHero.getTarget().getName());
			Label Name = createLabel("Name: " +selectedHero.getTarget().getName());
			HBox hpbox = new HBox();
			Label curhp = new Label(selectedHero.getTarget().getCurrentHp() +"/"+selectedHero.getTarget().getMaxHp());
			curhp.setStyle("-fx-font-size: 10;-fx-text-fill: black;-fx-font-family: " + Frame.customFont.getName() + ";");
			StackPane stackpane = new StackPane();
			hpbox.setAlignment(Pos.CENTER);
			hpbox.setSpacing(20);
			Label hp = createLabel("HP: ");
			ProgressBar progressBar = new ProgressBar((double) selectedHero.getTarget().getCurrentHp() / selectedHero.getTarget().getMaxHp());
			progressBar.setStyle("-fx-accent: #AA0808;");
		    progressBar.setPrefWidth(100);
		    stackpane.getChildren().addAll(progressBar,curhp);
			hpbox.getChildren().addAll(hp,stackpane);
			System.out.println(selectedtargetlabel +" " +img+" "+Name+" "+hpbox);
			targetInfoScreen.getChildren().addAll(selectedtargetlabel,img,Name,hpbox);
		}
	}
	private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px;-fx-text-fill: white;-fx-font-family: " + Frame.customFont.getName() + ";");
        return label;
    }
	public ImageView choosePortrait(String x) {
		ImageView tmp = null;
		switch(x) {
		case "Joel Miller":tmp=SelectHeroMp.createImage("joel.png");break;
		case "Ellie Williams": tmp=SelectHeroMp.createImage("ellie.png"); break;
		case "Tess":tmp=SelectHeroMp.createImage("tess.png");break;
		case "Riley Abel":tmp=SelectHeroMp.createImage("riley.png");break;
		case "Tommy Miller": tmp=SelectHeroMp.createImage("tommy.png");break;
		case "Bill": tmp=SelectHeroMp.createImage("bill.png");break;
		case "David": tmp=SelectHeroMp.createImage("david.png");break;
		case "Henry Burell":tmp=SelectHeroMp.createImage("henry.png");break;
		default:tmp=SelectHeroMp.zombie;break;
		}
		return tmp;
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
	public void changeHero(String name, String filepath) {
		switch(name) {
		case "Joel Miller":joel=createImage(filepath);break;
		case "Ellie Williams": ellie=createImage(filepath); break;
		case "Tess":tess=createImage(filepath);break;
		case "Riley Abel":riley=createImage(filepath);break;
		case "Tommy Miller": tommy=createImage(filepath);break;
		case "Bill": bill=createImage(filepath);break;
		case "David":david=createImage(filepath);;break;
		case "Henry Burell":henry=createImage(filepath);break;
	}
	
}
	public void updateAliveHeroes() {
		aliveheroes.getChildren().clear();
		for(Hero i: Game.heroes) {
			ImageView tmp = null;
			switch(i.getName()) {
				case "Joel Miller":tmp=icon("joel.png");break;
				case "Ellie Williams": tmp=icon("ellie.png"); break;
				case "Tess":tmp=icon("tess.png");break;
				case "Riley Abel":tmp=icon("riley.png");break;
				case "Tommy Miller": tmp=icon("tommy.png");break;
				case "Bill": tmp=icon("bill.png");break;
				case "David":tmp=icon("david.png");;break;
				case "Henry Burell":tmp=icon("henry.png");break;
			}
			Tooltip tooltip = new Tooltip("Name: "+i.getName() +"\nType: "+(i instanceof Fighter?"Fighter":i instanceof Medic?"Medic":"Explorer") +"\nHP: "+i.getCurrentHp()+"/"+i.getMaxHp()+"\nDMG: "+i.getAttackDmg()+"\nActions: "+i.getActionsAvailable()+"/"+i.getMaxActions());
		    Tooltip.install(tmp, tooltip);
		    tmp.setOnMouseClicked(e->{
		    	if(e.getButton()==MouseButton.PRIMARY) {
		    		selectedHero=i;
		    		Frame.clickfx.seek(new Duration(0));
			    	Frame.clickfx.play();
		    	}
		    	else if(e.getButton() == MouseButton.SECONDARY)	{
		    		free = false;
		    		Frame.rootPane.getChildren().add(new Details(this,i));
		    	}
		    	
				update();
		    });
		    StackPane sp = new StackPane();
		    ProgressBar progressBar = new ProgressBar((double) i.getCurrentHp() / i.getMaxHp());
			progressBar.setPrefWidth(gamecell);
			progressBar.setPrefHeight(gamecell/5);
		    progressBar.setStyle("-fx-accent: #AA0808;");
		    sp.getChildren().addAll(tmp,progressBar);
		    StackPane.setAlignment(progressBar, Pos.BOTTOM_CENTER);
			aliveheroes.getChildren().add(sp);
		}
	}
	public ImageView icon(String filepath) {
		Image tmp = new Image(filepath);
		ImageView icon = new ImageView(tmp);
		icon.setFitHeight(50);
		icon.setFitWidth(50);
		return icon;
	}
	public void removeeffect(ImageView img) {
		Timeline removeTimeline = new Timeline();
        removeTimeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(500), event -> {
                gameholder.getChildren().remove(gameeffects);	
                gameeffects.getChildren().clear();
                }));
        removeTimeline.setCycleCount(1);
        removeTimeline.play();
	}
	
	public void run() {
		listen();
	}
public void listen() {
	listen = new Thread("Listen") {
		public void run() {
			System.out.println("listen Running");
			while (running) {
					Object obj = client.receive();
					if(obj instanceof Cell[][]) {
					Game.map=(Cell[][]) obj;
					System.out.println("Map Update Received");
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							displayMap();
							System.out.println("After Update");
						}
						
					});
					}
					else {
						Game.map=((ArrayList<Cell[][]>)obj).get(0);
						System.out.println("Map Update Received After End Turn");
						explorer = false;

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								displayMap();
								if(herosize>Game.heroes.size() ) {
									Frame.deathfx.seek(new Duration(0));
							    	Frame.deathfx.play();
							    	boolean flag = true;
							    	for(Hero i:Game.heroes) {
							    		if(i.getName().equals(selectedHero.getName()))
							    			flag = false;
							    	}
							    	if(flag) {
							    		selectedHero = Game.heroes.get(0);
							    	}
								}
								if(herompsize>Game.heroesmp.size()) {
									Frame.deathfx.seek(new Duration(0));
							    	Frame.deathfx.play();
								}
								GameScreenMP.this.setFocusTraversable(true);
								GameScreenMP.this.setEffect(null);
								GameScreenMP.this.free = true;
								Frame.rootPane.getChildren().remove(GameScreenMP.this.end);
							}
							
						});
					}
					
				}
			
			
		}
	};
	listen.start();
}
	
}
