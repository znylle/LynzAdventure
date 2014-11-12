package com.znylle.objects;

@SuppressWarnings("serial")
public class Weapon extends Item {
	// weapon!!
	public Weapon(String name, int damage, int price) {
		this.damage = damage;
		this.price = price;
		this.name = name;
	}

}
