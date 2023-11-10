package views;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import engine.Client;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Frame extends Application{
	public static Font customFont;
	public static StackPane rootPane = new StackPane();
	public static Stage stage;
	public static MainMenu mainmenu;
	public static Settings settings;
	public static KeyBinding KeyBinds;
	public static MediaPlayer mainmenutheme,selectfx,startgamefx,clickfx,backfx,swapfx,winfx,losefx,trapfx,specialfx,movefx,killfx,deathfx,pickupfx,curefx,attackfx,endturnfx,eastereggfx,gametheme,errorfx;
	public static ArrayList<MediaPlayer>Overall = new ArrayList<>();
	public static ArrayList<MediaPlayer>Music = new ArrayList<>();
	public static ArrayList<MediaPlayer>FX = new ArrayList<>();
	public static int frameheight;
	public static int framewidth;
	public static Start start;
	public void start(Stage prim) throws Exception {
		Image cursor = new Image("cursor.png");
		rootPane.setCursor(new ImageCursor(cursor));
		rootPane.setStyle("-fx-background-color:black;");
		
		mainmenutheme = createSong("Game FX Design - Main Menu Theme Song.wav");
		selectfx = createSong("Game FX Design - Select FX.wav");
		startgamefx = createSong("Game FX Design - Start Game.wav");
		clickfx = createSong("Game FX Design - Options FX.wav");
		backfx = createSong("Game FX Design - Back FX.wav");
		swapfx = createSong("Game FX Design - Swap FX.wav");
		winfx = createSong("Game FX Design - Win FX.wav");
		losefx = createSong("Game FX Design - Loose FX.wav");
		trapfx = createSong("Game FX Design - Trap FX.wav");
		specialfx = createSong("Game FX Design - Power Up.wav");
		movefx = createSong("Game FX Design - Move FX.wav");
		killfx = createSong("Game FX Design - Kill FX.wav");
		deathfx = createSong("Game FX Design - Death FX.wav");
		pickupfx = createSong("Game FX Design - Item Pickup FX.wav");
		curefx = createSong("Game FX Design - Cure FX.wav");
		attackfx = createSong("Game FX Design - Attack FX.wav");
		endturnfx = createSong("Game FX Design - End Turn FX.wav");
		eastereggfx = createSong("Game FX Design - Easter Egg.wav");
		gametheme = createSong("Game FX Design - Gameplay Song.wav");
		errorfx = createSong("error.wav");
		
		mainmenutheme.play();
        mainmenutheme.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mainmenutheme.seek(mainmenutheme.getStartTime());
                mainmenutheme.play();
            }
        });
        loadSongs();
		stage =  prim;
		Scene mainScene = new Scene(rootPane,Double.MAX_VALUE,Double.MAX_VALUE);
		customFont = Font.loadFont(new FileInputStream("ARCADECLASSIC.TTF"), 18);
		mainmenu = new MainMenu();
		settings = new Settings();
		KeyBinds = new KeyBinding();
		
		mainmenu.setFocusTraversable(true);
        stage.setScene(mainScene);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.setResizable(false);
		stage.show();
		frameheight = (int) stage.getHeight();
		framewidth = (int) stage.getWidth();
		start = new Start();
		rootPane.getChildren().add(start);
		
	}
	public static void main(String[] args) {
		launch(args);
		
	}
	public MediaPlayer createSong(String s) {
		Media sound = new Media(new File(s).toURI().toString());
		MediaPlayer tmp = new MediaPlayer(sound);
		tmp.setOnEndOfMedia(tmp::stop);
		return tmp;
	}
	public void loadSongs() {
		Overall.add(mainmenutheme);
		Overall.add(selectfx);
		Overall.add(startgamefx);
		Overall.add(clickfx);
		Overall.add(backfx);
		Overall.add(swapfx);
		Overall.add(winfx);
		Overall.add(losefx);
		Overall.add(trapfx);
		Overall.add(specialfx);
		Overall.add(movefx);
		Overall.add(killfx);
		Overall.add(deathfx);
		Overall.add(pickupfx);
		Overall.add(curefx);
		Overall.add(attackfx);
		Overall.add(endturnfx);
		Overall.add(gametheme);
		Overall.add(errorfx);
		
		Music.add(mainmenutheme);
		Music.add(gametheme);
		
		FX.add(selectfx);
		FX.add(startgamefx);
		FX.add(clickfx);
		FX.add(backfx);
		FX.add(swapfx);
		FX.add(winfx);
		FX.add(losefx);
		FX.add(trapfx);
		FX.add(specialfx);
		FX.add(movefx);
		FX.add(killfx);
		FX.add(deathfx);
		FX.add(pickupfx);
		FX.add(curefx);
		FX.add(attackfx);
		FX.add(endturnfx);
		FX.add(errorfx);
	}
	

	
	
}
