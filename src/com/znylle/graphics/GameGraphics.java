package com.znylle.graphics;

import org.newdawn.slick.GameContainer;

import com.znylle.graphics.input.InputProcessor;
import com.znylle.graphics.input.InputProcessorMenu;
import com.znylle.graphics.input.InputProcessorStore;
import com.znylle.graphics.input.InputProcessorWonGame;
import com.znylle.graphics.input.SwitchToInputProcessorWonGame;

public class GameGraphics {
	// analoga a la clase Game, es la que contiene todas las cosas usadas en la parte grafica
	private static GameGraphics gameGraphics = null;

	private GameImages gameImages;
	private RenderPlayer renderPlayer;
	private RenderCreatures renderCreatures;
	private RenderBackground renderBackground;
	private RenderStore renderStore;
	private RenderMenu renderMenu;
	private RenderWonGame renderWonGame;

	private GameContainer gameContainer;

	private int ResHeight = 400;
	private int ResWidth = 600;

	private InputProcessor inputProcessor;
	private InputProcessorStore inputProcessorStore;
	private InputProcessorMenu inputProcessorMenu;
	private InputProcessorWonGame inputProcessorWonGame;

	private SwitchToInputProcessorWonGame switchToInputProcessorWonGame;

	public static GameGraphics getInstance() {
		if (gameGraphics == null) {
			gameGraphics = new GameGraphics();
		}
		return gameGraphics;
	}

	public GameImages getGameImages() {
		return gameImages;
	}

	public void setGameImages(GameImages gameImages) {
		this.gameImages = gameImages;
	}

	public RenderPlayer getRenderPlayer() {
		return renderPlayer;
	}

	public void setRenderPlayer(RenderPlayer renderPlayer) {
		this.renderPlayer = renderPlayer;
	}

	public RenderCreatures getRenderCreatures() {
		return renderCreatures;
	}

	public void setRenderCreatures(RenderCreatures renderCreatures) {
		this.renderCreatures = renderCreatures;
	}

	public RenderBackground getRenderBackground() {
		return renderBackground;
	}

	public void setRenderBackground(RenderBackground renderBackground) {
		this.renderBackground = renderBackground;
	}

	public RenderStore getRenderStore() {
		return renderStore;
	}

	public void setRenderStore(RenderStore renderStore) {
		this.renderStore = renderStore;
	}

	public RenderMenu getRenderMenu() {
		return renderMenu;
	}

	public void setRenderMenu(RenderMenu renderMenu) {
		this.renderMenu = renderMenu;
	}

	public RenderWonGame getRenderWonGame() {
		return renderWonGame;
	}

	public void setRenderWonGame(RenderWonGame renderWonGame) {
		this.renderWonGame = renderWonGame;
	}

	public int getResHeight() {
		return ResHeight;
	}

	public void setResHeight(int resHeight) {
		ResHeight = resHeight;
	}

	public int getResWidth() {
		return ResWidth;
	}

	public void setResWidth(int resWidth) {
		ResWidth = resWidth;
	}

	public InputProcessor getInputProcessor() {
		return inputProcessor;
	}

	public void setInputProcessor(InputProcessor inputProcessor) {
		this.inputProcessor = inputProcessor;
	}

	public GameContainer getGameContainer() {
		return gameContainer;
	}

	public void setGameContainer(GameContainer gameContainer) {
		this.gameContainer = gameContainer;
	}

	public InputProcessorStore getInputProcessorStore() {
		return inputProcessorStore;
	}

	public void setInputProcessorStore(InputProcessorStore inputProcessorStore) {
		this.inputProcessorStore = inputProcessorStore;
	}

	public InputProcessorMenu getInputProcessorMenu() {
		return inputProcessorMenu;
	}

	public void setInputProcessorMenu(InputProcessorMenu inputProcessorMenu) {
		this.inputProcessorMenu = inputProcessorMenu;
	}

	public InputProcessorWonGame getInputProcessorWonGame() {
		return inputProcessorWonGame;
	}

	public void setInputProcessorWonGame(InputProcessorWonGame inputProcessorWonGame) {
		this.inputProcessorWonGame = inputProcessorWonGame;
	}

	public SwitchToInputProcessorWonGame getSwitchToInputProcessorWonGame() {
		return switchToInputProcessorWonGame;
	}

	public void setSwitchToInputProcessorWonGame(SwitchToInputProcessorWonGame switchToInputProcessorWonGame) {
		this.switchToInputProcessorWonGame = switchToInputProcessorWonGame;
	}

}
