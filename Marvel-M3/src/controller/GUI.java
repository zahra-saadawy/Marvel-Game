package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Guard;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import engine.Player;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Direction;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class GUI extends Application {

	Console console;
	int PlayerCount = 0;
	String p1;
	String p2;
	GridMap map;
	static Pane scene4L;
	Text dynamicText = new Text();
	Text dynamicExp = new Text();
	Stage primaryStage= new Stage();
	private MediaPlayer intropl;
	private MediaPlayer mediaPlayer;
	private MediaPlayer win;
	public void start(Stage stage) throws Exception {
		File intro = new File( "res/images/intro.mp4");
		Media introm = new Media(intro.toURI().toString());
		MediaView intr = new MediaView();
		intropl = new MediaPlayer(introm);
        intr.setMediaPlayer(intropl);
        intr.setVisible(false);
        intropl.setAutoPlay(true);
		 
        
		// DynamicTextEdits
		dynamicText.setTranslateX(500);
		dynamicText.setTranslateY(50);
		dynamicText.setWrappingWidth(600);
		dynamicText.setFont(new Font("Arial", 35));
		dynamicText.setTextAlignment(TextAlignment.CENTER);
		dynamicText.setFill(Color.WHITE);
		dynamicExp.setTranslateX(0);
		dynamicExp.setTranslateY(100);
		dynamicExp.setWrappingWidth(600);
		dynamicExp.setFont(new Font("Arial", 35));
		dynamicExp.setTextAlignment(TextAlignment.CENTER);
		dynamicExp.setFill(Color.RED);

//		primaryStage.setResizable(false);
		Pane root = new Pane();
		root.setPrefSize(1600, 900);
		Pane scene2L = new Pane();
		Pane scene3L = new Pane();
		scene4L = new Pane();
		scene2L.setPrefSize(1600, 900);
		scene3L.setPrefSize(1600, 900);
		scene4L.setPrefSize(1600, 900);
		Scene scene2 = new Scene(scene2L);
		Scene scene3 = new Scene(scene3L);
		Scene scene4 = new Scene(scene4L);
		primaryStage.setFullScreen(true);
		InputStream is = Files.newInputStream(Paths.get("res/images/Marvel2.png"));
		Image img = new Image(is);
		is.close();
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(900);
		imgView.setFitWidth(1600);

		// BGimage for scene2
		InputStream is2 = Files.newInputStream(Paths.get("res/images/Scene2lastedit.jpg"));
		Image img2 = new Image(is2);
		is2.close();

		ImageView imgView2 = new ImageView(img2);
		imgView2.setFitHeight(900);
		imgView2.setFitWidth(1600);

		TextField player1 = new TextField();
		player1.setPrefSize(500, 30);
		player1.setTranslateX(110);
		player1.setTranslateY(400);
		player1.setPromptText("Player 1,Please enter your name");
		player1.setOpacity(0.5);
		player1.setFocusTraversable(false);

		TextField player2 = new TextField();
		player2.setPrefSize(500, 30);
		player2.setTranslateX(110);
		player2.setTranslateY(500);
		player2.setPromptText("Player 2,Please enter your name");
		player2.setOpacity(0.5);
		player2.setFocusTraversable(false);

		StartGameButton startGame = new StartGameButton("Start");
		startGame.setTranslateX(230);
		startGame.setTranslateY(700);
		Tothebattle tothebattle = new Tothebattle("Choose Leaders");
		tothebattle.setTranslateX(1250);
		tothebattle.setTranslateY(350);
		tothebattle.setOnMouseClicked(e -> {
			if (PlayerCount != 6) {
				Text TeamCount = new Text("Please Each Choose 3 Champions");
				TeamCount.setFont(new Font("Comic Sans MS", 40));
				TeamCount.setFill(Color.RED);
				TeamCount.setTranslateX(800);
				TeamCount.setTranslateY(70);
				scene2L.getChildren().addAll(TeamCount);
				Timeline blinker = createBlinker(TeamCount);
				blinker.setOnFinished(event -> root.getChildren().remove(TeamCount));
				blinker.play();
			} else {
				handle(scene3L);
				handleboard(scene4L);
				primaryStage.setScene(scene3);
			}
		});
		startGame.setOnMouseClicked(e -> {
			
			p1 = player1.getText();
			p2 = player2.getText();
			if (player1.getText() == null || player1.getText().trim().isEmpty() || player2.getText() == null
					|| player2.getText().trim().isEmpty()) {
				Text names = new Text("Please Enter Your Names");
				names.setFont(new Font("Comic Sans MS", 30));
				names.setFill(Color.RED);
				names.setTranslateX(200);
				names.setTranslateY(650);
				root.getChildren().addAll(names);
				Timeline blinker = createBlinker(names);
				blinker.setOnFinished(event -> root.getChildren().remove(names));
				blinker.play();
			} else {
				Console.addingPlayerNames(p1, p2);
				Label l1 = new Label(Console.player2.getName());
				l1.setTranslateX(1200);
				l1.setTranslateY(550);
				l1.setPrefSize(500, 30);
				l1.setFont(new Font("Comic Sans MS", 50));
				l1.setTextFill(Color.GREY);
				Label l2 = new Label(Console.player1.getName());
				l2.setTranslateX(200);
				l2.setTranslateY(550);
				l2.setPrefSize(500, 30);
				l2.setFont(new Font("Comic Sans MS", 50));
				l2.setTextFill(Color.GREY);
				Label l14 = new Label(Console.player2.getName());
				l14.setTranslateX(1200);
				l14.setTranslateY(30);
				l14.setPrefSize(500, 30);
				l14.setFont(new Font("Comic Sans MS", 60));
				l14.setTextFill(Color.GREY);
				Label l24 = new Label(Console.player1.getName());
				l24.setTranslateX(1200);
				l24.setTranslateY(410);
				l24.setPrefSize(500, 30);
				l24.setFont(new Font("Comic Sans MS", 60));
				l24.setTextFill(Color.GREY);
				scene4L.getChildren().addAll(l24, l14);
				scene2L.getChildren().addAll(l2, l1);
				primaryStage.setScene(scene2);
			}
		});

		root.getChildren().addAll(imgView, startGame, player1, player2);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		// Scene 2 Children
		ChampionButton CA = new ChampionButton("Captain America");
		CA.setTranslateX(270);
		CA.setTranslateY(90);
		InfoBox box = new InfoBox("Captain America");
		CA.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				CA.setDisable(true);
			CA.setOpacity(0.50);
			box.setTranslateX(270);
			box.setTranslateY(290);
			scene2L.getChildren().addAll(box);
		});
		CA.setOnMouseExited(e -> {

			scene2L.getChildren().remove(box);
			// CA.setTranslateX(40);
			CA.setOpacity(1);
		});
		CA.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				CA.setTranslateX(30);
				CA.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				CA.setTranslateX(200);
				CA.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				CA.setTranslateX(370);
				CA.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				CA.setTranslateX(980);
				CA.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				CA.setTranslateX(1150);
				CA.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				CA.setTranslateX(1320);
				CA.setTranslateY(620);
			}

			CA.setDisable(true);
			Console.addChampion("Captain America", PlayerCount);
			PlayerCount++;
		});

		ChampionButton Deadpool = new ChampionButton("Deadpool");
		Deadpool.setTranslateX(430);
		Deadpool.setTranslateY(90);
		InfoBox box1 = new InfoBox("Deadpool");
		Deadpool.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Deadpool.setDisable(true);
			// Deadpool.setTranslateX(45);
			Deadpool.setOpacity(0.50);
			box1.setTranslateX(430);
			box1.setTranslateY(290);
			scene2L.getChildren().addAll(box1);
		});
		Deadpool.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box1);
			// Deadpool.setTranslateX(40);
			Deadpool.setOpacity(1);
		});
		Deadpool.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Deadpool.setTranslateX(30);
				Deadpool.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Deadpool.setTranslateX(200);
				Deadpool.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Deadpool.setTranslateX(370);
				Deadpool.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Deadpool.setTranslateX(980);
				Deadpool.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Deadpool.setTranslateX(1150);
				Deadpool.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Deadpool.setTranslateX(1320);
				Deadpool.setTranslateY(620);
			}

			Deadpool.setDisable(true);
			Console.addChampion("Deadpool", PlayerCount);
			PlayerCount++;
		});

		ChampionButton DrStrange = new ChampionButton("Dr Strange");
		DrStrange.setTranslateX(590);
		DrStrange.setTranslateY(90);
		InfoBox box3 = new InfoBox("Dr Strange");
		DrStrange.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				DrStrange.setDisable(true);
			DrStrange.setOpacity(0.50);
			box3.setTranslateX(590);
			box3.setTranslateY(290);
			scene2L.getChildren().addAll(box3);
		});
		DrStrange.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box3);
			DrStrange.setOpacity(1);
		});
		DrStrange.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				DrStrange.setTranslateX(30);
				DrStrange.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				DrStrange.setTranslateX(200);
				DrStrange.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				DrStrange.setTranslateX(370);
				DrStrange.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				DrStrange.setTranslateX(980);
				DrStrange.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				DrStrange.setTranslateX(1150);
				DrStrange.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				DrStrange.setTranslateX(1320);
				DrStrange.setTranslateY(620);
			}

			DrStrange.setDisable(true);
			Console.addChampion("Dr Strange", PlayerCount);
			PlayerCount++;
		});

		ChampionButton Electro = new ChampionButton("Electro");
		Electro.setTranslateX(750);
		Electro.setTranslateY(90);

		InfoBox box4 = new InfoBox("Electro");
		Electro.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Electro.setDisable(true);
			Electro.setOpacity(0.50);
			box4.setTranslateX(750);
			box4.setTranslateY(290);
			scene2L.getChildren().addAll(box4);
		});
		Electro.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box4);
			Electro.setOpacity(1);
		});
		Electro.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Electro.setTranslateX(30);
				Electro.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Electro.setTranslateX(200);
				Electro.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Electro.setTranslateX(370);
				Electro.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Electro.setTranslateX(980);
				Electro.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Electro.setTranslateX(1150);
				Electro.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Electro.setTranslateX(1320);
				Electro.setTranslateY(620);
			}

			Electro.setDisable(true);
			Console.addChampion("Electro", PlayerCount);
			PlayerCount++;
		});
		ChampionButton GhostRider = new ChampionButton("Ghost Rider");
		GhostRider.setTranslateX(910);
		GhostRider.setTranslateY(90);
		GhostRider.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				GhostRider.setTranslateX(30);
				GhostRider.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				GhostRider.setTranslateX(200);
				GhostRider.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				GhostRider.setTranslateX(370);
				GhostRider.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				GhostRider.setTranslateX(980);
				GhostRider.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				GhostRider.setTranslateX(1150);
				GhostRider.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				GhostRider.setTranslateX(1320);
				GhostRider.setTranslateY(620);
			}

			GhostRider.setDisable(true);
			Console.addChampion("Ghost Rider", PlayerCount);
			PlayerCount++;
		});

		InfoBox box5 = new InfoBox("Ghost Rider");
		GhostRider.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				GhostRider.setDisable(true);
			GhostRider.setOpacity(0.50);
			box5.setTranslateX(910);
			box5.setTranslateY(290);
			scene2L.getChildren().addAll(box5);
		});
		GhostRider.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box5);
			GhostRider.setOpacity(1);
		});
		ChampionButton Hela = new ChampionButton("Hela");
		Hela.setTranslateX(1070);
		Hela.setTranslateY(90);
		Hela.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Hela.setTranslateX(30);
				Hela.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Hela.setTranslateX(200);
				Hela.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Hela.setTranslateX(370);
				Hela.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Hela.setTranslateX(980);
				Hela.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Hela.setTranslateX(1150);
				Hela.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Hela.setTranslateX(1320);
				Hela.setTranslateY(620);
			}

			Hela.setDisable(true);
			Console.addChampion("Hela", PlayerCount);
			PlayerCount++;
		});
		InfoBox box6 = new InfoBox("Hela");
		Hela.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Hela.setDisable(true);
			Hela.setOpacity(0.50);
			box6.setTranslateX(750);
			box6.setTranslateY(290);
			scene2L.getChildren().addAll(box6);
		});
		Hela.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box6);
			Hela.setOpacity(1);
		});
		ChampionButton Hulk = new ChampionButton("Hulk");
		Hulk.setTranslateX(1230);
		Hulk.setTranslateY(90);
		Hulk.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Hulk.setTranslateX(30);
				Hulk.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Hulk.setTranslateX(200);
				Hulk.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Hulk.setTranslateX(370);
				Hulk.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Hulk.setTranslateX(980);
				Hulk.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Hulk.setTranslateX(1150);
				Hulk.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Hulk.setTranslateX(1320);
				Hulk.setTranslateY(620);
			}

			Hulk.setDisable(true);
			Console.addChampion("Hulk", PlayerCount);
			PlayerCount++;
		});
		InfoBox box7 = new InfoBox("Hulk");
		Hulk.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Hulk.setDisable(true);
			Hulk.setOpacity(0.50);
			box7.setTranslateX(900);
			box7.setTranslateY(290);
			scene2L.getChildren().addAll(box7);
		});
		Hulk.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box7);
			Hulk.setOpacity(1);
		});

		ChampionButton Iceman = new ChampionButton("Iceman");
		Iceman.setTranslateX(110);
		Iceman.setTranslateY(80);
		Iceman.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Iceman.setTranslateX(30);
				Iceman.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Iceman.setTranslateX(200);
				Iceman.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Iceman.setTranslateX(370);
				Iceman.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Iceman.setTranslateX(980);
				Iceman.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Iceman.setTranslateX(1150);
				Iceman.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Iceman.setTranslateX(1320);
				Iceman.setTranslateY(620);
			}

			Iceman.setDisable(true);
			Console.addChampion("Iceman", PlayerCount);
			PlayerCount++;
		});
		InfoBox box8 = new InfoBox("Iceman");
		Iceman.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Iceman.setDisable(true);
			Iceman.setOpacity(0.50);
			box8.setTranslateX(110);
			box8.setTranslateY(290);
			scene2L.getChildren().addAll(box8);
		});
		Iceman.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box8);
			Iceman.setOpacity(1);
		});
		ChampionButton Ironman = new ChampionButton("Ironman");
		Ironman.setTranslateX(110);
		Ironman.setTranslateY(295);
		Ironman.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Ironman.setTranslateX(30);
				Ironman.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Ironman.setTranslateX(200);
				Ironman.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Ironman.setTranslateX(370);
				Ironman.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Ironman.setTranslateX(980);
				Ironman.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Ironman.setTranslateX(1150);
				Ironman.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Ironman.setTranslateX(1320);
				Ironman.setTranslateY(620);
			}

			Ironman.setDisable(true);
			Console.addChampion("Ironman", PlayerCount);
			PlayerCount++;
		});
		InfoBox box9 = new InfoBox("Ironman");
		Ironman.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Ironman.setDisable(true);
			Ironman.setOpacity(0.50);
			box9.setTranslateX(110);
			box9.setTranslateY(480);
			scene2L.getChildren().addAll(box9);
		});
		Ironman.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box9);
			Ironman.setOpacity(1);
		});

		ChampionButton Loki = new ChampionButton("Loki");
		Loki.setTranslateX(270);
		Loki.setTranslateY(295);
		Loki.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Loki.setTranslateX(30);
				Loki.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Loki.setTranslateX(200);
				Loki.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Loki.setTranslateX(370);
				Loki.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Loki.setTranslateX(980);
				Loki.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Loki.setTranslateX(1150);
				Loki.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Loki.setTranslateX(1320);
				Loki.setTranslateY(620);
			}

			Loki.setDisable(true);
			Console.addChampion("Loki", PlayerCount);
			PlayerCount++;
		});
		InfoBox box10 = new InfoBox("Loki");
		Loki.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Loki.setDisable(true);
			Loki.setOpacity(0.50);
			box10.setTranslateX(270);
			box10.setTranslateY(480);
			scene2L.getChildren().addAll(box10);
		});
		Loki.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box10);
			Loki.setOpacity(1);
		});

		ChampionButton Quicksilver = new ChampionButton("Quicksilver");
		Quicksilver.setTranslateX(430);
		Quicksilver.setTranslateY(295);
		Quicksilver.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Quicksilver.setTranslateX(30);
				Quicksilver.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Quicksilver.setTranslateX(200);
				Quicksilver.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Quicksilver.setTranslateX(370);
				Quicksilver.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Quicksilver.setTranslateX(980);
				Quicksilver.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Quicksilver.setTranslateX(1150);
				Quicksilver.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Quicksilver.setTranslateX(1320);
				Quicksilver.setTranslateY(620);
			}

			Quicksilver.setDisable(true);
			Console.addChampion("Quicksilver", PlayerCount);
			PlayerCount++;
		});
		InfoBox box11 = new InfoBox("Quicksilver");
		Quicksilver.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Quicksilver.setDisable(true);
			Quicksilver.setOpacity(0.50);
			box11.setTranslateX(430);
			box11.setTranslateY(480);
			scene2L.getChildren().addAll(box11);
		});
		Quicksilver.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box11);
			Quicksilver.setOpacity(1);
		});

		ChampionButton Spiderman = new ChampionButton("Spiderman");
		Spiderman.setTranslateX(590);
		Spiderman.setTranslateY(295);
		Spiderman.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Spiderman.setTranslateX(30);
				Spiderman.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Spiderman.setTranslateX(200);
				Spiderman.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Spiderman.setTranslateX(370);
				Spiderman.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Spiderman.setTranslateX(980);
				Spiderman.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Spiderman.setTranslateX(1150);
				Spiderman.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Spiderman.setTranslateX(1320);
				Spiderman.setTranslateY(620);
			}

			Spiderman.setDisable(true);
			Console.addChampion("Spiderman", PlayerCount);
			PlayerCount++;
		});
		InfoBox box12 = new InfoBox("Spiderman");
		Spiderman.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Spiderman.setDisable(true);
			Spiderman.setOpacity(0.50);
			box12.setTranslateX(430);
			box12.setTranslateY(480);
			scene2L.getChildren().addAll(box12);
		});
		Spiderman.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box12);
			Spiderman.setOpacity(1);
		});

		ChampionButton Thor = new ChampionButton("Thor");
		Thor.setTranslateX(750);
		Thor.setTranslateY(295);
		Thor.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Thor.setTranslateX(30);
				Thor.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Thor.setTranslateX(200);
				Thor.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Thor.setTranslateX(370);
				Thor.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Thor.setTranslateX(980);
				Thor.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Thor.setTranslateX(1150);
				Thor.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Thor.setTranslateX(1320);
				Thor.setTranslateY(620);
			}

			Thor.setDisable(true);
			Console.addChampion("Thor", PlayerCount);
			PlayerCount++;
		});
		InfoBox box13 = new InfoBox("Thor");
		Thor.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Thor.setDisable(true);
			Thor.setOpacity(0.50);
			box13.setTranslateX(750);
			box13.setTranslateY(480);
			scene2L.getChildren().addAll(box13);
		});
		Thor.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box13);
			Thor.setOpacity(1);
		});

		ChampionButton Venom = new ChampionButton("Venom");
		Venom.setTranslateX(910);
		Venom.setTranslateY(295);
		Venom.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				Venom.setTranslateX(30);
				Venom.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				Venom.setTranslateX(200);
				Venom.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				Venom.setTranslateX(370);
				Venom.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				Venom.setTranslateX(980);
				Venom.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				Venom.setTranslateX(1150);
				Venom.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				Venom.setTranslateX(1320);
				Venom.setTranslateY(620);
			}

			Venom.setDisable(true);
			Console.addChampion("Venom", PlayerCount);
			PlayerCount++;
		});
		InfoBox box14 = new InfoBox("Venom");
		Venom.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				Venom.setDisable(true);
			Venom.setOpacity(0.50);
			box14.setTranslateX(910);
			box14.setTranslateY(480);
			scene2L.getChildren().addAll(box14);
		});
		Venom.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box14);
			Venom.setOpacity(1);
		});

		ChampionButton YellowJacket = new ChampionButton("Yellow Jacket");
		YellowJacket.setTranslateX(1070);
		YellowJacket.setTranslateY(295);
		YellowJacket.setOnMouseClicked(e -> {
			if (PlayerCount == 0) {
				YellowJacket.setTranslateX(30);
				YellowJacket.setTranslateY(620);
			}
			if (PlayerCount == 2) {
				YellowJacket.setTranslateX(200);
				YellowJacket.setTranslateY(620);
			}
			if (PlayerCount == 4) {
				YellowJacket.setTranslateX(370);
				YellowJacket.setTranslateY(620);
			}
			if (PlayerCount == 1) {
				YellowJacket.setTranslateX(980);
				YellowJacket.setTranslateY(620);
			}
			if (PlayerCount == 3) {
				YellowJacket.setTranslateX(1150);
				YellowJacket.setTranslateY(620);
			}
			if (PlayerCount == 5) {
				YellowJacket.setTranslateX(1320);
				YellowJacket.setTranslateY(620);
			}

			YellowJacket.setDisable(true);
			Console.addChampion("Yellow Jacket", PlayerCount);
			PlayerCount++;
		});
		InfoBox box15 = new InfoBox("Yellow Jacket");
		YellowJacket.setOnMouseEntered(e -> {
			if (PlayerCount == 6)
				YellowJacket.setDisable(true);
			YellowJacket.setOpacity(0.50);
			box15.setTranslateX(960);
			box15.setTranslateY(480);
			scene2L.getChildren().addAll(box15);
		});
		YellowJacket.setOnMouseExited(e -> {
			scene2L.getChildren().remove(box15);
			YellowJacket.setOpacity(1);
		});

		scene2L.getChildren().addAll(imgView2, CA, Deadpool, DrStrange, Electro, GhostRider, Hela, Hulk, Iceman,
				Ironman, Loki, Quicksilver, Spiderman, Thor, Venom, YellowJacket, tothebattle);
		// Scene 4 Children
		// ---------------------------------------------------------------------------------------------------------------
		Tothebattle lessgo = new Tothebattle("StartGame");
		lessgo.setTranslateX(650);
		lessgo.setTranslateY(600);
		lessgo.setPrefSize(150, 50);
		Button leader1 = new Button("Player1 Leader Ability ");
		leader1.setPrefSize(350, 60);
		leader1.setTranslateX(1175);
		leader1.setTranslateY(750);
		leader1.setStyle("-fx-background-color: #556B2F");
		Button leader2 = new Button("Player2 Leader Ability");
		leader2.setPrefSize(350, 60);
		leader2.setTranslateX(1175);
		leader2.setStyle("-fx-background-color: #556B2F");
		//leader1.setDisable(true);
		//leader2.setDisable(true);
		
        
		lessgo.setOnMouseClicked(e -> {
			String battle = "res/images/battle.mp4";
	        Media battlee = new Media(new File(battle).toURI().toString());
	         mediaPlayer = new MediaPlayer(battlee);
	         intropl.pause();
	        mediaPlayer.play();
	      
	        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			if (Console.player1.getLeader() == null || Console.player2.getLeader() == null) {
				Text Leaders = new Text("Please Choose your leaders");
				Leaders.setFont(new Font("Comic Sans MS", 30));
				Leaders.setFill(Color.RED);
				Leaders.setTranslateX(800);
				Leaders.setTranslateY(70);
				scene3L.getChildren().addAll(Leaders);
				Timeline blinker = createBlinker(Leaders);
				blinker.setOnFinished(event -> root.getChildren().remove(Leaders));
				blinker.play();
			} else {
				
				primaryStage.setScene(scene4);
				Console.startGame();
				Turns turns = new Turns();
				turns.setTranslateX(450);
				turns.setTranslateY(825);
				scene4L.getChildren().addAll(turns);
				if(Console.player1.getTeam().contains(Console.game.getCurrentChampion())) {
					leader1.setDisable(false);
					leader2.setDisable(true);
				}
				else {
					leader2.setDisable(false);
					leader1.setDisable(true);
				}
				try {
					CurrentChampionInfo curr = new CurrentChampionInfo(Console.game.getCurrentChampion());
					curr.setTranslateX(10);
					curr.setTranslateY(100);
					scene4L.getChildren().addAll(curr);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					CreateButtons(scene4L);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					map = new GridMap();
					map.setTranslateX(500);
					map.setTranslateY(100);
					  
					  
					scene4L.getChildren().addAll(map);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
			// DynamicText
			scene4L.getChildren().addAll(dynamicText);
			dynamicText.setText("Welcome to the game");
			FadeTransition fade = permblinker(dynamicText);
			fade.play();
		});
		InputStream is6 = Files.newInputStream(Paths.get("res/images/boardd1.jpeg"));
		Image img6 = new Image(is6);
		is6.close();

		ImageView imgView6 = new ImageView(img6);
		imgView6.setFitHeight(900);
		imgView6.setFitWidth(1600);
		scene4L.getChildren().addAll(imgView6);
		Button endTurn = new Button();
		endTurn.setPrefSize(160, 60);
		endTurn.setTranslateX(330);
		endTurn.setTranslateY(530);
		
		
		endTurn.setText("EndTurn");

		endTurn.setOnAction(e -> {
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i).getId() == "ID") {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}

			for (int j = 0; j < scene4L.getChildren().size(); j++) {
				if (scene4L.getChildren().get(j) instanceof CurrentChampionInfo) {
					scene4L.getChildren().remove(scene4L.getChildren().get(j));
					j--;
				}
			}
			try {
				Champion curr = Console.game.getCurrentChampion();
				dynamicText.setText(curr.getName() + " Ended Their Turn");
				Console.endTurn();
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				if(Console.player1.getTeam().contains(Console.game.getCurrentChampion())) {
					leader1.setDisable(false);
					leader2.setDisable(true);
				}
				else {
					leader2.setDisable(false);
					leader1.setDisable(true);
				}

				scene4L.getChildren().addAll(curr1);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			try {
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);

		});
		endTurn.setOnMouseEntered(e -> {
			endTurn.setStyle("-fx-background-color: Gray");
			endTurn.setOpacity(0.6);
		});

		endTurn.setOnMouseExited(e -> {
			endTurn.setStyle("-fx-background-color: White");
			endTurn.setOpacity(1);
		});
		Button move = new Button("Move");
		// button.setOpacity(0.2);
		move.setPrefSize(160, 60);
		move.setTranslateX(10);
		move.setTranslateY(530);

		move.setOnAction(e -> {
			
			try {
				dynamicText.setText("please Choose a direction!");
				for (int j = 0; j < scene4L.getChildren().size(); j++) {
					if (scene4L.getChildren().get(j).getId() == "ID") {
						scene4L.getChildren().remove(scene4L.getChildren().get(j));
						j--;
					}
				}
				moveWindow();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
		});
		move.setOnMouseEntered(e -> {
			move.setStyle("-fx-background-color: Gray");
			move.setOpacity(0.6);
		});

		move.setOnMouseExited(e -> {
			move.setStyle("-fx-background-color: White");
			move.setOpacity(1);
		});
		Button attack = new Button("attack");
		// button.setOpacity(0.2);
		attack.setPrefSize(160, 60);
		attack.setTranslateX(170);
		attack.setTranslateY(530);

		attack.setOnAction(e -> {
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i).getId() == "ID") {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				dynamicText.setText("please Choose a direction!");
				attackWindow();
			} catch (Exception e2) {

				e2.printStackTrace();
			}
		});
		attack.setOnMouseEntered(e -> {
			attack.setStyle("-fx-background-color: Gray");
			attack.setOpacity(0.6);
		});

		attack.setOnMouseExited(e -> {
			attack.setStyle("-fx-background-color: White");
			attack.setOpacity(1);
		});
			
		leader1.setOnMouseEntered(e->{
			leader1.setOpacity(0.6);
		});
		leader1.setOnMouseExited(e->{
			leader1.setOpacity(1);
		});
		leader1.setOnAction(e -> {
			try {
				leader1.setStyle("-fx-background-color: #556B2F");
				Console.game.useLeaderAbility();
				leader1.setStyle("-fx-background-color: #B22222");
			} catch (LeaderNotCurrentException e1) {
				handleException("Can't cast LeaderAbility, not the Leader's Turn");
				e1.printStackTrace();
			} catch (LeaderAbilityAlreadyUsedException e1) {
				handleException("LeaderAbility was already used!");
				e1.printStackTrace();
			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				map.updateBoard();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			Champion curr = Console.game.getCurrentChampion();
			dynamicText.setText(curr.getName() + " Used Leader Ability ");
		});

		
		leader2.setOnMouseEntered(e -> {
			//leader2.setStyle("-fx-background-color: Gray");
			leader2.setOpacity(0.6);
		});

		leader2.setOnMouseExited(e -> {
			//leader2.setStyle("-fx-background-color: #556B2F");
			leader2.setOpacity(1);
		});
		leader2.setOnAction(e -> {
			leader2.setStyle("-fx-background-color: #556B2F");
			try {
				Console.game.useLeaderAbility();
				leader2.setStyle("-fx-background-color: #B22222");
			} catch (LeaderNotCurrentException e1) {
				handleException("Can't cast LeaderAbility, not the Leader's Turn");
				e1.printStackTrace();
			} catch (LeaderAbilityAlreadyUsedException e1) {
				handleException("LeaderAbility was already used!");
				e1.printStackTrace();
			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				map.updateBoard();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			Champion curr = Console.game.getCurrentChampion();
			dynamicText.setText(curr.getName() + " Used Leader Ability ");
		});
		leader2.setTranslateY(350);

		scene4L.getChildren().addAll(move, attack, endTurn, leader1, leader2);

		// scene3
		InputStream is5 = Files.newInputStream(Paths.get("res/images/Scene3hopefully.jpg"));
		Image img5 = new Image(is5);
		is5.close();

		ImageView imgView5 = new ImageView(img5);
		imgView5.setFitHeight(900);
		imgView5.setFitWidth(1600);
		scene3L.getChildren().addAll(imgView5);

		scene3L.getChildren().addAll(lessgo);

	}

	public static void handleboard(Pane Scene) {
		Player pl1 = Console.player1;
		Player pl2 = Console.player2;
		try {
			ChampionButton1 c1 = new ChampionButton1(pl1.getTeam().get(0).getName());
			ChampionButton1 c2 = new ChampionButton1(pl1.getTeam().get(1).getName());
			ChampionButton1 c3 = new ChampionButton1(pl1.getTeam().get(2).getName());
			ChampionButton1 c4 = new ChampionButton1(pl2.getTeam().get(0).getName());
			ChampionButton1 c5 = new ChampionButton1(pl2.getTeam().get(1).getName());
			ChampionButton1 c6 = new ChampionButton1(pl2.getTeam().get(2).getName());
			c1.setTranslateX(1440);
			c1.setTranslateY(500);
			c2.setTranslateX(1320);
			c2.setTranslateY(500);
			c3.setTranslateX(1200);
			c3.setTranslateY(500);
			c4.setTranslateX(1440);
			c4.setTranslateY(120);
			c5.setTranslateX(1320);
			c5.setTranslateY(120);
			c6.setTranslateX(1200);
			c6.setTranslateY(120);
			InfoBox box = new InfoBox(pl1.getTeam().get(0).getName());
			c1.setOnMouseEntered(e -> {
				c1.setOpacity(0.50);
				box.setTranslateX(1140);
				box.setTranslateY(80);
				Scene.getChildren().addAll(box);
			});
			c1.setOnMouseExited(e -> {
				Scene.getChildren().remove(box);
				c1.setOpacity(1);
			});
			InfoBox box1 = new InfoBox(pl1.getTeam().get(1).getName());
			c2.setOnMouseEntered(e -> {
				c2.setOpacity(0.50);
				box1.setTranslateX(1140);
				box1.setTranslateY(80);
				Scene.getChildren().addAll(box1);
			});
			c2.setOnMouseExited(e -> {
				Scene.getChildren().remove(box1);
				c2.setOpacity(1);
			});
			InfoBox box2 = new InfoBox(pl1.getTeam().get(2).getName());
			c3.setOnMouseEntered(e -> {
				c3.setOpacity(0.50);
				box2.setTranslateX(1140);
				box2.setTranslateY(80);
				Scene.getChildren().addAll(box2);
			});
			c3.setOnMouseExited(e -> {
				Scene.getChildren().remove(box2);
				c3.setOpacity(1);
			});
			InfoBox box3 = new InfoBox(pl2.getTeam().get(0).getName());
			c4.setOnMouseEntered(e -> {
				c4.setOpacity(0.50);
				box3.setTranslateX(1140);
				box3.setTranslateY(330);
				Scene.getChildren().addAll(box3);
			});
			c4.setOnMouseExited(e -> {
				Scene.getChildren().remove(box3);
				c4.setOpacity(1);
			});
			InfoBox box4 = new InfoBox(pl2.getTeam().get(1).getName());
			c5.setOnMouseEntered(e -> {
				c5.setOpacity(0.50);
				box4.setTranslateX(1140);
				box4.setTranslateY(330);
				Scene.getChildren().addAll(box4);
			});
			c5.setOnMouseExited(e -> {
				Scene.getChildren().remove(box4);
				c5.setOpacity(1);
			});
			InfoBox box5 = new InfoBox(pl2.getTeam().get(2).getName());
			c6.setOnMouseEntered(e -> {
				c6.setOpacity(0.50);
				box5.setTranslateX(1140);
				box5.setTranslateY(330);
				Scene.getChildren().addAll(box5);
			});
			c6.setOnMouseExited(e -> {
				Scene.getChildren().remove(box5);
				c6.setOpacity(1);
			});
			Scene.getChildren().addAll(c1, c2, c3, c4, c5, c6);

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

	public static void handle(Pane Scene) {
		Player pl1 = Console.player1;
		Player pl2 = Console.player2;

		try {
			ChampionButton c1 = new ChampionButton(pl1.getTeam().get(0).getName());
			ChampionButton c2 = new ChampionButton(pl1.getTeam().get(1).getName());
			ChampionButton c3 = new ChampionButton(pl1.getTeam().get(2).getName());
			ChampionButton c4 = new ChampionButton(pl2.getTeam().get(0).getName());
			ChampionButton c5 = new ChampionButton(pl2.getTeam().get(1).getName());
			ChampionButton c6 = new ChampionButton(pl2.getTeam().get(2).getName());
			c1.setTranslateX(30);
			c1.setTranslateY(100);
			c2.setTranslateX(200);
			c2.setTranslateY(100);
			c3.setTranslateX(370);
			c3.setTranslateY(100);
			c4.setTranslateX(980);
			c4.setTranslateY(100);
			c5.setTranslateX(1150);
			c5.setTranslateY(100);
			c6.setTranslateX(1320);
			c6.setTranslateY(100);

			Scene.getChildren().addAll(c1, c2, c3, c4, c5, c6);
			c1.setOnMouseClicked(e -> {
				Console.setLeader1(pl1.getTeam().get(0));
				c2.setVisible(false);
				c3.setVisible(false);
				c1.setVisible(false);
				try {
					ImageView leader = leader(pl1.getTeam().get(0).getName(), Scene);
					leader.setTranslateX(100);
					leader.setTranslateY(200);
				} catch (Exception e1) {

					e1.printStackTrace();
				}

			});
			c2.setOnMouseClicked(e -> {
				Console.setLeader1(pl1.getTeam().get(1));
				c2.setVisible(false);
				c3.setVisible(false);
				c1.setVisible(false);
				try {
					ImageView leader = leader(pl1.getTeam().get(1).getName(), Scene);
					leader.setTranslateX(100);
					leader.setTranslateY(200);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			});
			c3.setOnMouseClicked(e -> {
				Console.setLeader1(pl1.getTeam().get(2));
				c2.setVisible(false);
				c3.setVisible(false);
				c1.setVisible(false);
				try {
					ImageView leader = leader(pl1.getTeam().get(2).getName(), Scene);
					leader.setTranslateX(100);
					leader.setTranslateY(200);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			});
			c4.setOnMouseClicked(e -> {
				Console.setLeader2(pl2.getTeam().get(0));
				c4.setVisible(false);
				c5.setVisible(false);
				c6.setVisible(false);
				try {
					ImageView leader = leader(pl2.getTeam().get(0).getName(), Scene);
					leader.setTranslateX(1000);
					leader.setTranslateY(200);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			});
			c5.setOnMouseClicked(e -> {
				Console.setLeader2(pl2.getTeam().get(1));
				c4.setVisible(false);
				c5.setVisible(false);
				c6.setVisible(false);
				try {
					ImageView leader = leader(pl2.getTeam().get(1).getName(), Scene);
					leader.setTranslateX(1000);
					leader.setTranslateY(200);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			});
			c6.setOnMouseClicked(e -> {
				Console.setLeader2(pl2.getTeam().get(2));
				c4.setVisible(false);
				c5.setVisible(false);
				c6.setVisible(false);
				try {
					ImageView leader = leader(pl2.getTeam().get(2).getName(), Scene);
					leader.setTranslateX(1000);
					leader.setTranslateY(200);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			});

		} catch (Exception e1) {

			e1.printStackTrace();
		}

	}

	private static class StartGameButton extends StackPane {
		private Text text;
		public Rectangle bg;


		

			public StartGameButton(String name) {
				text = new Text(name);
				text.setFont(text.getFont().font(50));
				text.setFill(Color.WHITE);

				bg = new Rectangle(250, 100);
				bg.setOpacity(0.6);
				bg.setFill(Color.BLACK);
				bg.setEffect(new GaussianBlur(3.5));

				setAlignment(Pos.CENTER);
				getChildren().addAll(bg, text);

				setOnMouseEntered(e -> {
					// bg.setTranslateX(10);
					// text.setTranslateX(10);
					bg.setFill(Color.GRAY);
					bg.setOpacity(0.6);
				});

				setOnMouseExited(e -> {
					// bg.setTranslateX(0);
					// text.setTranslateX(0);
					bg.setFill(Color.BLACK);
					bg.setOpacity(0.3);
				});

				// DropShadow drop = new DropShadow(50, Color.WHITE);
				// drop.setInput(new Glow());
				//
				// setOnMousePressed(e -> setEffect(drop));
				//
				// setOnMouseReleased(e -> setEffect(null));
			}}
		

	private static class Tothebattle extends StackPane {
		private Text text;
		public Rectangle bg;

		public Tothebattle(String name) {
			text = new Text(name);
			text.setFont(text.getFont().font(40));
			text.setFill(Color.WHITE);

			bg = new Rectangle(300, 40);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));

			setAlignment(Pos.CENTER);
			getChildren().addAll(bg, text);

			setOnMouseEntered(e -> {
				// bg.setTranslateX(10);
				// text.setTranslateX(10);
				bg.setFill(Color.GRAY);
				bg.setOpacity(0.6);
			});

			setOnMouseExited(e -> {
				// bg.setTranslateX(0);
				// text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				bg.setOpacity(0.3);
			});

			// DropShadow drop = new DropShadow(50, Color.WHITE);
			// drop.setInput(new Glow());
			//
			// setOnMousePressed(e -> setEffect(drop));
			//
			// setOnMouseReleased(e -> setEffect(null));
		}
	}

	public static ImageView leader(String s, Pane scene) throws Exception {
		Rectangle bg = new Rectangle(150, 200);
		bg.setOpacity(0);
		bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));

		switch (s) {
		case "Captain America":
			InputStream CaptainP = Files.newInputStream(Paths.get("res/images/CaptainAmerica.png"));
			Image Captain = new Image(CaptainP);
			CaptainP.close();
			ImageView CaptainView = new ImageView(Captain);
			CaptainView.setPreserveRatio(true);
			CaptainView.setSmooth(true);
			CaptainView.setCache(true);
			CaptainView.setFitHeight(600);
			CaptainView.setFitWidth(450);

			scene.getChildren().addAll(CaptainView, bg);
			return CaptainView;

		case "Deadpool":
			InputStream DeadpoolP = Files.newInputStream(Paths.get("res/images/DeadPool.png"));
			Image Deadpool = new Image(DeadpoolP);
			DeadpoolP.close();
			ImageView DeadpoolView = new ImageView(Deadpool);
			DeadpoolView.setPreserveRatio(true);
			DeadpoolView.setSmooth(true);
			DeadpoolView.setCache(true);
			DeadpoolView.setFitHeight(600);
			DeadpoolView.setFitWidth(450);
			scene.getChildren().addAll(DeadpoolView, bg);
			return DeadpoolView;
		case "Dr Strange":
			InputStream DrStrangeP = Files.newInputStream(Paths.get("res/images/DrStrange.png"));
			Image DrStrange = new Image(DrStrangeP);
			DrStrangeP.close();
			ImageView DrStrangeView = new ImageView(DrStrange);
			DrStrangeView.setFitHeight(600);
			DrStrangeView.setFitWidth(450);
			scene.getChildren().addAll(DrStrangeView, bg);
			return DrStrangeView;
		case "Electro":
			InputStream ElectroP = Files.newInputStream(Paths.get("res/images/Electro.png"));
			Image Electro = new Image(ElectroP);
			ElectroP.close();
			ImageView ElectroView = new ImageView(Electro);
			ElectroView.setFitHeight(600);
			ElectroView.setFitWidth(450);
			scene.getChildren().addAll(ElectroView, bg);
			return ElectroView;
		case "Ghost Rider":
			InputStream GhostRiderP = Files.newInputStream(Paths.get("res/images/GhostRider.png"));
			Image GhostRider = new Image(GhostRiderP);
			GhostRiderP.close();
			ImageView GhostRiderView = new ImageView(GhostRider);
			GhostRiderView.setFitHeight(600);
			GhostRiderView.setFitWidth(450);
			scene.getChildren().addAll(GhostRiderView, bg);
			return GhostRiderView;
		case "Hela":
			InputStream HelaP = Files.newInputStream(Paths.get("res/images/Hela.png"));
			Image Hela = new Image(HelaP);
			HelaP.close();
			ImageView HelaView = new ImageView(Hela);
			HelaView.setFitHeight(600);
			HelaView.setFitWidth(450);
			scene.getChildren().addAll(HelaView, bg);
			return HelaView;
		case "Hulk":
			InputStream HulkP = Files.newInputStream(Paths.get("res/images/Hulk.png"));
			Image Hulk = new Image(HulkP);
			HulkP.close();
			ImageView HulkView = new ImageView(Hulk);
			HulkView.setFitHeight(600);
			HulkView.setFitWidth(450);
			scene.getChildren().addAll(HulkView, bg);
			return HulkView;
		case "Iceman":
			InputStream IcemanP = Files.newInputStream(Paths.get("res/images/IceMan.png"));
			Image Iceman = new Image(IcemanP);
			IcemanP.close();
			ImageView IcemanView = new ImageView(Iceman);
			IcemanView.setFitHeight(600);
			IcemanView.setFitWidth(450);
			scene.getChildren().addAll(IcemanView, bg);
			return IcemanView;
		case "Ironman":
			InputStream IronmanP = Files.newInputStream(Paths.get("res/images/IronMan.png"));
			Image Ironman = new Image(IronmanP);
			IronmanP.close();
			ImageView IronmanView = new ImageView(Ironman);
			IronmanView.setFitHeight(600);
			IronmanView.setFitWidth(450);
			scene.getChildren().addAll(IronmanView, bg);
			return IronmanView;
		case "Loki":
			InputStream LokiP = Files.newInputStream(Paths.get("res/images/Loki.png"));
			Image Loki = new Image(LokiP);
			LokiP.close();
			ImageView LokiView = new ImageView(Loki);
			LokiView.setFitHeight(600);
			LokiView.setFitWidth(450);
			scene.getChildren().addAll(LokiView, bg);
			return LokiView;
		case "Quicksilver":
			InputStream QuicksilverP = Files.newInputStream(Paths.get("res/images/QuickSilver.png"));
			Image Quicksilver = new Image(QuicksilverP);
			QuicksilverP.close();
			ImageView QuicksilverView = new ImageView(Quicksilver);
			QuicksilverView.setFitHeight(600);
			QuicksilverView.setFitWidth(450);
			scene.getChildren().addAll(QuicksilverView, bg);
			return QuicksilverView;
		case "Spiderman":
			InputStream SpidermanP = Files.newInputStream(Paths.get("res/images/SpiderMan.png"));
			Image Spiderman = new Image(SpidermanP);
			SpidermanP.close();
			ImageView SpidermanView = new ImageView(Spiderman);
			SpidermanView.setFitHeight(600);
			SpidermanView.setFitWidth(450);
			scene.getChildren().addAll(SpidermanView, bg);
			return SpidermanView;
		case "Thor":
			InputStream ThorP = Files.newInputStream(Paths.get("res/images/Thor.png"));
			Image Thor = new Image(ThorP);
			ThorP.close();
			ImageView ThorView = new ImageView(Thor);
			ThorView.setFitHeight(600);
			ThorView.setFitWidth(450);
			scene.getChildren().addAll(ThorView, bg);
			return ThorView;
		case "Venom":
			InputStream VenomP = Files.newInputStream(Paths.get("res/images/Venom.png"));
			Image Venom = new Image(VenomP);
			VenomP.close();
			ImageView VenomView = new ImageView(Venom);
			VenomView.setFitHeight(600);
			VenomView.setFitWidth(450);
			scene.getChildren().addAll(VenomView, bg);
			return VenomView;
		case "Yellow Jacket":
			InputStream YellowJacketP = Files.newInputStream(Paths.get("res/images/YellowJacket.png"));
			Image YellowJacket = new Image(YellowJacketP);
			YellowJacketP.close();
			ImageView YellowJacketView = new ImageView(YellowJacket);
			YellowJacketView.setFitHeight(600);
			YellowJacketView.setFitWidth(450);
			scene.getChildren().addAll(YellowJacketView, bg);
			return YellowJacketView;
		}
		return null;
	}

	public void CreateButtons(Pane scene) throws Exception {
		for (int i = 0; i < scene.getChildren().size(); i++) {
			if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
				scene.getChildren().remove(i);
				i--;
			}
		}
		Champion curr = Console.game.getCurrentChampion();
		int locationx = 10;

		for (Ability a : curr.getAbilities()) {

			switch (a.getCastArea()) {
			case TEAMTARGET:
				CastAbility ab = new CastAbility(a);
				

				ab.setOnMouseEntered(e -> {
					try {
						AbilityInfoBox aa = new AbilityInfoBox(a.getName());
						aa.setTranslateX(30);
						aa.setTranslateY(600);

						scene.getChildren().addAll(aa);
					} catch (IOException e1) {

						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				ab.setOnMouseExited(e -> {
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}
				});
				;

				ab.setOnMouseClicked(e -> {

					for (int j = 0; j < scene4L.getChildren().size(); j++) {
						if (scene4L.getChildren().get(j) instanceof CurrentChampionInfo) {
							scene4L.getChildren().remove(scene4L.getChildren().get(j));
							j--;
						}
					}
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}

					try {
						Console.castability(a);
						dynamicText.setText(curr.getName() + " Casted the TeamTargetAbility " + a.getName());
					} catch (NotEnoughResourcesException e3) {
						handleException("NotEnoughResources!");
						e3.printStackTrace();
					} catch (InvalidTargetException e3) {
						handleException("InvalidTarget!");
						e3.printStackTrace();
					} catch (AbilityUseException e3) {
						handleException("Can't Use Ability Now!");
						e3.printStackTrace();
					} catch (CloneNotSupportedException e3) {
					}
					try {
						winnerscene(primaryStage);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						map.updateBoard();
					} catch (IOException e2) {

						e2.printStackTrace();
					}
					try {
						CurrentChampionInfo curr1 = new CurrentChampionInfo(curr);
						curr1.setTranslateX(10);
						curr1.setTranslateY(100);

						scene4L.getChildren().addAll(curr1);
					} catch (Exception e1) {

						e1.printStackTrace();
					}
//					try {
//						CreateButtons(scene4L);
//					} catch (Exception e1) {
//
//						e1.printStackTrace();
//					}
				});

				ab.setTranslateX(locationx);
				ab.setTranslateY(440);
				scene.getChildren().addAll(ab);
				break;
			case SELFTARGET:
				CastAbility ab2 = new CastAbility(a);
				ab2.setOnMouseEntered(e -> {
					try {
						AbilityInfoBox aa = new AbilityInfoBox(a.getName());
						aa.setTranslateX(30);
						aa.setTranslateY(600);

						scene.getChildren().addAll(aa);
					} catch (IOException e1) {

						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				ab2.setOnMouseExited(e -> {
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}
				});
				;
				ab2.setOnMouseClicked(e -> {
//					for (int i = 0; i < scene4L.getChildren().size(); i++) {
//						if (scene4L.getChildren().get(i) instanceof CastAbility) {
//							scene4L.getChildren().remove(scene4L.getChildren().get(i));
//							i--;
//						}
//					}
					for (int j = 0; j < scene4L.getChildren().size(); j++) {
						if (scene4L.getChildren().get(j) instanceof CurrentChampionInfo) {
							scene4L.getChildren().remove(scene4L.getChildren().get(j));
							j--;
						}
					}
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}
					try {
						Console.castability(a);
						dynamicText.setText(curr.getName() + " Casted the SelfTargetAbility " + a.getName());
					} catch (NotEnoughResourcesException e3) {
						handleException("NotEnoughResources!");
						e3.printStackTrace();
					} catch (InvalidTargetException e3) {
						handleException("InvalidTarget!");
						e3.printStackTrace();
					} catch (AbilityUseException e3) {
						handleException("Can't Use Ability Now!");
						e3.printStackTrace();
					} catch (CloneNotSupportedException e3) {

					}
					try {
						winnerscene(primaryStage);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println("Casted a SelfTarget ability");

					try {
						map.updateBoard();
					} catch (IOException e2) {

						e2.printStackTrace();
					}
					try {
						CurrentChampionInfo curr1 = new CurrentChampionInfo(curr);
						curr1.setTranslateX(10);
						curr1.setTranslateY(100);

						scene4L.getChildren().addAll(curr1);
					} catch (Exception e1) {

						e1.printStackTrace();
					}
//					try {
//						CreateButtons(scene4L);
//					} catch (Exception e1) {
//
//						e1.printStackTrace();
//					}
				});

				ab2.setTranslateX(locationx);
				ab2.setTranslateY(440);
				scene.getChildren().addAll(ab2);

				break;
			case SURROUND:
				CastAbility ab3 = new CastAbility(a);
				ab3.setOnMouseEntered(e -> {
					try {
						AbilityInfoBox aa = new AbilityInfoBox(a.getName());
						aa.setTranslateX(30);
						aa.setTranslateY(600);

						scene.getChildren().addAll(aa);
					} catch (IOException e1) {

						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				ab3.setOnMouseExited(e -> {
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}
				});
				;
				ab3.setOnMouseClicked(e -> {

					for (int j = 0; j < scene4L.getChildren().size(); j++) {
						if (scene4L.getChildren().get(j) instanceof CurrentChampionInfo) {
							scene4L.getChildren().remove(scene4L.getChildren().get(j));
							j--;
						}
					}
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}

					try {
						Console.castability(a);
						dynamicText.setText(curr.getName() + " Casted the SurroundAbility " + a.getName());
					} catch (NotEnoughResourcesException e3) {
						handleException("NotEnoughResources!");
						e3.printStackTrace();
					} catch (InvalidTargetException e3) {
						handleException("InvalidTarget!");
						e3.printStackTrace();
					} catch (AbilityUseException e3) {
						handleException("Can't Use Ability Now!");
						e3.printStackTrace();
					} catch (CloneNotSupportedException e3) {

					}
					try {
						winnerscene(primaryStage);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println("Casted a Surround ability");

					try {
						map.updateBoard();
					} catch (IOException e2) {

						e2.printStackTrace();
					}
					try {
						CurrentChampionInfo curr1 = new CurrentChampionInfo(curr);
						curr1.setTranslateX(10);
						curr1.setTranslateY(100);

						scene4L.getChildren().addAll(curr1);
					} catch (Exception e1) {

						e1.printStackTrace();
					}
				
				});

				ab3.setTranslateY(440);
				ab3.setTranslateX(locationx);
				scene.getChildren().addAll(ab3);

				break;
			case DIRECTIONAL:
				CastAbility ab4 = new CastAbility(a);
				ab4.setOnMouseEntered(e -> {
					try {
						AbilityInfoBox aa = new AbilityInfoBox(a.getName());
						aa.setTranslateX(30);
						aa.setTranslateY(600);

						scene.getChildren().addAll(aa);
					} catch (IOException e1) {

						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				ab4.setOnMouseExited(e -> {
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}
				});
				;
				ab4.setOnMouseClicked(e -> {

					for (int j = 0; j < scene4L.getChildren().size(); j++) {
						if (scene4L.getChildren().get(j) instanceof CurrentChampionInfo) {
							scene4L.getChildren().remove(scene4L.getChildren().get(j));
							j--;
						}
					}
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}
					dynamicText.setText("Please choose a direction.");
					try {

						abilityWindow(a);
					} catch (Exception e1) {

						e1.printStackTrace();
					}
					try {

						CurrentChampionInfo curr1 = new CurrentChampionInfo(curr);
						curr1.setTranslateX(10);
						curr1.setTranslateY(100);

						scene4L.getChildren().addAll(curr1);
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				});

				ab4.setTranslateY(440);
				ab4.setTranslateX(locationx);
				scene.getChildren().addAll(ab4);

				break;
			case SINGLETARGET:
				CastAbility ab5 = new CastAbility(a);
				ab5.setOnMouseEntered(e -> {
					try {
						AbilityInfoBox aa = new AbilityInfoBox(a.getName());
						aa.setTranslateX(30);
						aa.setTranslateY(600);

						scene.getChildren().addAll(aa);
					} catch (IOException e1) {

						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				ab5.setOnMouseExited(e -> {
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox) {
							scene.getChildren().remove(i);
							i--;
						}
					}
				});
				;
				ab5.setOnMouseClicked(e -> {
					for (int i = 0; i < scene.getChildren().size(); i++) {
						if (scene.getChildren().get(i) instanceof AbilityInfoBox)
							scene.getChildren().remove(i);
					}
					for (int i = 0; i < scene4L.getChildren().size(); i++) {
						if (scene4L.getChildren().get(i) instanceof CastAbility) {
							scene4L.getChildren().remove(scene4L.getChildren().get(i));
							i--;
						}
					}
					try {
						dynamicText.setText("Casting SingleTargetAbility, Please click on a Cell");
						ButtonsGrid(a, scene);
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				});
				if (4 == curr.getAbilities().size() && a == curr.getAbilities().get(3)) {
					ab5.setTranslateX(170);
					ab5.setTranslateY(460);
				} else {
					ab5.setTranslateX(locationx);
					ab5.setTranslateY(440);
				}

				scene4L.getChildren().addAll(ab5);

				break;
			}
			locationx = locationx + 160;
		}
	}

	private Timeline createBlinker(Node node) {
		Timeline blink = new Timeline(
				new KeyFrame(Duration.seconds(0), new KeyValue(node.opacityProperty(), 1, Interpolator.DISCRETE)),
				new KeyFrame(Duration.seconds(0.5), new KeyValue(node.opacityProperty(), 0, Interpolator.DISCRETE)),
				new KeyFrame(Duration.seconds(1), new KeyValue(node.opacityProperty(), 1, Interpolator.DISCRETE)));
		blink.setCycleCount(3);

		return blink;
	}

	private FadeTransition permblinker(Node node) {
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), node);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.5);
		fadeTransition.setCycleCount(Animation.INDEFINITE);
		return fadeTransition;
	}

	public void ButtonsGrid(Ability a, Pane scene) {
		Champion curr = Console.game.getCurrentChampion();
		GridPane grid = new GridPane();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Rectangle rect = new Rectangle(0, 0, 120, 140);
				grid.add(rect, i, j);
				rect.setOpacity(0.3);
				rect.setFill(Color.RED);
				rect.setOnMouseEntered(e -> {
					rect.setOpacity(0.3);
					rect.setFill(Color.GRAY);
					rect.setEffect(new GaussianBlur(3.5));
				});
				rect.setOnMouseExited(e -> {
					rect.setOpacity(0.3);
					rect.setFill(Color.RED);
				});
				rect.setOnMouseClicked(e -> {
					for (int s = 0; s < scene4L.getChildren().size(); s++) {
						if (scene4L.getChildren().get(s) instanceof CastAbility) {
							scene4L.getChildren().remove(scene4L.getChildren().get(s));
							s--;
						}
					}
					for (int m = 0; m < scene4L.getChildren().size(); m++) {
						if (scene4L.getChildren().get(m) instanceof CurrentChampionInfo) {
							scene4L.getChildren().remove(scene4L.getChildren().get(m));
							m--;
						}
					}
					try {
						Console.castability2(a, 4 - grid.getRowIndex(rect), grid.getColumnIndex(rect));
					} catch (NotEnoughResourcesException e1) {
						handleException("NotEnoughResources!");
						e1.printStackTrace();
					} catch (InvalidTargetException e1) {
						handleException("InvalidTarget!");
						e1.printStackTrace();
					} catch (AbilityUseException e1) {
						handleException("Can't Use Ability Now!");
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {

					}
					try {
						winnerscene(primaryStage);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println("Casted a SingleTarget ability");

					for (int s = 0; s < scene4L.getChildren().size(); s++) {
						if (scene4L.getChildren().get(s) instanceof Turns) {
							scene4L.getChildren().remove(scene4L.getChildren().get(s));
							s--;
						}
					}
					Turns turns = new Turns();
					turns.setTranslateX(450);
					turns.setTranslateY(825);
					scene4L.getChildren().addAll(turns);
					try {
						map.updateBoard();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					try {
						CurrentChampionInfo curr1 = new CurrentChampionInfo(curr);
						curr1.setTranslateX(10);
						curr1.setTranslateY(100);

						scene4L.getChildren().addAll(curr1);
					} catch (Exception e1) {

						e1.printStackTrace();
					}
					try {
						CreateButtons(scene4L);
					} catch (Exception e1) {

						e1.printStackTrace();
					}
					grid.getChildren().clear();
					dynamicText.setText(curr.getName() + " Casted The SingleTargetAbility " + a.getName());
				});
			}
			grid.setAlignment(Pos.CENTER);
		}
		grid.setTranslateX(500);
		grid.setTranslateY(100);
		scene.getChildren().addAll(grid);
	}

	private void moveWindow() throws Exception {
		Champion curr = Console.game.getCurrentChampion();
		Pane root = new Pane();
		root.setId("ID");
		Rectangle bg = new Rectangle(310, 250);
		root.setTranslateX(100);
		root.setTranslateY(600);
		bg.setOpacity(0.4);
		bg.setFill(Color.WHITE);
		bg.setEffect(new GaussianBlur(3.5));
		MoveButton up = new MoveButton(Direction.UP);
		up.setTranslateX(100);
		up.setTranslateY(10);
		up.setOnMouseClicked(e -> {

			try {
				Console.move(Direction.UP);
				dynamicText.setText(curr.getName() + " Moved Up!");
			} catch (NotEnoughResourcesException e1) {
				handleException("NotEnoughResources!");
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				handleException("UnallowedMovement");
				e1.printStackTrace();
			}
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});

		MoveButton down = new MoveButton(Direction.DOWN);
		down.setTranslateX(100);
		down.setTranslateY(140);
		down.setOnMouseClicked(e -> {
			try {
				Console.move(Direction.DOWN);
				dynamicText.setText(curr.getName() + " Moved Down!");
			} catch (NotEnoughResourcesException e1) {
				handleException("NotEnoughResources!");
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				handleException("UnallowedMovement");
				e1.printStackTrace();
			}
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});
		MoveButton right = new MoveButton(Direction.RIGHT);
		right.setTranslateX(190);
		right.setTranslateY(70);

		right.setOnMouseClicked(e -> {

			try {
				Console.move(Direction.RIGHT);
				dynamicText.setText(curr.getName() + " Moved Right!");
			} catch (NotEnoughResourcesException e1) {
				handleException("NotEnoughResources!");
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				handleException("UnallowedMovement");
				e1.printStackTrace();
			}
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();

		});
		MoveButton left = new MoveButton(Direction.LEFT);
		left.setTranslateX(10);
		left.setTranslateY(70);
		left.setOnMouseClicked(e -> {

			try {
				Console.move(Direction.LEFT);
				dynamicText.setText(curr.getName() + " Moved Left!");
			} catch (NotEnoughResourcesException e1) {
				handleException("NotEnoughResources!");
				e1.printStackTrace();
			} catch (UnallowedMovementException e1) {
				handleException("UnallowedMovement");
				e1.printStackTrace();
			}
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});
		root.getChildren().addAll(bg, right, left, up, down);
		scene4L.getChildren().addAll(root);
	}

	private void attackWindow() throws Exception {
		Champion curr = Console.game.getCurrentChampion();
		Pane root = new Pane();
		root.setId("ID");
		Rectangle bg = new Rectangle(310, 250);
		root.setTranslateX(100);
		root.setTranslateY(600);
		bg.setOpacity(0.4);
		bg.setFill(Color.WHITE);
		bg.setEffect(new GaussianBlur(3.5));
		MoveButton up = new MoveButton(Direction.UP);
		up.setTranslateX(100);
		up.setTranslateY(10);
		up.setOnMouseClicked(e -> {

			try {
				Console.attack(Direction.UP);
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (ChampionDisarmedException e2) {
				handleException("ChampionDisarmed, Can't Attack!!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			dynamicText.setText(curr.getName() + " Attacked Up!");

			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);

			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});

		MoveButton down = new MoveButton(Direction.DOWN);
		down.setTranslateX(100);
		down.setTranslateY(140);
		down.setOnMouseClicked(e -> {
			try {
				Console.attack(Direction.DOWN);
				dynamicText.setText(curr.getName() + " Attacked Down!");
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (ChampionDisarmedException e2) {
				handleException("ChampionDisarmed, Can't Attack!!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});
		MoveButton right = new MoveButton(Direction.RIGHT);
		right.setTranslateX(190);
		right.setTranslateY(70);

		right.setOnMouseClicked(e -> {

			try {
				Console.attack(Direction.RIGHT);
				dynamicText.setText(curr.getName() + " Attacked Right!");
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (ChampionDisarmedException e2) {
				handleException("ChampionDisarmed, Can't Attack!!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();

		});
		MoveButton left = new MoveButton(Direction.LEFT);
		left.setTranslateX(10);
		left.setTranslateY(70);
		left.setOnMouseClicked(e -> {

			try {
				Console.attack(Direction.LEFT);
				dynamicText.setText(curr.getName() + " Attacked Left!");
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (ChampionDisarmedException e2) {
				handleException("ChampionDisarmed, Can't Attack!!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});
		root.getChildren().addAll(bg, right, left, up, down);
		scene4L.getChildren().addAll(root);
	}

	public void abilityWindow(Ability a) throws Exception {
		Champion curr = Console.game.getCurrentChampion();
		Pane root = new Pane();
		root.setId("ID");
		Rectangle bg = new Rectangle(310, 250);
		root.setTranslateX(100);
		root.setTranslateY(600);
		bg.setOpacity(0.4);
		bg.setFill(Color.WHITE);
		bg.setEffect(new GaussianBlur(3.5));
		MoveButton up = new MoveButton(Direction.UP);
		up.setTranslateX(100);
		up.setTranslateY(10);
		up.setOnMouseClicked(e -> {
			try {
				Console.castability1(a, Direction.UP);
				dynamicText.setText(curr.getName() + " Casted " + a.getName() + " Up!");
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			} catch (AbilityUseException e2) {
				handleException("Can't Use Ability Now!");
				e2.printStackTrace();
			} catch (CloneNotSupportedException e2) {

			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});

		MoveButton down = new MoveButton(Direction.DOWN);
		down.setTranslateX(100);
		down.setTranslateY(140);
		down.setOnMouseClicked(e -> {
			try {
				Console.castability1(a, Direction.DOWN);
				dynamicText.setText(curr.getName() + " Casted " + a.getName() + " Down!");
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			} catch (AbilityUseException e2) {
				handleException("Can't Use Ability Now!");
				e2.printStackTrace();
			} catch (CloneNotSupportedException e2) {

			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});
		MoveButton right = new MoveButton(Direction.RIGHT);
		right.setTranslateX(190);
		right.setTranslateY(70);

		right.setOnMouseClicked(e -> {

			try {
				Console.castability1(a, Direction.RIGHT);
				dynamicText.setText(curr.getName() + " Casted " + a.getName() + " Right!");
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			} catch (AbilityUseException e2) {
				handleException("Can't Use Ability Now!");
				e2.printStackTrace();
			} catch (CloneNotSupportedException e2) {

			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();

		});
		MoveButton left = new MoveButton(Direction.LEFT);
		left.setTranslateX(10);
		left.setTranslateY(70);
		left.setOnMouseClicked(e -> {

			try {
				Console.castability1(a, Direction.LEFT);
				dynamicText.setText(curr.getName() + " Casted " + a.getName() + " Left!");
			} catch (NotEnoughResourcesException e2) {
				handleException("NotEnoughResources!");
				e2.printStackTrace();
			} catch (InvalidTargetException e2) {
				handleException("InvalidTarget!");
				e2.printStackTrace();
			} catch (AbilityUseException e2) {
				handleException("Can't Use Ability Now!");
				e2.printStackTrace();
			} catch (CloneNotSupportedException e2) {

			}
			try {
				winnerscene(primaryStage);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof Turns) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			Turns turns = new Turns();
			turns.setTranslateX(450);
			turns.setTranslateY(825);
			scene4L.getChildren().addAll(turns);
			try {
				map.updateBoard();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			for (int i = 0; i < scene4L.getChildren().size(); i++) {
				if (scene4L.getChildren().get(i) instanceof CastAbility) {
					scene4L.getChildren().remove(scene4L.getChildren().get(i));
					i--;
				}
			}
			try {
				CurrentChampionInfo curr1 = new CurrentChampionInfo(Console.game.getCurrentChampion());
				curr1.setTranslateX(10);
				curr1.setTranslateY(100);
				for (int i = 0; i < scene4L.getChildren().size(); i++) {
					if (scene4L.getChildren().get(i) instanceof CurrentChampionInfo)
						scene4L.getChildren().remove(i);
				}
				scene4L.getChildren().addAll(curr1);
				CreateButtons(scene4L);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			root.getChildren().clear();
		});
		root.getChildren().addAll(bg, right, left, up, down);
		scene4L.getChildren().addAll(root);
	}

	public void handleException(String s) {
		Stage window = new Stage();
		Pane layout = new Pane();
		layout.setPrefSize(600, 200);
		Scene pop = new Scene(layout);

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("ALERT");
		window.setMinHeight(250);
		window.setMinWidth(500);
		Button Close = new Button("Close");
		Close.setOnAction(e -> window.close());

		dynamicExp.setText(s);
		KeyFrame frame = new KeyFrame(Duration.seconds(2), e -> {
			scene4L.getChildren().addAll(dynamicText);
			scene4L.getChildren().remove(dynamicExp);
		}, new KeyValue(dynamicText.opacityProperty(), 0));
		Timeline timeline = new Timeline();
		FadeTransition fade = permblinker(dynamicExp);
		timeline.getKeyFrames().add(frame);
		fade.play();
		timeline.play();
		Close.setTranslateX(280);
		Close.setTranslateY(150);
		layout.getChildren().addAll(dynamicExp, Close);
		window.setScene(pop);
		window.showAndWait();
	}

	public void winnerscene(Stage stage) throws IOException {
		Pane scene = new Pane();
		scene.setPrefSize(1600, 900);
		Scene sc = new Scene(scene);
		if (Console.game.checkGameOver() != null) {
			String playerwinner = Console.game.checkGameOver().getName();
			stage.setScene(sc);
			InputStream is = Files.newInputStream(Paths.get("res/images/LastScene.jpg"));
			Image img = new Image(is);
			is.close();
			ImageView imgView = new ImageView(img);
			imgView.setFitHeight(900);
			imgView.setFitWidth(1600);

			Text player = new Text(playerwinner);
			player.setFill(Color.WHITESMOKE);
			player.setTranslateX(70);
			player.setTranslateY(370);
			player.setWrappingWidth(700);
			player.setTextAlignment(TextAlignment.CENTER);
			player.setFont(new Font("Impact", 140));
			mediaPlayer.pause();
			File winnn = new File( "res/images/win.mp4");
			Media winner = new Media(winnn.toURI().toString());
			MediaView winn = new MediaView();
			win = new MediaPlayer(winner);
	        winn.setMediaPlayer(intropl);
	        winn.setVisible(false);
	        intropl.setAutoPlay(true);
	        
			Text wins = new Text("WINS!");
			wins.setFill(Color.WHITESMOKE);
			wins.setTranslateX(70);
			wins.setTranslateY(570);
			wins.setWrappingWidth(500);
			wins.setTextAlignment(TextAlignment.CENTER);
			wins.setFont(new Font("Impact", 200));

			StartGameButton playagain = new StartGameButton("PlayAgain");
			playagain.setPrefSize(200, 100);
			playagain.setTranslateX(200);
			playagain.setTranslateY(600);
			playagain.setOnMouseClicked(e -> {
				try {
					GUI newgame = new GUI();
					newgame.start(stage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});

			StartGameButton exit = new StartGameButton("Exit");
			exit.setPrefSize(200, 100);
			exit.setTranslateX(200);
			exit.setTranslateY(700);
			exit.setOnMouseClicked(e -> stage.close());

			scene.getChildren().addAll(imgView, player, wins, playagain, exit);
		}
	}
}
