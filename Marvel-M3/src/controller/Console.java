package controller;

import java.util.ArrayList;

import engine.*;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.abilities.Ability;
import model.world.Champion;
import model.world.Direction;

public class Console {
	public static Player player1;
	public static Player player2;
	static ArrayList<Champion> champions = Game.getAvailableChampions();
	static Game game;

	public static void addingPlayerNames(String p1, String p2) {
		player1 = new Player(p1);
		player2 = new Player(p2);

	}

	public static void main(String[] args) throws Exception {
		System.out.println("hello");
		Game.loadAbilities("Abilities.csv");

		Game.loadChampions("Champions.csv");

		Application.launch(GUI.class, args);
	}

	public static Champion getChampion(String s) {

		for (int i = 0; i < champions.size(); i++) {
			if (champions.get(i).getName().equals(s))
				return champions.get(i);
		}
		return null;
	}

	// FALLA7A
	public static void addChampion(String s, int count) {
		for (int i = 0; i < champions.size(); i++) {
			if (champions.get(i).getName().equals(s)) {
				if (count % 2 == 0)
					player1.getTeam().add(champions.get(i));

				if (count % 2 != 0)
					player2.getTeam().add(champions.get(i));

			}
		}
		int k = 0;
		while (k < player1.getTeam().size()) {
			System.out.println(player1.getName()
					+ player1.getTeam().get(k).getName());
			k++;
		}
		System.out.println();
		int f = 0;
		while (f < player2.getTeam().size()) {
			System.out.println(player2.getName()
					+ player2.getTeam().get(f).getName());
			f++;
		}
	}

	public static void startGame() {
		game = new Game(player1, player2);

	}

	public static Object[][] getBoard() {
		return game.getBoard();
	}

	public static void move(Direction s) throws NotEnoughResourcesException,
			UnallowedMovementException {
		game.move(s);
		System.out.println("Moved" + s);
	}

	public static void attack(Direction s) throws NotEnoughResourcesException, ChampionDisarmedException, InvalidTargetException{
		game.attack(s);
		System.out.println("hp"
				+ game.getCurrentChampion().getCurrentActionPoints());
	}

	public static void castability(Ability a)
			throws NotEnoughResourcesException, InvalidTargetException,
			AbilityUseException, CloneNotSupportedException {
		game.castAbility(a);
	}

	public static void castability1(Ability a, Direction d)
			throws NotEnoughResourcesException, InvalidTargetException,
			AbilityUseException, CloneNotSupportedException {
		game.castAbility(a, d);
	}

	public static void castability2(Ability a, int x, int y)
			throws NotEnoughResourcesException, InvalidTargetException,
			AbilityUseException, CloneNotSupportedException {
		game.castAbility(a, x, y);
	}

	public static void endTurn() {
		game.endTurn();
	}

	public static void useLeaderAbility() throws LeaderNotCurrentException,
			LeaderAbilityAlreadyUsedException {
		game.useLeaderAbility();
	}

	public static void setLeader1(Champion c){
		player1.setLeader(c);
	}
	public static void setLeader2(Champion c){
		player2.setLeader(c);
	}
}
