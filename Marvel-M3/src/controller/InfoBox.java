package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;

public class InfoBox extends StackPane {
	public InfoBox(String s) throws IOException {
		Champion c = Console.getChampion(s);
		Pane root = new Pane();
		root.setPrefSize(500, 330);
		Rectangle bg = new Rectangle(500, 410);
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
		String abi1 = c.getAbilities().get(0).getName();
		String abi2 = c.getAbilities().get(1).getName();
		String abi3 = c.getAbilities().get(2).getName();

		Text name = new Text("Name: " + c.getName());
		name.getFont();
		name.setFont(new Font("Arial", 14));
		name.setFill(Color.WHITE);
		name.setTranslateX(5);
		name.setTranslateY(10);

		Text type = new Text("Type: " + t);
		type.getFont();
		type.setFont(Font.font(16));
		type.setFill(Color.WHITE);
		type.setTranslateX(205);
		type.setTranslateY(10);

		Text hp = new Text("MaxHp: " + Integer.toString(c.getMaxHP()));
		hp.getFont();
		hp.setFont(Font.font(16));
		hp.setFill(Color.WHITE);
		hp.setTranslateX(5);
		hp.setTranslateY(40);

		Text mana = new Text("MaxMana: " + Integer.toString(c.getMana()));
		mana.getFont();
		mana.setFont(Font.font(16));
		mana.setFill(Color.WHITE);
		mana.setTranslateX(205);
		mana.setTranslateY(40);

		Text actions = new Text("ActionPoints: "
				+ Integer.toString(c.getMaxActionPointsPerTurn()));
		actions.getFont();
		actions.setFont(Font.font(16));
		actions.setFill(Color.WHITE);
		actions.setTranslateX(5);
		actions.setTranslateY(70);

		Text speed = new Text("Speed: " + Integer.toString(c.getSpeed()));
		speed.getFont();
		speed.setFont(Font.font(16));
		speed.setFill(Color.WHITE);
		speed.setTranslateX(205);
		speed.setTranslateY(70);

		Text attackRange = new Text("AttackRange: "
				+ Integer.toString(c.getAttackRange()));
		attackRange.getFont();
		attackRange.setFont(Font.font(16));
		attackRange.setFill(Color.WHITE);
		attackRange.setTranslateX(5);
		attackRange.setTranslateY(100);

		Text attackDamage = new Text("AttackDamage: "
				+ Integer.toString(c.getAttackDamage()));
		attackDamage.getFont();
		attackDamage.setFont(Font.font(16));
		attackDamage.setFill(Color.WHITE);
		attackDamage.setTranslateX(205);
		attackDamage.setTranslateY(100);
		Text a1 = new Text("Ability1: " + abi1);
		a1.getFont();
		a1.setFont(Font.font(16));
		a1.setFill(Color.WHITE);
		a1.setTranslateX(5);
		a1.setTranslateY(130);
		
		Text c1 = new Text("CD: "
				+ c.getAbilities().get(0).getBaseCooldown());
		c1.getFont();
		c1.setFont(Font.font(12));
		c1.setFill(Color.WHITE);
		c1.setTranslateX(20);
		c1.setTranslateY(150);
		Text cost1 = new Text("Cost: "
				+ c.getAbilities().get(0).getManaCost());
		cost1.getFont();
		cost1.setFont(Font.font(12));
		cost1.setFill(Color.WHITE);
		cost1.setTranslateX(300);
		cost1.setTranslateY(150);
		Text range1 = new Text("Range: "
				+ c.getAbilities().get(0).getCastRange());
		range1.getFont();
		range1.setFont(Font.font(12));
		range1.setFill(Color.WHITE);
		range1.setTranslateX(20);
		range1.setTranslateY(170);
		Text area1 = new Text("Area: "
				+ c.getAbilities().get(0).getCastArea());
		area1.getFont();
		area1.setFont(Font.font(12));
		area1.setFill(Color.WHITE);
		area1.setTranslateX(300);
		area1.setTranslateY(170);
		Text action1 = new Text("ActionPoints: "
				+ c.getAbilities().get(0).getRequiredActionPoints());
		action1.getFont();
		action1.setFont(Font.font(12));
		action1.setFill(Color.WHITE);
		action1.setTranslateX(20);
		action1.setTranslateY(190);
		if (c.getAbilities().get(0) instanceof HealingAbility) {
			Text Heal1 = new Text("Heal Amount: "
					+ ((HealingAbility) (c.getAbilities().get(0))).getHealAmount());
			Heal1.getFont();
			Heal1.setFont(Font.font(12));
			Heal1.setFill(Color.WHITE);
			Heal1.setTranslateX(300);
			Heal1.setTranslateY(190);

			Text Type1 = new Text("Type: HealingAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(150);
			Type1.setTranslateY(210);
			root.getChildren().addAll(Heal1,Type1);
		}else if (c.getAbilities().get(0) instanceof DamagingAbility) {
			Text Damage1 = new Text("Damage Amount: "
					+ ((DamagingAbility) (c.getAbilities().get(0))).getDamageAmount());
			Damage1.getFont();
			Damage1.setFont(Font.font(12));
			Damage1.setFill(Color.WHITE);
			Damage1.setTranslateX(300);
			Damage1.setTranslateY(190);
			
			Text Type1 = new Text("Type: DamagingAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(150);
			Type1.setTranslateY(210);
			root.getChildren().addAll(Damage1,Type1);
		} else {
			Text Effect1 = new Text("Effect: "
					+ ((CrowdControlAbility) (c.getAbilities().get(0))).getEffect().getName());
			Effect1.getFont();
			Effect1.setFont(Font.font(12));
			Effect1.setFill(Color.WHITE);
			Effect1.setTranslateX(300);
			Effect1.setTranslateY(190);
			
			Text EDuration1 = new Text("Effect Duration: "
					+ ((CrowdControlAbility) (c.getAbilities().get(0))).getEffect().getDuration());
			EDuration1.getFont();
			EDuration1.setFont(Font.font(12));
			EDuration1.setFill(Color.WHITE);
			EDuration1.setTranslateX(300);
			EDuration1.setTranslateY(210);
			
			Text Type1 = new Text("Type: CrowdControlAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(20);
			Type1.setTranslateY(210);
			root.getChildren().addAll(Effect1,Type1,EDuration1);
		}
		
		
//a2----------------------------------------------------------------------------------------------------------------
		Text a2 = new Text("Ability2: " + abi2);
		a2.getFont();
		a2.setFont(Font.font(16));
		a2.setFill(Color.WHITE);
		a2.setTranslateX(5);
		a2.setTranslateY(230);

		Text c2 = new Text("CD: "
				+ c.getAbilities().get(1).getBaseCooldown());
		c2.getFont();
		c2.setFont(Font.font(12));
		c2.setFill(Color.WHITE);
		c2.setTranslateX(20);
		c2.setTranslateY(250);
		Text cost2 = new Text("Cost: "
				+ c.getAbilities().get(1).getManaCost());
		cost2.getFont();
		cost2.setFont(Font.font(12));
		cost2.setFill(Color.WHITE);
		cost2.setTranslateX(300);
		cost2.setTranslateY(250);
		Text range2 = new Text("Range: "
				+ c.getAbilities().get(1).getCastRange());
		range2.getFont();
		range2.setFont(Font.font(12));
		range2.setFill(Color.WHITE);
		range2.setTranslateX(20);
		range2.setTranslateY(270);
		Text area2 = new Text("Area: "
				+ c.getAbilities().get(1).getCastArea());
		area2.getFont();
		area2.setFont(Font.font(12));
		area2.setFill(Color.WHITE);
		area2.setTranslateX(300);
		area2.setTranslateY(270);
		Text action2 = new Text("ActionPoints: "
				+ c.getAbilities().get(1).getRequiredActionPoints());
		action2.getFont();
		action2.setFont(Font.font(12));
		action2.setFill(Color.WHITE);
		action2.setTranslateX(20);
		action2.setTranslateY(290);
		if (c.getAbilities().get(1) instanceof HealingAbility) {
			Text Heal1 = new Text("Heal Amount: "
					+ ((HealingAbility) (c.getAbilities().get(1))).getHealAmount());
			Heal1.getFont();
			Heal1.setFont(Font.font(12));
			Heal1.setFill(Color.WHITE);
			Heal1.setTranslateX(300);
			Heal1.setTranslateY(290);

			Text Type1 = new Text("Type: HealingAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(150);
			Type1.setTranslateY(310);
			root.getChildren().addAll(Heal1,Type1);
		}else if (c.getAbilities().get(1) instanceof DamagingAbility) {
			Text Damage1 = new Text("Damage Amount: "
					+ ((DamagingAbility) (c.getAbilities().get(1))).getDamageAmount());
			Damage1.getFont();
			Damage1.setFont(Font.font(12));
			Damage1.setFill(Color.WHITE);
			Damage1.setTranslateX(300);
			Damage1.setTranslateY(290);
			
			Text Type1 = new Text("Type: DamagingAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(150);
			Type1.setTranslateY(310);
			root.getChildren().addAll(Damage1,Type1);
		} else {
			Text Effect1 = new Text("Effect: "
					+ ((CrowdControlAbility) (c.getAbilities().get(1))).getEffect().getName());
			Effect1.getFont();
			Effect1.setFont(Font.font(12));
			Effect1.setFill(Color.WHITE);
			Effect1.setTranslateX(300);
			Effect1.setTranslateY(290);
			
			Text EDuration1 = new Text("Effect Duration: "
					+ ((CrowdControlAbility) (c.getAbilities().get(1))).getEffect().getDuration());
			EDuration1.getFont();
			EDuration1.setFont(Font.font(12));
			EDuration1.setFill(Color.WHITE);
			EDuration1.setTranslateX(300);
			EDuration1.setTranslateY(310);
			
			Text Type1 = new Text("Type: CrowdControlAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(20);
			Type1.setTranslateY(310);
			root.getChildren().addAll(Effect1,Type1,EDuration1);
		}

		
		
		
//a3-----------------------------------------------------------------------------------------------------------------
		Text a3 = new Text("Ability3: " + abi3);
		a3.getFont();
		a3.setFont(Font.font(16));
		a3.setFill(Color.WHITE);
		a3.setTranslateX(5);
		a3.setTranslateY(330);

		Text c3 = new Text("CD: "
				+ c.getAbilities().get(2).getBaseCooldown());
		c3.getFont();
		c3.setFont(Font.font(12));
		c3.setFill(Color.WHITE);
		c3.setTranslateX(20);
		c3.setTranslateY(350);
		Text cost3 = new Text("Cost: "
				+ c.getAbilities().get(2).getManaCost());
		cost3.getFont();
		cost3.setFont(Font.font(12));
		cost3.setFill(Color.WHITE);
		cost3.setTranslateX(300);
		cost3.setTranslateY(350);
		Text range3 = new Text("Range: "
				+ c.getAbilities().get(2).getCastRange());
		range3.getFont();
		range3.setFont(Font.font(12));
		range3.setFill(Color.WHITE);
		range3.setTranslateX(20);
		range3.setTranslateY(370);
		Text area3 = new Text("Area: "
				+ c.getAbilities().get(2).getCastArea());
		area3.getFont();
		area3.setFont(Font.font(12));
		area3.setFill(Color.WHITE);
		area3.setTranslateX(300);
		area3.setTranslateY(370);
		Text action3 = new Text("ActionPoints: "
				+ c.getAbilities().get(2).getRequiredActionPoints());
		action3.getFont();
		action3.setFont(Font.font(12));
		action3.setFill(Color.WHITE);
		action3.setTranslateX(20);
		action3.setTranslateY(390);
		if (c.getAbilities().get(2) instanceof HealingAbility) {
			Text Heal1 = new Text("Heal Amount: "
					+ ((HealingAbility) (c.getAbilities().get(2))).getHealAmount());
			Heal1.getFont();
			Heal1.setFont(Font.font(12));
			Heal1.setFill(Color.WHITE);
			Heal1.setTranslateX(300);
			Heal1.setTranslateY(390);

			Text Type1 = new Text("Type: HealingAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(150);
			Type1.setTranslateY(410);
			root.getChildren().addAll(Heal1,Type1);
		}else if (c.getAbilities().get(2) instanceof DamagingAbility) {
			Text Damage1 = new Text("Damage Amount: "
					+ ((DamagingAbility) (c.getAbilities().get(2))).getDamageAmount());
			Damage1.getFont();
			Damage1.setFont(Font.font(12));
			Damage1.setFill(Color.WHITE);
			Damage1.setTranslateX(300);
			Damage1.setTranslateY(390);
			
			Text Type1 = new Text("Type: DamagingAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(150);
			Type1.setTranslateY(410);
			root.getChildren().addAll(Damage1,Type1);
		} else {
			Text Effect1 = new Text("Effect: "
					+ ((CrowdControlAbility) (c.getAbilities().get(2))).getEffect().getName());
			Effect1.getFont();
			Effect1.setFont(Font.font(12));
			Effect1.setFill(Color.WHITE);
			Effect1.setTranslateX(300);
			Effect1.setTranslateY(390);
			
			Text EDuration1 = new Text("Effect Duration: "
					+ ((CrowdControlAbility) (c.getAbilities().get(2))).getEffect().getDuration());
			EDuration1.getFont();
			EDuration1.setFont(Font.font(12));
			EDuration1.setFill(Color.WHITE);
			EDuration1.setTranslateX(300);
			EDuration1.setTranslateY(410);
			
			Text Type1 = new Text("Type: CrowdControlAbility");
			Type1.getFont();
			Type1.setFont(Font.font(12));
			Type1.setFill(Color.WHITE);
			Type1.setTranslateX(20);
			Type1.setTranslateY(410);
			root.getChildren().addAll(Effect1,Type1,EDuration1);
		}
		root.getChildren().addAll(c1, c2, c3, a1, a2, a3, name, type,
				attackDamage, speed, mana, hp, actions, attackRange,action1,cost1,area1,range1,action2,cost2,area2,range2,action3,cost3,area3,range3);
		getChildren().addAll(bg, root);
	}
}
