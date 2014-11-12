package com.znylle.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.znylle.entities.Player;
import com.znylle.graphics.GameGraphics;
import com.znylle.graphics.GameImages;
import com.znylle.graphics.RenderBackground;
import com.znylle.graphics.RenderCreatures;
import com.znylle.graphics.RenderMenu;
import com.znylle.graphics.RenderPlayer;
import com.znylle.graphics.RenderStore;
import com.znylle.graphics.RenderWonGame;
import com.znylle.graphics.input.InputProcessor;
import com.znylle.graphics.input.InputProcessorMenu;
import com.znylle.graphics.input.InputProcessorStore;
import com.znylle.graphics.input.InputProcessorWonGame;
import com.znylle.graphics.input.SwitchToInputProcessorWonGame;
import com.znylle.logic.GameLogic;
import com.znylle.objects.CreatureSlots;
import com.znylle.observers.ObserverCreatureSlots;
import com.znylle.observers.ObserverGameLogic;
import com.znylle.observers.ObserverInputProcessorMenu;
import com.znylle.observers.ObserverPlayer;

public class Main extends BasicGame {

	private static Game game = Game.getInstance();
	private static GameGraphics gameGraphics = GameGraphics.getInstance();

	public Main(String title) {
		super(title);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Main("LynzAdventure"));
			app.setDisplayMode(gameGraphics.getResWidth(), gameGraphics.getResHeight(), false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		gameGraphics.getRenderBackground().renderBackGround(g);
		gameGraphics.getRenderCreatures().renderCreatures(g);
		gameGraphics.getRenderPlayer().renderPlayer(g);
		gameGraphics.getRenderStore().renderStore(g);
		gameGraphics.getRenderMenu().renderMenu(g);
		gameGraphics.getRenderWonGame().renderWonGame(g); // En el caso de llegar a determinado mapa, representa gráficamente que se ganó el juego.
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gameGraphics.setGameContainer(gc);
		gameGraphics.getGameContainer().setShowFPS(false);

		game.creaturesBuilder();
		game.bossesBuilder();
		game.armorBuilder();
		game.weaponsBuilder();

		gameGraphics.setGameImages(new GameImages(game.getCreatureNames(), game.getBossNames(), game.getWeaponNames(), game.getArmorNames(), game.getBackgroundNames(), Game.numberOfMapsDivided10));
		gameGraphics.setRenderPlayer(new RenderPlayer(gameGraphics.getGameImages()));
		gameGraphics.setRenderCreatures(new RenderCreatures(game.getCreatureNames(), game.getBossNames(), gameGraphics.getGameImages()));
		gameGraphics.setRenderBackground(new RenderBackground(gameGraphics.getGameImages()));
		gameGraphics.setRenderStore(new RenderStore(game.getWeapons(), game.getArmors(), game.getWeaponNames(), game.getArmorNames(), gameGraphics.getGameImages()));
		gameGraphics.setRenderMenu(new RenderMenu(gameGraphics.getGameImages()));
		gameGraphics.setRenderWonGame(new RenderWonGame(gameGraphics.getGameImages()));

		game.setObserverPlayer(new ObserverPlayer(gameGraphics));
		game.setObserverCreatureSlots(new ObserverCreatureSlots(gameGraphics));
		game.setObserverGameLogic(new ObserverGameLogic(gameGraphics));
		game.setObserverInputProcessorMenu(new ObserverInputProcessorMenu(gameGraphics));

		gameGraphics.setInputProcessor(new InputProcessor(game, gameGraphics));
		gameGraphics.setInputProcessorStore(new InputProcessorStore(game, gameGraphics));
		gameGraphics.setInputProcessorMenu(new InputProcessorMenu(game, gameGraphics));
		gameGraphics.setInputProcessorWonGame(new InputProcessorWonGame(game, gameGraphics));
		gameGraphics.setSwitchToInputProcessorWonGame(new SwitchToInputProcessorWonGame(game, gameGraphics));
		gameGraphics.getInputProcessorMenu().registerObserver(game.getObserverInputProcessorMenu());

		game.setGameLogic(new GameLogic(game));
		game.setPlayer(new Player("Lynz"));
		game.setCreatureSlots(new CreatureSlots());

		game.getPlayer().registerObserver(game.getObserverPlayer());
		game.getCreatureSlots().registerObserver(game.getObserverCreatureSlots());
		game.getGameLogic().registerObserver(game.getObserverGameLogic());

		game.getGameLogic().newCreatures(Game.RIGHT);
		game.getGameLogic().nextCreatureR();

		game.getPlayer().setArmor(game.getArmors().get(0));
		game.getPlayer().setWeapon(game.getWeapons().get(0));

		gameGraphics.getGameContainer().getInput().addKeyListener(gameGraphics.getInputProcessor());
	}

	@Override
	public void update(GameContainer arg0, int delta) throws SlickException {
		game.getGameLogic().autoRestoreHealth(); // Restaura 1 de vida cada x segundos
		game.getGameLogic().walkLogic(); // Pregunta si el input es para caminar. Si lo es, se fija si puede caminar hacia esa dirección y, si puede, entonces le indica al player que camine.
		game.getGameLogic().attackLogic(); // Pregunta si el input es para atacar. Si lo es, entonces le dice al player que ataque.
		game.getGameLogic().creatureAttack(); // Pregunta si el player está o no cerca de una criatura. Si lo está, entonces le indica a la criatura que lo ataque.

	}

}
