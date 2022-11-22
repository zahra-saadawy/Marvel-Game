package controller;

import java.util.ArrayList;

import engine.PriorityQueue;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.world.Champion;

public class Turns extends StackPane {
	public Turns() {
		Rectangle bg = new Rectangle(700, 50);
		bg.setOpacity(0.7);
		bg.setEffect(new GaussianBlur(3.5));
		
		PriorityQueue turn1 = Console.game.getTurnOrder();
		ArrayList<Champion> temp = new ArrayList<Champion>();
		Champion playing = (Champion) turn1.remove();
		while (!turn1.isEmpty()) {
			temp.add((Champion) turn1.remove());
		}
		String champs = ("Turn Order: " + playing.getName() +", ");
		for (int i=0; i < temp.size();i++) {
			turn1.insert(temp.get(i));
			champs = champs + temp.get(i).getName() +", ";
		}
		turn1.insert(playing);
		Text order = new Text(champs);
		order.getFont();
		order.setFont(Font.font(20));
		order.setFill(Color.WHITE);
		this.getChildren().addAll(bg,order);
	}
}
