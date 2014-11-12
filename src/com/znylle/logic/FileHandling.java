package com.znylle.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.znylle.entities.Player;
import com.znylle.main.Game;
import com.znylle.objects.CreatureSlots;

public class FileHandling {

	public static void saveFile(Game game, String string) {
		try {
			FileOutputStream saveFile = new FileOutputStream(string);
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(game.getPlayer());
			save.writeObject(game.getCreatureSlots());
			save.writeObject(game.getGameLogic());
			save.close();
			game.getGameLogic().setSaveExist(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadFile(Game game, String string) {
		try {
			FileInputStream saveFile = new FileInputStream(string);
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			Object obj = restore.readObject();
			Object obj2 = restore.readObject();
			Object obj3 = restore.readObject();
			game.setGameLogic((GameLogic) obj3);
			game.getGameLogic().setGame(game);
			game.setPlayer((Player) obj);
			game.setCreatureSlots((CreatureSlots) obj2);
			game.getGameLogic().registerObserver(game.getObserverGameLogic());
			game.getCreatureSlots().registerObserver(game.getObserverCreatureSlots());
			game.getPlayer().registerObserver(game.getObserverPlayer());
			/* Para los Observers */
			game.getObserverCreatureSlots().update(game.getCreatureSlots());
			game.getObserverGameLogic().update(game.getGameLogic());
			game.getObserverPlayer().update(game.getPlayer());
			game.getGameLogic().setSaveExist(true);
			/*----------------*/
			restore.close();
		} catch (ClassNotFoundException | IOException e) {
			game.getGameLogic().setSaveExist(false); // para q el render tire un "no existe save anterior"
			e.printStackTrace(); // o "juego cargado" dependiendo del valor de saveExist
		}
	}
}
