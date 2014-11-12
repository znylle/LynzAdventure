package com.znylle.objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Item implements Serializable {
	// item
	protected String name;
	protected int defense;
	protected int damage;
	protected int price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
