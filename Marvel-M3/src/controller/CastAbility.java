package controller;

import model.abilities.Ability;
import model.world.Champion;

import java.io.IOException;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CastAbility extends StackPane {
	Champion curr = Console.game.getCurrentChampion();
	GUI gui;

	public CastAbility(Ability a) {
		Rectangle bg = new Rectangle(160, 40);
		bg.setOpacity(0.2);
		Text name = new Text(a.getName());
		name.setFill(Color.WHITE);
		bg.setOnMouseEntered(e -> {
			bg.setFill(Color.GREY);
			bg.setOpacity(0.4);
		});
		bg.setOnMouseExited(e -> {
			bg.setFill(Color.BLACK);
			bg.setOpacity(0.2);
		});
		this.getChildren().addAll(name,bg);
	}
	
}
