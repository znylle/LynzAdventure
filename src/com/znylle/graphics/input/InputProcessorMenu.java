package com.znylle.graphics.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.znylle.graphics.GameGraphics;
import com.znylle.logic.FileHandling;
import com.znylle.main.Game;
import com.znylle.observers.Observable;
import com.znylle.observers.Observer;

public class InputProcessorMenu implements Observable<InputProcessorMenu>, KeyListener {

	private Game game;
	private GameGraphics gameGraphics;
	private Observer<InputProcessorMenu> observer;
	private int lastKeyPressed = Input.KEY_0; // Sirve para la interfaz gráfica

	public InputProcessorMenu(Game game, GameGraphics gameGraphics) {
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
		case Input.KEY_S: {
			if (lastKeyPressed != Input.KEY_I) {
				setLastKeyPressed(Input.KEY_S);
				FileHandling.saveFile(game, "LynzAdventure-SaveFile");
			}
			break;
		}
		case Input.KEY_L: {
			if (lastKeyPressed != Input.KEY_I) {
				setLastKeyPressed(Input.KEY_L);
				FileHandling.loadFile(game, "LynzAdventure-SaveFile");
			}
			break;
		}
		case Input.KEY_I: {
			setLastKeyPressed(Input.KEY_I);
			break;
		}
		case Input.KEY_ESCAPE: {
			if (lastKeyPressed != Input.KEY_I) {
				game.getGameLogic().setMenuOpen(false);
				gameGraphics.getGameContainer().getInput().removeAllKeyListeners();
				gameGraphics.getGameContainer().getInput().addKeyListener(gameGraphics.getInputProcessor());
			}
			setLastKeyPressed(Input.KEY_0);
			break;
		}
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeObserver(Observer<InputProcessorMenu> obj) {
		// TODO Auto-generated method stub
		observer = null;

	}

	@Override
	public void notifyObservers(InputProcessorMenu obj) {
		// TODO Auto-generated method stub
		if (observer != null)
			observer.update(obj);
	}

	@Override
	public void registerObserver(Observer<InputProcessorMenu> obj) {
		// TODO Auto-generated method stub
		observer = obj;

	}

	public int getLastKeyPressed() {
		return lastKeyPressed;
	}

	public void setLastKeyPressed(int lastKeyPressed) {
		this.lastKeyPressed = lastKeyPressed;
		notifyObservers(this);
	}

}
