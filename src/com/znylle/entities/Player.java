package com.znylle.entities;

import java.io.Serializable;
import java.util.ArrayList;

import com.znylle.main.Game;
import com.znylle.objects.Armor;
import com.znylle.objects.Weapon;
import com.znylle.observers.Observable;
import com.znylle.observers.Observer;

@SuppressWarnings("serial")
public class Player extends Entity implements Observable<Player>, Serializable {
	// Clase del jugador
	private static final int standR = 2;
	private static final int standL = -2;
	private Armor armor;
	private Weapon weapon;
	private transient Observer<Player> observer;
	private int realPosition;
	private int experience;
	private int expForNextLevel;
	private double multiplier;
	private int bossKills;
	private long timeOfEachStep = 100;
	private int playerWalkState = 1;
	private int timeAttackSpeed = 600 - speed * 50;
	private long time = 0;
	private long time2 = 0;

	private boolean myAttackTurn = false;

	// Arreglar que el att speed y walk speed este dado x speed
	public Player(String name) {
		this.name = name;
		this.speed = this.level = 1;
		this.money = 10000;
		this.health = this.maxHealth = this.expForNextLevel = 25;
		this.multiplier = 1;
		this.setRealPosition(0);
		this.bossKills = 0;
	}

	public void plaAttack(Creature creature) {
		if (System.currentTimeMillis() - time2 > timeAttackSpeed) { // para que uno ataque cada timeattackspeed milisegundos
			setMyAttackTurn(true); // los turnos son para que el render sepa cuando mostrar que ataca el player o el bicho
			if ((int) (getAttack() * multiplier) - creature.getDefense() > 0) {
				creature.downHealth((int) (getAttack() * multiplier) - creature.getDefense());
			}
			time2 = System.currentTimeMillis();
			setMyAttackTurn(false);
		}
	}

	public void walk(int dir, int condition) { // condition es para cuando la speed del player es mayor
		if (System.currentTimeMillis() - time > timeOfEachStep) { // que 1 y que no pise a los bichos(que las imagenes no se solapen)
			if (condition == 0) {
				setRealPosition((int) (getRealPosition() + dir * speed));
			} else {
				setRealPosition(getRealPosition() + dir * condition);
			}
			setPlayerWalkState(dir);
			time = System.currentTimeMillis();
		}
	}

	public boolean stand(boolean walked) {
		// esta funcion es solamente para avisarle a la parte grafica que si esta parado que no muestre un sprite de cuando esta caminando
		if (System.currentTimeMillis() - time > timeOfEachStep && walked) {
			if (playerWalkState == Game.LEFT) {
				setPlayerWalkState(standL);
			} else if (playerWalkState == Game.RIGHT) {
				setPlayerWalkState(standR);
			}
			return false;
		}
		return true;
	}

	public int getExpForNextLevel() {
		return expForNextLevel;
	}

	public void setExpForNextLevel() {
		// funcion para ver la exp para el sig nivel
		this.expForNextLevel = (int) (25 * (Math.pow((double) Game.MULT_PER_LEVEL, this.level) - 1) / (Game.MULT_PER_LEVEL - 1));
		notifyObservers(this);
	}

	@Override
	public void registerObserver(Observer<Player> obj) {
		observer = obj;
		notifyObservers(this);
	}

	@Override
	public void removeObserver(Observer<Player> obj) {
		observer = null;
	}

	@Override
	public void notifyObservers(Player obj) {
		if (observer != null)
			observer.update(obj);
	}

	public int getRealPosition() {
		return realPosition;
	}

	public int setRealPosition(int realPosition) {
		this.realPosition = realPosition;
		notifyObservers(this);
		return realPosition;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		notifyObservers(this);
	}

	public Armor getArmor() {
		return armor;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
		notifyObservers(this);
	}

	public int getAttack() {
		return weapon.getDamage();
	}

	public int getDefense() {
		return armor.getDefense();
	}

	public void killCreature(Creature creature) {
		setMoney(getMoney() + creature.getMoney());
		setExperience(getExperience() + (int) (creature.getMaxHealth() * 0.75));
		if (expForNextLevel <= experience) {
			levelUp();
			setExpForNextLevel();
		}
	}

	public void getDropArmor(Armor newArmor) {
		setArmor(newArmor);
	}

	public void getDropWeapon(Weapon newWeapon) {
		setWeapon(newWeapon);
	}

	public void levelUp() {
		setLevel(level + 1);
		setMaxHealth((int) (maxHealth + 10 * multiplier));
		setMultiplier(multiplier + 0.1);
		if (speed < 5 && level > 19) {
			setSpeed((int) (1 + level * 0.05));
		}
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
		notifyObservers(this);
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
		notifyObservers(this);
	}

	public int getBossKills() {
		return bossKills;
	}

	public boolean buyArmor(ArrayList<Armor> armors) {
		if (armors.indexOf(this.armor) != armors.size() - 1) {
			Armor possiblePurchase = armors.get(armors.indexOf(this.armor) + 1);
			if (possiblePurchase.getPrice() <= money) {
				setArmor(possiblePurchase);
				setMoney(money - possiblePurchase.getPrice());
				return true;
			}
		}
		return false;
	}

	public void buyWeapon(ArrayList<Weapon> weapons) {
		if (weapons.indexOf(this.weapon) != weapons.size() - 1) {
			Weapon possiblePurchase = weapons.get(weapons.indexOf(this.weapon) + 1);
			if (possiblePurchase.getPrice() <= money) {
				setWeapon(possiblePurchase);
				setMoney(money - possiblePurchase.getPrice());
			}
		}
	}

	public void setBossKills(int bossKills) {
		this.bossKills = bossKills;
	}

	public void revive() {
		this.health = maxHealth;
		setRealPosition(0);
	}

	public boolean isKill() {
		if (health <= 0)
			return true;
		return false;
	}

	public void setName(String name) {
		this.name = name;
		notifyObservers(this);
	}

	public void setHealth(int health) {
		this.health = health;
		notifyObservers(this);
	}

	public void setLevel(int level) {
		this.level = level;
		notifyObservers(this);
	}

	public void setMoney(int money) {
		this.money = money;
		notifyObservers(this);
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		notifyObservers(this);
	}

	public int getPlayerWalkState() {
		return playerWalkState;
	}

	public void setPlayerWalkState(int playerWalkState) {
		this.playerWalkState = playerWalkState;
		notifyObservers(this);
	}

	public boolean isMyAttackTurn() {
		return myAttackTurn;
	}

	public void setMyAttackTurn(boolean myAttackTurn) {
		this.myAttackTurn = myAttackTurn;
		notifyObservers(this);
	}

}
