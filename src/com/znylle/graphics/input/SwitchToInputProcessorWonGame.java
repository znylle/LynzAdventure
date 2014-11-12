package com.znylle.graphics.input;

import com.znylle.graphics.GameGraphics;
import com.znylle.main.Game;

public class SwitchToInputProcessorWonGame {

	private GameGraphics gameGraphics;
	private Game game;

	public SwitchToInputProcessorWonGame(Game game, GameGraphics gameGraphics) {
		this.gameGraphics = gameGraphics;
		this.game = game;
	}

	public void isWonGameSoSwitch(boolean wonGame) {
		if (wonGame) {
			gameGraphics.getGameContainer().getInput().removeAllKeyListeners();
			gameGraphics.getGameContainer().getInput().addKeyListener(gameGraphics.getInputProcessorWonGame());
			game.getGameLogic().setWalkingR(false);
		}
	}
}
