package com.znylle.objects;

@SuppressWarnings("serial")
public class Armor extends Item {
	// armor

	public Armor(String name, int defense, int price) {
		this.defense = defense;
		this.price = price;
		this.name = name;
	}
}
