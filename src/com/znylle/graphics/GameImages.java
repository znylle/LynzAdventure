package com.znylle.graphics;

import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameImages {
	// inicializa todas las imagenes y animations para su uso posterior
	protected HashMap<String, Image> itemsImages;
	protected HashMap<Integer, Image> backgroundImages;
	protected HashMap<String, Image[]> creaturesImages;
	/* Para stats */
	protected Image healthBarImage = new Image("data/Other/HealthBar.png");
	protected Image healthBarMarkImage = new Image("data/Other/HealthBarMark.png");
	/*-----------*/
	/* Para RenderPlayer */
	protected HashMap<String, Animation> weaponAnimations;
	protected HashMap<String, Animation> armorAnimationsR;
	protected HashMap<String, Animation> armorAnimationsL;
	protected HashMap<String, Animation> armorAnimationsSR;
	protected HashMap<String, Animation> armorAnimationsSL;
	int[] duration = { 200, 200, 200, 200 };// Velocidad de drawing del movimiento de caminar
	int duration2 = 200;// Velocidad de drawing del movimiento de caminar
	/*-----------------*/
	/* Para RenderBackground */
	protected Image separatorImage = new Image("data/Other/Separator.png");
	protected Image separator2Image = new Image("data/Other/Separator2.png");
	/*---------------------*/
	/* Para RenderStore */
	protected Image storeAndMenuBackgroundImage = new Image("data/Store/StoreBackground.png");

	/*-----------------*/

	public GameImages(String[] creatureNames, String[] bossNames, String[] weaponNames, String[] armorNames, String[] backgroundNames, int numberOfMapsDivided10) throws SlickException {
		itemsImages = new HashMap<>();
		backgroundImages = new HashMap<>();
		creaturesImages = new HashMap<>();
		weaponAnimations = new HashMap<>();
		armorAnimationsR = new HashMap<>();
		armorAnimationsL = new HashMap<>();
		armorAnimationsSR = new HashMap<>();
		armorAnimationsSL = new HashMap<>();
		createArmorAnimations(armorNames);
		createItemsImages(weaponNames, armorNames);
		createWeaponAnimations();
		createBackgroundImages(backgroundNames, numberOfMapsDivided10);
		createCreaturesImages(creatureNames, bossNames);
	}

	public void createArmorAnimations(String[] armorNames) throws SlickException {
		int size = armorNames.length;
		Image movementRight1, movementRight2, movementRight3, movementLeft1, movementLeft2, movementLeft3;
		Image[] movementRight, movementLeft, playerStandR, playerStandL;
		for (int i = 0; i < size; i++) {
			movementRight1 = new Image("data/Player/" + armorNames[i] + "/1.png");
			movementRight2 = new Image("data/Player/" + armorNames[i] + "/2.png");
			movementRight3 = new Image("data/Player/" + armorNames[i] + "/3.png");
			movementLeft1 = new Image("data/Player/" + armorNames[i] + "/1L.png");
			movementLeft2 = new Image("data/Player/" + armorNames[i] + "/2L.png");
			movementLeft3 = new Image("data/Player/" + armorNames[i] + "/3L.png");
			movementRight = new Image[] { movementRight2, movementRight1, movementRight2, movementRight3 };
			movementLeft = new Image[] { movementLeft1, movementLeft2, movementLeft3, movementLeft2 };
			playerStandR = new Image[] { movementRight2 };
			playerStandL = new Image[] { movementLeft2 };
			armorAnimationsR.put(armorNames[i], new Animation(movementRight, duration, false));
			armorAnimationsSR.put(armorNames[i], new Animation(playerStandR, duration2, false));
			armorAnimationsL.put(armorNames[i], new Animation(movementLeft, duration, false));
			armorAnimationsSL.put(armorNames[i], new Animation(playerStandL, duration2, false));
		}
	}

	public void createWeaponAnimations() {
		for (Entry<String, Image> aux : itemsImages.entrySet()) {
			weaponAnimations.put(aux.getKey(), new Animation(new Image[] { aux.getValue() }, 10, false));
		}

	}

	public void createItemsImages(String[] weaponNames, String[] armorNames) throws SlickException {
		int size = weaponNames.length;
		for (int i = 0; i < size; i++) {
			itemsImages.put(weaponNames[i], new Image("data/Weapons/" + weaponNames[i] + ".png"));
		}
		size = armorNames.length;
		for (int i = 0; i < size; i++) {
			itemsImages.put(armorNames[i], new Image("data/Armors/" + armorNames[i] + ".png"));
		}
	}

	public void createBackgroundImages(String[] backgroundNames, int numberOfMapsDivided10) throws SlickException {
		for (int i = 0; i <= 10; i++) {
			backgroundImages.put(i, new Image("data/Backgrounds/" + backgroundNames[i] + ".png"));
		}
	}

	public void createCreaturesImages(String[] creatureNames, String[] bossNames) throws SlickException {
		int size = creatureNames.length;
		for (int i = 0; i < size; i++) {
			creaturesImages.put(creatureNames[i], new Image[] { new Image("data/Creatures/" + creatureNames[i] + ".png") });
		}
		size = bossNames.length;
		for (int i = 0; i < size; i++) {
			creaturesImages.put(bossNames[i], new Image[] { new Image("data/Creatures/" + bossNames[i] + ".png") });
		}
	}

}
