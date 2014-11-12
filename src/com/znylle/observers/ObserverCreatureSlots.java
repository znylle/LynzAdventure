package com.znylle.observers;

import com.znylle.graphics.GameGraphics;
import com.znylle.objects.CreatureSlots;

public class ObserverCreatureSlots implements Observer<CreatureSlots> {
	// idem observergamelogic pero para creatureSlots
	private GameGraphics gameGraphics;

	public ObserverCreatureSlots(GameGraphics gameGraphics) {
		this.gameGraphics = gameGraphics;
	}

	@Override
	public void update(CreatureSlots creatureSlots) {
		gameGraphics.getRenderCreatures().updateCreatures(creatureSlots);
	}

}
