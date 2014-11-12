package com.znylle.graphics;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.znylle.entities.Creature;
import com.znylle.main.Game;
import com.znylle.objects.CreatureSlots;

public class RenderCreatures {

	private CreatureSlots creatures;
	private Creature nextCreature;
	private Image cImage;
	private Animation cAnimation;
	private long time = -100;
	private int nextCreaturePos = 0;
	private int nextCreatureDir = 1;
	private GameImages gameImages;

	public RenderCreatures(String[] creatureNames, String[] bossNames, GameImages gameImages) throws SlickException {
		this.gameImages = gameImages;
	}

	public void renderCreatures(Graphics g) throws SlickException {

		if (nextCreature != null) {
			for (int i = 0; i < 10; i++) {
				if (creatures.get(i) != null) {
					int j;
					cAnimation = new Animation(gameImages.creaturesImages.get(creatures.get(i).getName()), 10, false);
					if (nextCreatureDir == Game.LEFT) { // segun la direccion agarra la imagen normal o flipeada horizontalmente
						cImage = cAnimation.getCurrentFrame().getFlippedCopy(true, false);
						j = 18;
					} else {
						cImage = cAnimation.getCurrentFrame().getFlippedCopy(false, false);
						j = -18;
					}

					if (System.currentTimeMillis() - time < 100) { // esto hace que se mueva el bicho cuando ataca
						if (i == nextCreaturePos) {
							cImage.draw(i * 60 + j, 140);
						} else {
							cImage.draw(i * 60, 140);
						}
					} else {
						cImage.draw(i * 60, 140);
					}
				}
			}
			g.drawString(nextCreature.getName() + ":", 390, 270);
			g.drawString("Health: ", 390, 300);
			gameImages.healthBarMarkImage.draw(470, 297);
			gameImages.healthBarImage.getScaledCopy((int) (((double) nextCreature.getHealth() / (double) nextCreature.getMaxHealth()) * 120), 18).draw(473, 301);
			g.drawString(nextCreature.getHealth() + "/" + nextCreature.getMaxHealth(), 475, 300);
			g.drawString("Attack: " + nextCreature.getAttack(), 390, 330);
			g.drawString("Defense: " + nextCreature.getDefense(), 390, 345);
			g.drawString("Money: " + nextCreature.getMoney(), 390, 360);
			g.drawString("Exp: " + (int) (nextCreature.getMaxHealth() * 0.75), 390, 375);
		}
	}

	public void updateCreatures(CreatureSlots creatureSlots) {
		this.creatures = creatureSlots;
	}

	public void updateNextCreature(Creature nextCreature) {
		this.nextCreature = nextCreature;
	}

	public void updateShowCreatureTurn(boolean creatureTurn, boolean playerOrCreatureDead) {
		if (creatureTurn) {
			this.time = System.currentTimeMillis();
		}
		if (playerOrCreatureDead) {
			this.time = 0;
		}
	}

	public void updateNextCreaturePos(int nextCreaturePos) {
		this.nextCreaturePos = nextCreaturePos;
	}

	public void updateNextCreatureDir(int nextCreatureDir) {
		this.nextCreatureDir = nextCreatureDir;
	}

}
