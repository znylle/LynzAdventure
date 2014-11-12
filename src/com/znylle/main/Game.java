package com.znylle.main;

import java.util.ArrayList;

import com.znylle.entities.Creature;
import com.znylle.entities.Player;
import com.znylle.logic.GameLogic;
import com.znylle.objects.Armor;
import com.znylle.objects.CreatureSlots;
import com.znylle.objects.Weapon;
import com.znylle.observers.ObserverCreatureSlots;
import com.znylle.observers.ObserverGameLogic;
import com.znylle.observers.ObserverInputProcessorMenu;
import com.znylle.observers.ObserverPlayer;

public final class Game {
	// clase contenedora de todo lo que refiere a la parte no grafica
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final float MULT_PER_LEVEL = 1.15f;
	public static final int BOSS = 10;
	public static final int numberOfMapsDivided10 = 10;

	private static Game game = null;

	private GameLogic gameLogic;

	private CreatureSlots creatureSlots;
	private Player player;

	private ObserverPlayer observerPlayer;
	private ObserverCreatureSlots observerCreatureSlots;
	private ObserverGameLogic observerGameLogic;
	private ObserverInputProcessorMenu observerInputProcessorMenu;

	private ArrayList<Creature> creatures;
	private ArrayList<Weapon> weapons;
	private ArrayList<Armor> armors;
	private ArrayList<Creature> bosses;

	private String[] bossNames = { "Queen Bee(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", "Rattarchy(Boss)", };
	private int[][] bossStats = { { 30, 40, 200, 200 }, { 50, 70, 400, 400 }, { 80, 110, 600, 800 }, { 10000, 10000, 100000, 10000 }, { 10000, 10000, 100000, 10000 }, { 10000, 10000, 100000, 10000 }, { 10000, 10000, 100000, 10000 }, { 10000, 10000, 100000, 10000 }, { 10000, 10000, 100000, 10000 }, { 10000, 10000, 100000, 10000 } };

	private String[] creatureNames = { "ET", "Ugly Jelly", "Scorpion", "Bodybuilder Flower", "Killer Banana", "Crazy Tongue", "ET", "ET", "ET", "ET" };

	private int[][] creatureStats = { { 4, 3, 10, 1 }, { 4, 4, 12, 1 }, { 1, 6, 6, 1 }, { 3, 4, 11, 1 }, { 5, 5, 8, 2 }, { 5, 4, 7, 1 }, { 4, 3, 12, 1 }, { 3, 4, 13, 1 }, { 2, 3, 15, 1 }, { 4, 4, 9, 2 } };

	private String[] weaponNames = { "Line", "Wooden Stick", "Stone Knife", "Steel Short Sword", "Magic Staff", "Whip", "War Axe", "Rainbow Mjölnir", "Invisible Weapon", "Ipad Air S", "ERROR" };
	private int[][] weaponStats = { { 7, 1 }, { 9, 26 }, { 11, 135 }, { 13, 246 }, { 15, 578 }, { 17, 897 }, { 19, 1258 }, { 21, 2000 }, { 23, 3694 }, { 30, 8650 }, { 100, 100000 } };

	private String[] armorNames = { "Clothes", "Croc Armor", "Rainbow", "Wiz Robes", "Steel Armor", "Gold Fever", "Night Armor", "Devil Armor", "Ghost", "ERROR" };
	private int[][] armorStats = { { 1, 1 }, { 3, 22 }, { 5, 145 }, { 7, 258 }, { 9, 597 }, { 11, 900 }, { 13, 1324 }, { 15, 2089 }, { 17, 3845 }, { 30, 9000 } };

	private String[] backgroundNames = { "Home City", "Forest", "Ruins", "Desert", "Beach", "Castle(outside)", "Castle(inside)", "Snow", "Dark Forest", "Final Castle", "You Win" };

	public static Game getInstance() {
		if (game == null) {
			game = new Game();
		}
		return game;
	}

	/* Creador de Creatures */
	public void creaturesBuilder() {
		creatures = new ArrayList<>();
		for (int i = 0; i < creatureNames.length; i++) {
			creatures.add(new Creature(creatureNames[i], creatureStats[i][0], creatureStats[i][1], creatureStats[i][2], creatureStats[i][3]));
		}
	}

	/* ********************** */
	/* Creador de Weapons */
	public void weaponsBuilder() {
		weapons = new ArrayList<>();
		for (int i = 0; i < weaponNames.length; i++) {
			weapons.add(new Weapon(weaponNames[i], weaponStats[i][0], weaponStats[i][1]));
		}
	}

	/* ********************* */
	/* Creador de Armors */
	public void armorBuilder() {
		armors = new ArrayList<>();
		for (int i = 0; i < armorNames.length; i++) {
			armors.add(new Armor(armorNames[i], armorStats[i][0], armorStats[i][1]));
		}
	}

	/* ********************* */
	/* Creador de Bosses */
	public void bossesBuilder() {
		bosses = new ArrayList<>();
		int size = bossNames.length;
		for (int i = 0; i < size; i++) {
			bosses.add(new Creature(bossNames[i], bossStats[i][0], bossStats[i][1], bossStats[i][2], bossStats[i][3]));
		}
	}

	/* ********************* */

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String[] getCreatureNames() {
		return creatureNames;
	}

	public void setCreatureNames(String[] creatureNames) {
		this.creatureNames = creatureNames;
	}

	public GameLogic getGameLogic() {
		return gameLogic;
	}

	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}

	public CreatureSlots getCreatureSlots() {
		return creatureSlots;
	}

	public void setCreatureSlots(CreatureSlots creatureSlots) {
		this.creatureSlots = creatureSlots;
	}

	public ArrayList<Creature> getCreatures() {
		return creatures;
	}

	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}

	public String[] getWeaponNames() {
		return weaponNames;
	}

	public void setWeaponNames(String weaponNames[]) {
		this.weaponNames = weaponNames;
	}

	public int[][] getWeaponStats() {
		return weaponStats;
	}

	public void setWeaponStats(int weaponStats[][]) {
		this.weaponStats = weaponStats;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public String[] getArmorNames() {
		return armorNames;
	}

	public void setArmorNames(String armorNames[]) {
		this.armorNames = armorNames;
	}

	public int[][] getArmorStats() {
		return armorStats;
	}

	public void setArmorStats(int armorStats[][]) {
		this.armorStats = armorStats;
	}

	public ArrayList<Armor> getArmors() {
		return armors;
	}

	public void setArmors(ArrayList<Armor> armors) {
		this.armors = armors;
	}

	public int[][] getBossStats() {
		return bossStats;
	}

	public void setBossStats(int bossStats[][]) {
		this.bossStats = bossStats;
	}

	public String[] getBossNames() {
		return bossNames;
	}

	public void setBossNames(String bossNames[]) {
		this.bossNames = bossNames;
	}

	public ArrayList<Creature> getBosses() {
		return bosses;
	}

	public void setBosses(ArrayList<Creature> bosses) {
		this.bosses = bosses;
	}

	public String[] getBackgroundNames() {
		return backgroundNames;
	}

	public void setBackgroundNames(String[] backgroundNames) {
		this.backgroundNames = backgroundNames;
	}

	public ObserverPlayer getObserverPlayer() {
		return observerPlayer;
	}

	public void setObserverPlayer(ObserverPlayer observerPlayer) {
		this.observerPlayer = observerPlayer;
	}

	public ObserverCreatureSlots getObserverCreatureSlots() {
		return observerCreatureSlots;
	}

	public void setObserverCreatureSlots(ObserverCreatureSlots observerCreatureSlots) {
		this.observerCreatureSlots = observerCreatureSlots;
	}

	public ObserverGameLogic getObserverGameLogic() {
		return observerGameLogic;
	}

	public void setObserverGameLogic(ObserverGameLogic observerGameLogic) {
		this.observerGameLogic = observerGameLogic;
	}

	public ObserverInputProcessorMenu getObserverInputProcessorMenu() {
		return observerInputProcessorMenu;
	}

	public void setObserverInputProcessorMenu(ObserverInputProcessorMenu observerInputProcessorMenu) {
		this.observerInputProcessorMenu = observerInputProcessorMenu;
	}

}
