package com.znylle.observers;

import com.znylle.entities.Player;
import com.znylle.graphics.GameGraphics;

public class ObserverPlayer implements Observer<Player> {
	// idem observergamelogic pero para player
	private GameGraphics gameGraphics;

	public ObserverPlayer(GameGraphics gameGraphics) {
		this.gameGraphics = gameGraphics;
	}

	@Override
	public void update(Player player) {
		gameGraphics.getRenderPlayer().updatePlayer(player);
		gameGraphics.getRenderStore().updatePWeapon(player.getWeapon());
		gameGraphics.getRenderStore().updatePArmor(player.getArmor());
	}
}

