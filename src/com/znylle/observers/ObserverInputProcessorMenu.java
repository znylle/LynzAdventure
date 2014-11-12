package com.znylle.observers;

import com.znylle.graphics.GameGraphics;
import com.znylle.graphics.input.InputProcessorMenu;

public class ObserverInputProcessorMenu implements Observer<InputProcessorMenu> {
	// idem observergamelogic pero para input del menu
	private GameGraphics gameGraphics;

	public ObserverInputProcessorMenu(GameGraphics gameGraphics) {
		this.gameGraphics = gameGraphics;
	}

	@Override
	public void update(InputProcessorMenu inputProcessorMenu) {
		gameGraphics.getRenderMenu().updateLastKeyPressed(inputProcessorMenu.getLastKeyPressed());
	}
}
