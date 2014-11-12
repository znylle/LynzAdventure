package com.znylle.observers;

import com.znylle.graphics.GameGraphics;
import com.znylle.logic.GameLogic;

public class ObserverGameLogic implements Observer<GameLogic> {
	// segun lo q pasa en gamelogic los renders trabajan, para no mezclar parte grafica con logica
	// usamos observer para que le avise a los renders si algo cambia
	private GameGraphics gameGraphics;

	public ObserverGameLogic(GameGraphics gameGraphics) {
		this.gameGraphics = gameGraphics;
	}

	@Override
	public void update(GameLogic gameLogic) {
		gameGraphics.getRenderPlayer().updatePlayerOrCreatureDead(gameLogic.isPlayerOrCreatureDead());
		gameGraphics.getRenderBackground().updateMap(gameLogic.getMap());
		gameGraphics.getRenderPlayer().updateNextCreatureDir(gameLogic.getNextCreatureDir());
		gameGraphics.getRenderCreatures().updateNextCreature(gameLogic.getNextCreature());
		gameGraphics.getRenderCreatures().updateShowCreatureTurn(gameLogic.isCreatureTurn(), gameLogic.isPlayerOrCreatureDead());
		gameGraphics.getRenderCreatures().updateNextCreaturePos(gameLogic.getNextCreaturePos());
		gameGraphics.getRenderCreatures().updateNextCreatureDir(gameLogic.getNextCreatureDir());
		gameGraphics.getRenderPlayer().updateGotDrop(gameLogic.isGotDrop(), gameLogic.getDrop());
		gameGraphics.getRenderStore().updateStoreOpen(gameLogic.isStoreOpen());
		gameGraphics.getRenderMenu().updateMenuOpen(gameLogic.isMenuOpen());
		gameGraphics.getRenderMenu().updateSaveExist(gameLogic.isSaveExist());
		gameGraphics.getRenderWonGame().updateWonGame(gameLogic.isWonGame());
		gameGraphics.getSwitchToInputProcessorWonGame().isWonGameSoSwitch(gameLogic.isWonGame());
	}
}
/*
 * No se justifica hacer un observer para cada atributo que cambie ya que no afecta la performance del juego.
 */
