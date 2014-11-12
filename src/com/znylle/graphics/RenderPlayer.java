package com.znylle.graphics;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.znylle.entities.Player;
import com.znylle.main.Game;
import com.znylle.objects.Armor;
import com.znylle.objects.Item;
import com.znylle.objects.Weapon;

public class RenderPlayer {
	private int playerPos;
	private String playerName;
	private int playerHealth;
	private int playerMaxHealth;
	private double playerMultiplier;
	private int playerLevel;
	private Weapon playerWeapon;
	private Armor playerArmor;
	private int playerMoney;
	private int playerExpForNextLevel;
	private int playerExperience;
	private int playerWalkState = 1;

	private boolean playerOrCreatureDead = false;

	private Image iWeapon;
	private long time = -100;
	private long time2 = 0;
	private int nextCreatureDir = 1;
	private GameImages gameImages;
	private Item drop;

	private Animation player, left, right, standR, standL;

	public RenderPlayer(GameImages gameImages) throws SlickException {
		this.gameImages = gameImages;
		newArmorAnimations();
	}

	public void newArmorAnimations() {
		if (playerArmor != null) {
			this.right = gameImages.armorAnimationsR.get(playerArmor.getName());
			this.left = gameImages.armorAnimationsL.get(playerArmor.getName());
			this.standL = gameImages.armorAnimationsSL.get(playerArmor.getName());
			this.standR = gameImages.armorAnimationsSR.get(playerArmor.getName());
			this.player = standR;
		}
	}

	public void renderPlayer(Graphics g) {
		int dif = 0;
		int dir = 0;
		if (playerWalkState > 0) { // segun la direccion que dibuje el arma donde sea correcto
			iWeapon = gameImages.weaponAnimations.get(playerWeapon.getName()).getCurrentFrame().getFlippedCopy(false, false);
			dir = 1;
			dif = 30;
		} else {
			iWeapon = gameImages.weaponAnimations.get(playerWeapon.getName()).getCurrentFrame().getFlippedCopy(true, false);
			dir = -1;
			dif = -10;
		}
		if (System.currentTimeMillis() - time < 100) {
			if (nextCreatureDir > 0) {
				iWeapon.setRotation(45 * dir); // para q cuando pegue mueva el arma como si estuviera usando una espada
				iWeapon.draw(playerPos * 6 + 18 + dif, 170);
				player.draw(playerPos * 6 + 18, 140);
			} else {
				iWeapon.setRotation(45 * dir);
				iWeapon.draw(playerPos * 6 - 18 + dif, 170);
				player.draw(playerPos * 6 - 18, 140);
			}
		} else {
			iWeapon.draw(playerPos * 6 + dif, 160);
			player.draw(playerPos * 6, 140);
		}
		if (System.currentTimeMillis() - time2 < 5000) {
			g.drawString("Got drop:" + drop.getName() + " Att:" + drop.getDamage() + " Def:" + drop.getDefense(), 70, 0);
		}
		iWeapon.setRotation(0);
		iWeapon = gameImages.weaponAnimations.get(playerWeapon.getName()).getCurrentFrame().getFlippedCopy(false, false);
		g.drawString(playerName + " (lvl " + playerLevel + "):", 10, 270);
		g.drawString("Health: ", 10, 300);
		gameImages.healthBarMarkImage.draw(80, 297);
		gameImages.healthBarImage.getScaledCopy((int) (((double) playerHealth / (double) playerMaxHealth) * 120), 18).draw(83, 301);
		g.drawString(playerHealth + "/" + playerMaxHealth, 85, 300);
		g.drawString("Attack: " + (int) (playerWeapon.getDamage() * playerMultiplier), 10, 330);
		g.drawString("Defense: " + (int) (playerArmor.getDefense() * playerMultiplier), 10, 345);
		g.drawString("Money: " + playerMoney, 10, 360);
		g.drawString("Exp: " + playerExperience + "/" + playerExpForNextLevel, 10, 375);
		iWeapon.draw(220, 300);
		gameImages.itemsImages.get(playerArmor.getName()).draw(220, 358);
		g.drawString(playerWeapon.getName() + ":", 220, 283);
		g.drawString("Att:+" + playerWeapon.getDamage(), 260, 313);
		g.drawString(playerArmor.getName() + ":", 220, 340);
		g.drawString("Def:+" + playerArmor.getDefense(), 260, 370);
	}

	public void updatePlayer(Player player) {
		this.playerPos = player.getRealPosition();
		this.playerName = player.getName();
		this.playerHealth = player.getHealth();
		this.playerMaxHealth = player.getMaxHealth();
		this.playerMultiplier = player.getMultiplier();
		this.playerLevel = player.getLevel();
		this.playerWeapon = player.getWeapon();
		this.playerArmor = player.getArmor();
		this.playerMoney = player.getMoney();
		this.playerExpForNextLevel = player.getExpForNextLevel();
		this.playerExperience = player.getExperience();
		newArmorAnimations();
		updatePlayerWalkState(player.getPlayerWalkState());
		updateShowPlayerTurn(player.isMyAttackTurn());
	}

	public void updateShowPlayerTurn(boolean playerTurn) {
		if (playerTurn) {
			this.time = System.currentTimeMillis();
		}
		if (playerOrCreatureDead) {
			this.time = 0;
		}
	}

	public void updateGotDrop(boolean gotDrop, Item drop) {
		if (gotDrop) {
			this.time2 = System.currentTimeMillis();
			this.drop = drop;
		}
	}

	public void updatePlayerOrCreatureDead(boolean playerOrCreatureDead) {
		this.playerOrCreatureDead = playerOrCreatureDead;
	}

	public void updateNextCreatureDir(int nextCreatureDir) {
		this.nextCreatureDir = nextCreatureDir;
	}

	public void updatePlayerWalkState(int playerWalkState) { // para ver como dibujar el player (parado, caminando, etc)
		if (playerArmor != null) {
			this.playerWalkState = playerWalkState;
			switch (playerWalkState) {
			case Game.RIGHT:
				player = right;
				player.update(200);
				break;
			case Game.LEFT:
				player = left;
				player.update(200);
				break;
			case -2:
				player = standL;
				break;
			case 2:
				player = standR;
				break;
			}
		}
	}

}
