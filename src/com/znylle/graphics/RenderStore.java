package com.znylle.graphics;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.znylle.objects.Armor;
import com.znylle.objects.Weapon;

public class RenderStore {

	private boolean storeOpen = false;
	private ArrayList<Weapon> weapons;
	private ArrayList<Armor> armors;
	private Weapon pWeapon;
	private Armor pArmor;
	private GameImages gameImages;

	public RenderStore(ArrayList<Weapon> weapons, ArrayList<Armor> armors, String[] weaponNames, String[] armorNames, GameImages gameImages) throws SlickException {
		this.gameImages = gameImages;
		this.weapons = weapons;
		this.armors = armors;
	}

	public void renderStore(Graphics g) throws SlickException {
		if (storeOpen) {
			gameImages.storeAndMenuBackgroundImage.draw(170, 0);
			g.drawString("STORE:", 273, 8);
			g.drawString("Next Weapon: Buy(E)", 180, 40);
			if (!isLastWeapon()) {
				gameImages.itemsImages.get(weapons.get(weapons.indexOf(pWeapon) + 1).getName()).draw(185, 70);
				g.drawString("Name:" + weapons.get(weapons.indexOf(pWeapon) + 1).getName(), 235, 70);
				g.drawString("Attack:+" + weapons.get(weapons.indexOf(pWeapon) + 1).getDamage(), 235, 90);
				g.drawString("Price:" + weapons.get(weapons.indexOf(pWeapon) + 1).getPrice(), 235, 110);
			} else {
				g.drawString("You have the best \nweapon available", 185, 70);
			}
			g.drawString("Next Armor: Buy(Q)", 185, 140);
			if (!isLastArmor()) {
				gameImages.itemsImages.get(armors.get(armors.indexOf(pArmor) + 1).getName()).draw(185, 170);
				g.drawString("Name:" + armors.get(armors.indexOf(pArmor) + 1).getName(), 235, 170);
				g.drawString("Defense:+" + armors.get(armors.indexOf(pArmor) + 1).getDefense(), 235, 190);
				g.drawString("Price:" + armors.get(armors.indexOf(pArmor) + 1).getPrice(), 235, 210);
			} else {
				g.drawString("You have the best \narmor available", 185, 170);
			}
		}
	}

	public boolean isLastWeapon() {
		if (weapons.indexOf(pWeapon) == weapons.size() - 1) {
			return true;
		}
		return false;
	}

	public boolean isLastArmor() {
		if (armors.indexOf(pArmor) == armors.size() - 1) {
			return true;
		}
		return false;
	}

	public void updatePWeapon(Weapon pWeapon) {
		this.pWeapon = pWeapon;
	}

	public void updatePArmor(Armor pArmor) {
		this.pArmor = pArmor;
	}

	public void updateStoreOpen(boolean storeOpen) {
		this.storeOpen = storeOpen;
	}
}
