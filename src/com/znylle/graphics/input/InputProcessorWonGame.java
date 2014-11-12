package com.znylle.graphics.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.znylle.graphics.GameGraphics;
import com.znylle.main.Game;

public class InputProcessorWonGame implements KeyListener {

	private Game game;
	private GameGraphics gameGraphics;

	public InputProcessorWonGame(Game game, GameGraphics gameGraphics) {
		this.game = game;
		this.gameGraphics = gameGraphics;
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int key, char arg1) {
		// TODO Auto-generated method stub
		switch (key) {
		case Input.KEY_ENTER: {
			game.getGameLogic().startOver();
			gameGraphics.getGameContainer().getInput().removeAllKeyListeners();
			gameGraphics.getGameContainer().getInput().addKeyListener(gameGraphics.getInputProcessor());
			break;
		}
		case Input.KEY_D: {
			game.getGameLogic().setWalkingR(false);
			break;
		}
		}
	}

	@Override
	public void keyReleased(int key, char arg1) {
		// TODO Auto-generated method stub
	}

}
