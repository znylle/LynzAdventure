package com.znylle.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Entity implements Serializable {
	// Clase padre de creature y player
	protected String name;
	protected int health;
	protected int maxHealth;
	protected int speed;
	protected int level;
	protected int money;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;

	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void downHealth(int down) {
		setHealth(getHealth() - down);
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

}
