package com.znylle.graphics;

import org.newdawn.slick.Graphics;

public class RenderWonGame {
	private GameImages gameImages;
	private boolean wonGame = false;

	public RenderWonGame(GameImages gameImages) {
		this.gameImages = gameImages;
	}

	public void renderWonGame(Graphics g) {
		if (wonGame) {
			gameImages.storeAndMenuBackgroundImage.draw(170, 0);
			g.drawString("CONGRATULATIONS, \n   YOU WON!!", 230, 8);
			g.drawString("-Start New Game (ENTER)", 180, 70);
		}
	}

	public void updateWonGame(boolean wonGame) {
		this.wonGame = wonGame;
	}

}
