package com.znylle.graphics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class RenderBackground {
	// renderiza la imagen del fondo de por donde el player camina
	private int map = 1;
	private GameImages gameImages;

	public RenderBackground(GameImages gameImages) throws SlickException {
		this.gameImages = gameImages;
	}

	public void renderBackGround(Graphics g) {
		gameImages.backgroundImages.get(map / 10).draw(0, 20);
		gameImages.separatorImage.draw(0, 260);
		gameImages.separator2Image.draw(370, 270);
		g.drawString("Map " + map, 0, 0);
		g.drawString("Menu (ESCAPE)", 480, 0);
	}

	public void updateMap(int map) {
		this.map = map;
	}
}
