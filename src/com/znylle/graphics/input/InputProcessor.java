package com.znylle.graphics.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.znylle.graphics.GameGraphics;
import com.znylle.main.Game;

public class InputProcessor implements KeyListener {

	private Game game;
	private GameGraphics gameGraphics;

	public InputProcessor(Game game, GameGraphics gameGraphics) {
		this.game = game;
		this.gameGraphics = gameGraphics;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void keyPressed(int key, char arg1) {
		// TODO Auto-generated method stub
		switch (key) {
		case Input.KEY_A: {
			game.getGameLogic().setWalkingL(true);
			break;
		}
		case Input.KEY_D: {
			game.getGameLogic().setWalkingR(true);
			break;
		}
		case Input.KEY_SPACE: {
			game.getGameLogic().setPlayerAttacking(true);
			break;
		}
		case Input.KEY_TAB: {
			game.getGameLogic().setStoreOpen(true);
			game.getGameLogic().setWalkingL(false); //
			game.getGameLogic().setWalkingR(false); // Para que no siga caminando automaticamente
			game.getGameLogic().setWalked(true); // si abris el store (o el menu) mientras caminas
			game.getGameLogic().setPlayerAttacking(false);
			gameGraphics.getGameContainer().getInput().removeAllKeyListeners();
			gameGraphics.getGameContainer().getInput().addKeyListener(gameGraphics.getInputProcessorStore());
			break;
		}
		case Input.KEY_ESCAPE: {
			game.getGameLogic().setMenuOpen(true);
			game.getGameLogic().setWalkingL(false);
			game.getGameLogic().setWalkingR(false);
			game.getGameLogic().setWalked(true);
			gameGraphics.getGameContainer().getInput().removeAllKeyListeners();
			gameGraphics.getGameContainer().getInput().addKeyListener(gameGraphics.getInputProcessorMenu());
			break;
		}
		}
	}

	@Override
	public void keyReleased(int key, char arg1) {
		// TODO Auto-generated method stub
		switch (key) {
		case Input.KEY_A: {
			game.getGameLogic().setWalkingL(false);
			game.getGameLogic().setWalked(true);
			break;
		}
		case Input.KEY_D: {
			game.getGameLogic().setWalkingR(false);
			game.getGameLogic().setWalked(true);
			break;
		}
		case Input.KEY_SPACE: {
			game.getGameLogic().setPlayerAttacking(false);
			game.getGameLogic().setWalked(true);
			break;
		}
		}
	}

	@Override
	public void setInput(Input input) {
		// TODO Auto-generated method stub
	}

}
