package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;
import javafx.geometry.Pos;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CurrentChampionInfo extends StackPane{
	String Name;
	int HP, Mana, Actions, Speed, AttackRange, AttackDamage;
	Console console;
	ArrayList<model.effects.Effect> effects;
	public CurrentChampionInfo(Champion c) throws Exception{
		Pane root = new Pane();
		root.setPrefSize(500, 330);
		Rectangle bg = new Rectangle(500, 330);
		bg.setOpacity(0.7);
		// bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));
		String t = null;
		if (c instanceof Hero)
			t = "Hero";
		else if (c instanceof Villain)
			t = "villain";
		else
			t = "AntiHero";
		

		Text name = new Text(c.getName());
		name.getFont();
		name.setFont(new Font("Arial", 20));
		name.setFill(Color.WHITE);
		name.setTranslateX(190);
		name.setTranslateY(30);

		Text type = new Text(t);
		type.getFont();
		type.setFont(new Font("Arial", 20));
		type.setFill(Color.WHITE);
		type.setTranslateX(190);
		type.setTranslateY(70);
		boolean leader = false;
		if(Console.player1.getLeader().getName().equals(c.getName()))
			leader = true;
		else if(Console.player2.getLeader().getName().equals(c.getName()))
			leader = true;
		String leaderr = null;
		if(leader == true)
			leaderr = (c.getName()+ " is your leader");
		
		Text isleader = new Text(leaderr);
		isleader.getFont();
		isleader.setFont(Font.font(20));
		isleader.setFill(Color.WHITE);
		isleader.setTranslateX(190);
		isleader.setTranslateY(110);
		String eff = c.getName()+ "'s applied effects are: (";
		effects = c.getAppliedEffects();
		for (int i = 0; i<effects.size(); i++){
			eff = eff + effects.get(i).getName()+" duration: "+ effects.get(i).getDuration();
		}
		 eff = eff +")";
		 Text effect = new Text(eff);
		 effect.getFont();
		 effect.setFont(Font.font(14));
		 effect.setFill(Color.WHITE);
		 effect.setTranslateX(5);
		 effect.setTranslateY(210);
		 
		Text hp = new Text("Current hp: " + Integer.toString(c.getCurrentHP()));
		hp.getFont();
		hp.setFont(Font.font(16));
		hp.setFill(Color.WHITE);
		hp.setTranslateX(5);
		hp.setTranslateY(240);

		Text mana = new Text("CurrentMana: " + Integer.toString(c.getMana()));
		mana.getFont();
		mana.setFont(Font.font(16));
		mana.setFill(Color.WHITE);
		mana.setTranslateX(205);
		mana.setTranslateY(240);

		Text actions = new Text("ActionPoints: "
				+ Integer.toString(c.getCurrentActionPoints()));
		actions.getFont();
		actions.setFont(Font.font(16));
		actions.setFill(Color.WHITE);
		actions.setTranslateX(5);
		actions.setTranslateY(270);

		Text speed = new Text("Speed: " + Integer.toString(c.getSpeed()));
		speed.getFont();
		speed.setFont(Font.font(16));
		speed.setFill(Color.WHITE);
		speed.setTranslateX(205);
		speed.setTranslateY(270);

		Text attackRange = new Text("AttackRange: "
				+ Integer.toString(c.getAttackRange()));
		attackRange.getFont();
		attackRange.setFont(Font.font(16));
		attackRange.setFill(Color.WHITE);
		attackRange.setTranslateX(5);
		attackRange.setTranslateY(300);

		Text attackDamage = new Text("AttackDamage: "
				+ Integer.toString(c.getAttackDamage()));
		attackDamage.getFont();
		attackDamage.setFont(Font.font(16));
		attackDamage.setFill(Color.WHITE);
		attackDamage.setTranslateX(205);
		attackDamage.setTranslateY(300);
		
		root.getChildren().addAll(name, type,
				attackDamage, speed, mana, hp, actions, attackRange, isleader, effect);
		getChildren().addAll(bg, root);
	
	switch (c.getName()) {
	case "Captain America":
		InputStream CaptainP = Files.newInputStream(Paths.get("res/images/CaptainAmerica.png"));
		Image Captain = new Image(CaptainP);
		CaptainP.close();
		ImageView CaptainView = new ImageView(Captain);
		CaptainView.setPreserveRatio(true);
		CaptainView.setSmooth(true);
		CaptainView.setCache(true);
		CaptainView.setFitHeight(200);
		CaptainView.setFitWidth(150);
		CaptainView.setTranslateX(0);
		CaptainView.setTranslateY(0);
		root.getChildren().addAll(CaptainView);
		break;
	case "Deadpool":
		InputStream DeadpoolP = Files.newInputStream(Paths.get("res/images/DeadPool.png"));
		Image Deadpool = new Image(DeadpoolP);
		DeadpoolP.close();
		ImageView DeadpoolView = new ImageView(Deadpool);
		DeadpoolView.setPreserveRatio(true);
		DeadpoolView.setSmooth(true);
		DeadpoolView.setCache(true);
		DeadpoolView.setFitHeight(200);
		DeadpoolView.setFitWidth(150);
		DeadpoolView.setTranslateX(0);
		DeadpoolView.setTranslateY(0);
		root.getChildren().addAll(DeadpoolView);
		break;
	case "Dr Strange":
		InputStream DrStrangeP = Files.newInputStream(Paths.get("res/images/DrStrange.png"));
		Image DrStrange = new Image(DrStrangeP);
		DrStrangeP.close();
		ImageView DrStrangeView = new ImageView(DrStrange);
		DrStrangeView.setFitHeight(200);
		DrStrangeView.setFitWidth(150);
		DrStrangeView.setTranslateX(0);
		DrStrangeView.setTranslateY(0);
		root.getChildren().addAll(DrStrangeView);
		break;
	case "Electro":
		InputStream ElectroP = Files.newInputStream(Paths.get("res/images/Electro.png"));
		Image Electro = new Image(ElectroP);
		ElectroP.close();
		ImageView ElectroView = new ImageView(Electro);
		ElectroView.setFitHeight(200);
		ElectroView.setFitWidth(150);
		ElectroView.setTranslateX(0);
		ElectroView.setTranslateY(0);
		root.getChildren().addAll(ElectroView);
		
		break;
	case "Ghost Rider":
		InputStream GhostRiderP = Files.newInputStream(Paths.get("res/images/GhostRider.png"));
		Image GhostRider = new Image(GhostRiderP);
		GhostRiderP.close();
		ImageView GhostRiderView = new ImageView(GhostRider);
		GhostRiderView.setFitHeight(200);
		GhostRiderView.setFitWidth(150);
		GhostRiderView.setTranslateX(0);
		GhostRiderView.setTranslateY(0);
		root.getChildren().addAll(GhostRiderView);
		
		break;
	case "Hela":
		InputStream HelaP = Files.newInputStream(Paths.get("res/images/Hela.png"));
		Image Hela = new Image(HelaP);
		HelaP.close();
		ImageView HelaView = new ImageView(Hela);
		HelaView.setFitHeight(200);
		HelaView.setFitWidth(150);
		HelaView.setTranslateX(0);
		HelaView.setTranslateY(0);
		root.getChildren().addAll(HelaView);
		break;
	case "Hulk":
		InputStream HulkP = Files.newInputStream(Paths.get("res/images/Hulk.png"));
		Image Hulk = new Image(HulkP);
		HulkP.close();
		ImageView HulkView = new ImageView(Hulk);
		HulkView.setFitHeight(200);
		HulkView.setFitWidth(150);
		HulkView.setTranslateX(0);
		HulkView.setTranslateY(0);
		root.getChildren().addAll(HulkView);
		break;
	case "Iceman":
		InputStream IcemanP = Files.newInputStream(Paths.get("res/images/IceMan.png"));
		Image Iceman = new Image(IcemanP);
		IcemanP.close();
		ImageView IcemanView = new ImageView(Iceman);
		IcemanView.setFitHeight(200);
		IcemanView.setFitWidth(150);
		IcemanView.setTranslateX(0);
		IcemanView.setTranslateY(0);
		root.getChildren().addAll(IcemanView);
		break;
	case "Ironman":
		InputStream IronmanP = Files.newInputStream(Paths.get("res/images/IronMan.png"));
		Image Ironman = new Image(IronmanP);
		IronmanP.close();
		ImageView IronmanView = new ImageView(Ironman);
		IronmanView.setFitHeight(200);
		IronmanView.setFitWidth(150);
		IronmanView.setTranslateX(0);
		IronmanView.setTranslateY(0);
		root.getChildren().addAll(IronmanView);
		break;
	case "Loki":
		InputStream LokiP = Files.newInputStream(Paths.get("res/images/Loki.png"));
		Image Loki = new Image(LokiP);
		LokiP.close();
		ImageView LokiView = new ImageView(Loki);
		LokiView.setFitHeight(200);
		LokiView.setFitWidth(150);
		LokiView.setTranslateX(0);
		LokiView.setTranslateY(0);
		root.getChildren().addAll(LokiView);
		break;
	case "Quicksilver":
		InputStream QuicksilverP = Files.newInputStream(Paths.get("res/images/QuickSilver.png"));
		Image Quicksilver = new Image(QuicksilverP);
		QuicksilverP.close();
		ImageView QuicksilverView = new ImageView(Quicksilver);
		QuicksilverView.setFitHeight(200);
		QuicksilverView.setFitWidth(150);
		QuicksilverView.setTranslateX(0);
		QuicksilverView.setTranslateY(0);
		root.getChildren().addAll(QuicksilverView);
		break;
	case "Spiderman":
		InputStream SpidermanP = Files.newInputStream(Paths.get("res/images/SpiderMan.png"));
		Image Spiderman = new Image(SpidermanP);
		SpidermanP.close();
		ImageView SpidermanView = new ImageView(Spiderman);
		SpidermanView.setFitHeight(200);
		SpidermanView.setFitWidth(150);
		SpidermanView.setTranslateX(0);
		SpidermanView.setTranslateY(0);
		root.getChildren().addAll(SpidermanView);
		break;
	case "Thor":
		InputStream ThorP = Files.newInputStream(Paths.get("res/images/Thor.png"));
		Image Thor = new Image(ThorP);
		ThorP.close();
		ImageView ThorView = new ImageView(Thor);
		ThorView.setFitHeight(200);
		ThorView.setFitWidth(150);
		ThorView.setTranslateX(0);
		ThorView.setTranslateY(0);
		root.getChildren().addAll(ThorView);
		break;
	case "Venom":
		InputStream VenomP = Files.newInputStream(Paths.get("res/images/Venom.png"));
		Image Venom = new Image(VenomP);
		VenomP.close();
		ImageView VenomView = new ImageView(Venom);
		VenomView.setFitHeight(200);
		VenomView.setFitWidth(150);
		VenomView.setTranslateX(0);
		VenomView.setTranslateY(0);
		root.getChildren().addAll(VenomView);
		break;
	case "Yellow Jacket":
		InputStream YellowJacketP = Files.newInputStream(Paths.get("res/images/YellowJacket.png"));
		Image YellowJacket = new Image(YellowJacketP);
		YellowJacketP.close();
		ImageView YellowJacketView = new ImageView(YellowJacket);
		YellowJacketView.setFitHeight(200);
		YellowJacketView.setFitWidth(150);
		YellowJacketView.setTranslateX(0);
		YellowJacketView.setTranslateY(0);
		root.getChildren().addAll(YellowJacketView);
		break;
	}
}


	}

