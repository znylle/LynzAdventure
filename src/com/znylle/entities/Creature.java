package com.znylle.entities;

@SuppressWarnings("serial")
public class Creature extends Entity {
	// Clase que se encarga de los bichos.
	private int defense;
	private int attack;

	public Creature(Creature creature) {
		this.name = creature.name;
		this.setDefense(creature.defense);
		this.setAttack(creature.attack);
		this.health = this.maxHealth = creature.health;
		this.money = creature.money;
	}

	public Creature(String name, int defense, int attack, int health, int money) {
		this.name = name;
		this.setDefense(defense);
		this.setAttack(attack);
		this.health = this.maxHealth = health;
		this.money = money;
	}

	public void creAttack(Player player) {
		if (attack - (int) (player.getDefense() * player.getMultiplier()) > 0) {
			player.downHealth(attack - (int) (player.getDefense() * player.getMultiplier())); // funcion de ataque de los bichos
		}
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public Creature modify(int map) { // esto es para que los bichos sean más potentes segun el mapa (ya que son los mismos siempre)
		if (map != 1) { // sin el if los bichos pueden tener 0 de algun atributo
			this.attack *= map * 0.6;
			this.defense *= map * 0.6;
			this.maxHealth *= map * 0.6;
			this.health *= map * 0.6;
			this.money *= map * 1.5;
		}
		return this;
	}
}
