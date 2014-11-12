package com.znylle.graphics.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.znylle.graphics.GameGraphics;
import com.znylle.main.Game;

public class InputProcessorStore implements KeyListener {
	private Game game;
	private GameGraphics gameGraphics;

	public InputProcessorStore(Game game, GameGraphics gameGraphics) {
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
		case Input.KEY_Q: {
			game.getPlayer().buyArmor(game.getArmors());
			break;
		}
		case Input.KEY_E: {
			game.getPlayer().buyWeapon(game.getWeapons());
			break;
		}
		case Input.KEY_ESCAPE:
		case Input.KEY_TAB: {
			game.getGameLogic().setStoreOpen(false);
			gameGraphics.getGameContainer().getInput().removeAllKeyListeners();
			gameGraphics.getGameContainer().getInput().addKeyListener(gameGraphics.getInputProcessor());
			break;
		}
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub

	}

}
