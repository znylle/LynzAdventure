package com.znylle.objects;

import java.io.Serializable;
import java.util.ArrayList;

import com.znylle.entities.Creature;
import com.znylle.observers.Observable;
import com.znylle.observers.Observer;

@SuppressWarnings("serial")
public class CreatureSlots implements Observable<CreatureSlots>, Serializable {
	// clase que contiene al array de bichos
	// el juego tiene un array de 10 posiciones para ubicarlos en la pantalla y en la logica del juego
	// para la parte grafica se multiplica todo por 6, ya que es 600 de ancho la pantalla y para la parte
	// logica se usa para ver si uno esta al lado de un bicho o no, o para caminar correctamente sin pisarlos
	private ArrayList<Creature> creatureSlots;
	private transient Observer<CreatureSlots> observer;

	public CreatureSlots() {
		creatureSlots = new ArrayList<Creature>();
		for (int i = 0; i < 10; i++) {
			creatureSlots.add(i, null);
		}
	}

	public void set(int index, Creature creature) {
		creatureSlots.set(index, creature);
		notifyObservers(this);
	}

	public Creature get(int index) {
		return creatureSlots.get(index);
	}

	public ArrayList<Creature> getCreatureSlots() {
		return creatureSlots;
	}

	public void setCreatureSlots(ArrayList<Creature> creatureSlots) {
		this.creatureSlots = creatureSlots;
	}

	@Override
	public void removeObserver(Observer<CreatureSlots> obj) {
		observer = null;

	}

	@Override
	public void notifyObservers(CreatureSlots obj) {
		if (observer != null)
			observer.update(obj);

	}

	@Override
	public void registerObserver(Observer<CreatureSlots> obj) {
		observer = obj;
		notifyObservers(this);

	}

}
