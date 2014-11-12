package com.znylle.graphics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class RenderMenu {
	//
	private GameImages gameImages;
	private boolean menuOpen = false;
	private boolean saveExist = false;
	private int lastKeyPressed = Input.KEY_0;

	public RenderMenu(GameImages gameImages) {
		this.gameImages = gameImages;
	}

	public void renderMenu(Graphics g) {
		if (menuOpen) {
			gameImages.storeAndMenuBackgroundImage.draw(170, 0);
			if (lastKeyPressed == Input.KEY_0) {
				g.drawString("MENU", 273, 8);
				g.drawString("-Instructions (I)", 200, 60);
				g.drawString("-Save Game (S)", 200, 90);
				g.drawString("-Load Game (L)", 200, 120);
			} else if (lastKeyPressed == Input.KEY_I) {
				g.drawString("INSTRUCTIONS:", 243, 8);
				g.drawString("-Walk Right (D) \n-Walk Left (A) \n-Attack (ESPACE) \n-Open Store (TAB) ", 200, 60);
				g.drawString("Back (ESCAPE)", 200, 230);
			} else if (lastKeyPressed == Input.KEY_L) {
				g.drawString("LOAD:", 273, 8);
				if (saveExist) {
					g.drawString("-The saved game has\nbeen loaded.", 200, 60);
				} else {
					g.drawString("-There is no previously\nsaved game.", 200, 60);
				}
				g.drawString("Back (ESCAPE)", 200, 230);
			} else if (lastKeyPressed == Input.KEY_S) {
				g.drawString("SAVE:", 273, 8);
				g.drawString("-You have saved \n the game.", 200, 60);
				g.drawString("Back (ESCAPE)", 200, 230);
			}
		}
	}

	public void updateLastKeyPressed(int lastKeyPressed) {
		this.lastKeyPressed = lastKeyPressed;
	}

	public void updateMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
	}

	public void updateSaveExist(boolean saveExist) {
		this.saveExist = saveExist;
	}
}
