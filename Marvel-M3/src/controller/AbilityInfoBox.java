package controller;

import java.io.IOException;

import javafx.scene.effect.GaussianBlur;
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
import model.effects.Effect;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;

public class AbilityInfoBox extends StackPane {
	
	public AbilityInfoBox (String s) throws IOException {
		
		Pane root = new Pane();
		root.setPrefSize(500, 150);
		Rectangle bg = new Rectangle(500, 150);
		bg.setOpacity(0.7);
		// bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));
		Champion c = Console.game.getCurrentChampion();
		Ability a = null;
		if(c.getAbilities().get(0).getName().equals(s)) 
			a = c.getAbilities().get(0);
		else if (c.getAbilities().get(1).getName().equals(s))
			a = c.getAbilities().get(1);
		else a = c.getAbilities().get(2);
		
		String t = null;
		int z =0;
		String hehe = null;
		if(a instanceof HealingAbility) {
			t = "HealingAbility";
			z = ((HealingAbility) a).getHealAmount();
			 hehe = "Heal Amount: "+z;
			
	}
		else if(a instanceof DamagingAbility) {
			t = "DamagingAbility" ;
			z = ((DamagingAbility)a).getDamageAmount();
			 hehe = ("Damage Amount: "+z);
			
		}
		else {
			t = "CrowdControlAbility";
			Effect e = ((CrowdControlAbility)a ).getEffect();
			 hehe = "Effect: "+ e.getName()+ " duration: "+ e.getDuration();
			 
			
	}
		Text type = new Text("Type: "+t);
		type.setFont(new Font("Arial", 16));
		type.setFill(Color.WHITE);
		type.setTranslateX(5);
		type.setTranslateY(10);
		
		Text h = new Text(hehe);
		h.setFont(new Font("Arial", 16));
		h.setFill(Color.WHITE);
		h.setTranslateX(5);
		h.setTranslateY(130);
		
		Text area = new Text( "Area of Effect: "+a.getCastArea().toString());
		area.getFont();
		area.setFont(Font.font(16));
		area.setFill(Color.WHITE);
		area.setTranslateX(5);
		area.setTranslateY(40);

		Text range = new Text("Cast Range: "+a.getCastRange());
		range.getFont();
		range.setFont(Font.font(16));
		range.setFill(Color.WHITE);
		range.setTranslateX(250);
		range.setTranslateY(40);
		
		Text action = new Text ("Required Action Points: "+ Integer.toString(a.getRequiredActionPoints()));
		action.getFont();
		action.setFont(Font.font(16));
		action.setFill(Color.WHITE);
		action.setTranslateX(5);
		action.setTranslateY(70);
	
		Text mana = new Text ("Mana Cost: "+Integer.toString(a.getManaCost()));
		mana.getFont();
		mana.setFont(Font.font(16));
		mana.setFill(Color.WHITE);
		mana.setTranslateX(250);
		mana.setTranslateY(70);
		
		Text cool = new Text("Base CoolDown"+Integer.toString(a.getBaseCooldown()));
		cool.getFont();
		cool.setFont(Font.font(16));
		cool.setFill(Color.WHITE);
		cool.setTranslateX(5);
		cool.setTranslateY(100);
		
		Text coolcurr = new Text("Current CoolDown"+Integer.toString(a.getCurrentCooldown()));
		coolcurr.getFont();
		coolcurr.setFont(Font.font(16));
		coolcurr.setFill(Color.WHITE);
		coolcurr.setTranslateX(250);
		coolcurr.setTranslateY(100);
		
		
		root.getChildren().addAll(type, coolcurr, cool, mana, action, range, area, h);
		getChildren().addAll(bg, root);
	}
}