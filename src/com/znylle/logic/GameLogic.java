package com.znylle.logic;

import java.io.Serializable;
import java.util.Random;

import com.znylle.entities.Creature;
import com.znylle.entities.Player;
import com.znylle.main.Game;
import com.znylle.objects.Item;
import com.znylle.observers.Observable;
import com.znylle.observers.Observer;

@SuppressWarnings("serial")
public class GameLogic implements Observable<GameLogic>, Serializable {
	// logica principal del juego
	/* Variable de acceso a todo el juego */
	private transient Game game;
	/* Tiempos de logica */
	private long timeCreaAttack = 600;
	private long timeAutoStatRestore = 1000;
	/* Ayuda para tiempos de logica */
	private long time;
	private long time2;
	/* Booleans de la logica */
	private boolean creatureTurn = false;
	private boolean playerOrCreatureDead = false;
	private boolean wonGame = false;
	private boolean gotDrop = false;
	private boolean walkingR = false;
	private boolean walkingL = false;
	private boolean walked = false;
	private boolean playerAttacking = false;
	private boolean storeOpen = false;
	private boolean menuOpen = false;
	private boolean saveExist = false;
	/* Proxima criatura y datos sobre donde se encuentra */
	private Creature nextCreature = null;
	private int nextCreaturePos;
	private int nextCreatureDir = 1;
	/* Mapa donde se encuentra el jugador */
	private int map;
	/* Posible drop de alguna criatura y las chances de este */
	private Item drop;
	private int dropChance = 500;
	private int rareDropChance = 100;
	/* Observador de la logica */
	private transient Observer<GameLogic> observer;

	public GameLogic(Game game) {
		this.game = game;
		this.time = this.time2 = System.currentTimeMillis();
		this.map = 1;
	}

	public void walkLogic() {
		int condition = 0;
		this.walked = game.getPlayer().stand(this.walked);
		if (isWalkingR() && !isNextToCreatureR()) {
			if (willBeNextToCreatureR()) {
				condition = game.getCreatureSlots().getCreatureSlots().indexOf(nextCreature) * 10 - game.getPlayer().getRealPosition() - 10;
			} // esta condicion me dice cuanto caminar si hay un bicho adelante y la velocidad es mayor q 1, porque sino el player pisa al bicho
			game.getPlayer().walk(Game.RIGHT, condition);
			isMapChange();
			isNextToCreatureR();
		} else if (isWalkingL() && !isNextToCreatureL()) {
			if (willBeNextToCreatureL()) {
				condition = game.getPlayer().getRealPosition() - (game.getCreatureSlots().getCreatureSlots().indexOf(nextCreature) * 10) - 10;
			}
			game.getPlayer().walk(Game.LEFT, condition);
			isMapChange();
			isNextToCreatureL();
		}
	}

	public void attackLogic() {
		if (isPlayerAttacking()) {
			if (isNextToCreatureR()) {
				Creature creature = game.getCreatureSlots().get(game.getPlayer().getRealPosition() / 10 + 1);
				game.getPlayer().plaAttack(creature);
				isKill(creature, Game.RIGHT);

			} else if (isNextToCreatureL()) {
				Creature creature = game.getCreatureSlots().get(game.getPlayer().getRealPosition() / 10 - 1);
				game.getPlayer().plaAttack(creature);
				isKill(creature, Game.LEFT);
			}
		}
	}

	public void autoRestoreHealth() {
		if (System.currentTimeMillis() - time2 > timeAutoStatRestore) {
			int aux = game.getPlayer().getHealth();
			int aux2 = game.getPlayer().getLevel() / 10;
			if (aux < game.getPlayer().getMaxHealth()) {
				if (aux + aux2 > game.getPlayer().getMaxHealth()) { // para que no se pase del maxhealth al autorestorear
					game.getPlayer().setHealth(game.getPlayer().getMaxHealth());
				} else {
					game.getPlayer().setHealth(aux + Math.max(1, aux2));
				}
			}
			time2 = System.currentTimeMillis();
		}

	}

	public void startOver() { // para una nueva partida
		game.setPlayer(new Player("Lynz"));
		game.getPlayer().registerObserver(game.getObserverPlayer());
		game.getPlayer().setArmor(game.getArmors().get(0));
		game.getPlayer().setWeapon(game.getWeapons().get(0));
		setMap(1);
		newCreatures(Game.RIGHT);
		nextCreatureR();
		setWonGame(false);
	}

	public void isMapChange() {
		if (map % Game.BOSS == 1 && game.getPlayer().getBossKills() < (map - 1) / Game.BOSS) {
			game.getPlayer().setBossKills((map - 1) / Game.BOSS);
		}
		if ((map + 1) % Game.BOSS != 0) { // si el prox mapa no es un boss
			if (game.getPlayer().getRealPosition() >= 95) {
				game.getPlayer().setRealPosition(0);
				setMap(map + 1);
				newCreatures(Game.RIGHT);
				nextCreatureR();
			} else if (game.getPlayer().getRealPosition() < 0 && map != 1) {
				game.getPlayer().setRealPosition(94);
				setMap(map - 1);
				newCreatures(Game.LEFT);
				nextCreatureL();
			} else if (game.getPlayer().getRealPosition() < 0 && map == 1) { // para q no se vaya el player de la pantalla
				game.getPlayer().setRealPosition(0); // solo en el primer mapa
				nextCreatureR();
			}
		} else if (game.getPlayer().getRealPosition() < 0 && map != 1) { // para ir hacia la izq en la sala del boss
			game.getPlayer().setRealPosition(94);
			setMap(map - 1);
			newCreatures(Game.LEFT);
			nextCreatureL();
		} else if (game.getPlayer().getRealPosition() >= 95 && game.getPlayer().getBossKills() < (map + 1) / Game.BOSS) {
			// si todavia no se mato a este boss entonces q lo spawnee
			game.getPlayer().setRealPosition(0);
			setMap(map + 1);
			newBoss();
		} else if (game.getPlayer().getRealPosition() >= 95) { // para avanzar dsp de matar al boss
			game.getPlayer().setRealPosition(0);
			if ((map + 1) == (Game.numberOfMapsDivided10 * 10)) {
				setWonGame(true); // Indica que se gano el juego
				setMap(map + 1);
			} else {
				setMap(map + 1);
				newCreatures(Game.RIGHT);
				nextCreatureR();
			}
		}
	}

	public void newBoss() {
		for (int j = 0; j < 10; j++) {
			game.getCreatureSlots().set(j, null);
		}
		if (game.getBosses().get(game.getPlayer().getBossKills()).getHealth() > 0) {// si es menor q 0 significa q ya murio
			game.getCreatureSlots().set(8, game.getBosses().get(game.getPlayer().getBossKills()));
			game.getCreatureSlots().get(8).setHealth(game.getCreatureSlots().get(8).getMaxHealth());
		} else {
			newCreatures(Game.RIGHT);
		}
		nextCreatureR();

	}

	public void newCreatures(int dir) {
		Random random = new Random();
		int size = random.nextInt(5);
		int i;

		for (int j = 0; j < 10; j++) {
			game.getCreatureSlots().set(j, null);
		}
		for (i = 1 + dir; size < 5 && i <= 8 + dir; i++) {
			if (random.nextBoolean()) {
				game.getCreatureSlots().set(i, new Creature(game.getCreatures().get(random.nextInt(game.getCreatures().size()))).modify(map));
				// si no instanciabamos cada vez q cambiamos de mapa, los bichos aparecian con 0 de vida o con stats multiplicados muchas veces
				size += 1;
			}
		}

	}

	public void isKill(Creature creature, int dir) {
		if (creature.getHealth() <= 0) {
			setPlayerOrCreatureDead(true);
			setCreatureTurn(false); // los turns son para el render para dibujar atacando o no
			Random random = new Random();
			game.getPlayer().killCreature(creature);
			if (random.nextInt(dropChance) == dropChance - 1) { // todo este if es por si algun bicho dropea algo
				if (random.nextInt(rareDropChance) == rareDropChance - 1 && game.getWeapons().indexOf(game.getPlayer().getWeapon()) != game.getWeapons().size() - 1) {
					setDrop(game.getWeapons().get(game.getWeapons().size() - 1));
					setGotDrop(true);
					game.getPlayer().getDropWeapon(game.getWeapons().get(game.getWeapons().size() - 1));
					setGotDrop(false);
				} else if (random.nextBoolean()) {
					if (game.getArmors().indexOf(game.getPlayer().getArmor()) != game.getArmors().size() - 1) {
						setDrop(game.getArmors().get(game.getArmors().indexOf(game.getPlayer().getArmor()) + 1));
						setGotDrop(true);
						game.getPlayer().getDropArmor(game.getArmors().get(game.getArmors().indexOf(game.getPlayer().getArmor()) + 1));
						setGotDrop(false);
					} else if (game.getWeapons().indexOf(game.getPlayer().getWeapon()) != game.getWeapons().size() - 2 && game.getWeapons().indexOf(game.getPlayer().getWeapon()) != game.getWeapons().size() - 1) {
						setDrop(game.getWeapons().get(game.getWeapons().indexOf(game.getPlayer().getWeapon()) + 1));
						setGotDrop(true);
						game.getPlayer().getDropWeapon(game.getWeapons().get(game.getWeapons().indexOf(game.getPlayer().getWeapon()) + 1));
						setGotDrop(false);
					}
				} else if (game.getWeapons().indexOf(game.getPlayer().getWeapon()) != game.getWeapons().size() - 2 && game.getWeapons().indexOf(game.getPlayer().getWeapon()) != game.getWeapons().size() - 1) {
					setDrop(game.getWeapons().get(game.getWeapons().indexOf(game.getPlayer().getWeapon()) + 1));
					setGotDrop(true);
					game.getPlayer().getDropWeapon(game.getWeapons().get(game.getWeapons().indexOf(game.getPlayer().getWeapon()) + 1));
					setGotDrop(false);
				} else if (game.getArmors().indexOf(game.getPlayer().getArmor()) != game.getArmors().size() - 1) {
					setDrop(game.getArmors().get(game.getArmors().indexOf(game.getPlayer().getArmor()) + 1));
					setGotDrop(true);
					game.getPlayer().getDropArmor(game.getArmors().get(game.getArmors().indexOf(game.getPlayer().getArmor()) + 1));
					setGotDrop(false);
				}
			}
			game.getCreatureSlots().set(game.getPlayer().getRealPosition() / 10 + dir, null);
			setPlayerOrCreatureDead(false);
			if (dir == Game.RIGHT)
				nextCreatureR();
			else if (dir == Game.LEFT)
				nextCreatureL();

		}
	}

	public void creatureAttack() {
		if (System.currentTimeMillis() - time > timeCreaAttack) {
			if (isNextToCreatureR()) {
				game.getPlayer().setPlayerWalkState(2); // para q quede el sprite de cuando esta parado para la derecha, no caminando
				setCreatureTurn(true);
				nextCreature.creAttack(game.getPlayer());
				setCreatureTurn(false);
			} else if (isNextToCreatureL()) {
				game.getPlayer().setPlayerWalkState(-2); // idem para la izquierda
				setCreatureTurn(true);
				nextCreature.creAttack(game.getPlayer());
				setCreatureTurn(false);
			}
			time = System.currentTimeMillis();
			if (game.getPlayer().isKill()) {
				setPlayerOrCreatureDead(true);
				game.getPlayer().revive();
				setPlayerOrCreatureDead(false);
				if (getMap() >= 10) { // para no renacer en el principio si ya mataste algun jefe
					setMap(Math.max((game.getPlayer().getBossKills() * 10), 1));
				} else {
					setMap(1);
				}
				newCreatures(Game.RIGHT);
				nextCreatureR();
			}
		}
	}

	public boolean willBeNextToCreatureR() { // para calcular la condition al caminar
		if ((game.getPlayer().getRealPosition() + game.getPlayer().getSpeed()) / 10 < 9 && game.getCreatureSlots().get((game.getPlayer().getRealPosition() + game.getPlayer().getSpeed()) / 10 + 1) != null) {
			return true;
		}
		return false;
	}

	public boolean willBeNextToCreatureL() {
		if ((double) (game.getPlayer().getRealPosition() - game.getPlayer().getSpeed()) / 10.0 > 0.0 && game.getCreatureSlots().get((int) Math.ceil((double) (game.getPlayer().getRealPosition() - game.getPlayer().getSpeed()) / 10.0) - 1) != null) {
			return true;
		}
		return false;
	}

	public boolean isNextToCreatureR() {
		if (game.getPlayer().getRealPosition() / 10 < 9 && game.getCreatureSlots().get(game.getPlayer().getRealPosition() / 10 + 1) != null) {
			return true;
		}
		return false;
	}

	public boolean isNextToCreatureL() {
		if (game.getPlayer().getRealPosition() / 10 > 0 && game.getCreatureSlots().get((int) Math.ceil((double) game.getPlayer().getRealPosition() / 10.0) - 1) != null) {
			return true;
		}
		return false;
	}

	public int getMap() {
		return map;
	}

	public void nextCreatureR() { // me dice la pos del siguiente bicho a la derecha
		int i;
		game.getPlayer().setMyAttackTurn(false);
		for (i = 2; i < 10; i++) {
			if (game.getCreatureSlots().getCreatureSlots().get(i) != null) {
				setNextCreature(game.getCreatureSlots().getCreatureSlots().get(i));
				setNextCreaturePos(i);
				setNextCreatureDir(1);
				break;
			}
		}
		if (i == 10)
			setNextCreature(null);
	}

	public void nextCreatureL() { // idem pero izquierda
		int i;
		game.getPlayer().setMyAttackTurn(false);
		for (i = 8; i >= 0; i--) {
			if (game.getCreatureSlots().getCreatureSlots().get(i) != null) {
				setNextCreature(game.getCreatureSlots().getCreatureSlots().get(i));
				setNextCreaturePos(i);
				setNextCreatureDir(-1);
				break;
			}
		}
		if (i < 0) {
			setNextCreature(null);
		}
	}

	public Creature getNextCreature() {
		return nextCreature;
	}

	public void setNextCreature(Creature nextCreature) {
		this.nextCreature = nextCreature;
		notifyObservers(this);
	}

	@Override
	public void removeObserver(Observer<GameLogic> obj) {
		observer = null;

	}

	@Override
	public void notifyObservers(GameLogic obj) {
		if (observer != null)
			observer.update(obj);

	}

	@Override
	public void registerObserver(Observer<GameLogic> obj) {
		observer = obj;
	}

	public boolean isCreatureTurn() {
		return creatureTurn;
	}

	public void setCreatureTurn(boolean creatureTurn) {
		this.creatureTurn = creatureTurn;
		notifyObservers(this);
	}

	public int getNextCreaturePos() {
		return nextCreaturePos;
	}

	public void setNextCreaturePos(int nextCreaturePos) {
		this.nextCreaturePos = nextCreaturePos;
		notifyObservers(this);
	}

	public int getNextCreatureDir() {
		return nextCreatureDir;
	}

	public void setNextCreatureDir(int nextCreatureDir) {
		this.nextCreatureDir = nextCreatureDir;
		notifyObservers(this);
	}

	public boolean isPlayerOrCreatureDead() {
		return playerOrCreatureDead;
	}

	public void setPlayerOrCreatureDead(boolean playerOrCreatureDead) {
		this.playerOrCreatureDead = playerOrCreatureDead;
		notifyObservers(this);
	}

	public void setMap(int map) {
		this.map = map;
		notifyObservers(this);
	}

	public boolean isWonGame() {
		return wonGame;
	}

	public void setWonGame(boolean wonGame) {
		this.wonGame = wonGame;
		notifyObservers(this);
	}

	public boolean isGotDrop() {
		return gotDrop;
	}

	public void setGotDrop(boolean gotDrop) {
		this.gotDrop = gotDrop;
		notifyObservers(this);
	}

	public Item getDrop() {
		return drop;
	}

	public void setDrop(Item drop) {
		this.drop = drop;
	}

	public boolean isWalkingR() {
		return walkingR;
	}

	public void setWalkingR(boolean walkingR) {
		this.walkingR = walkingR;
	}

	public boolean isWalkingL() {
		return walkingL;
	}

	public void setWalkingL(boolean walkingL) {
		this.walkingL = walkingL;
	}

	public boolean isStoreOpen() {
		return storeOpen;
	}

	public void setStoreOpen(boolean storeOpen) {
		this.storeOpen = storeOpen;
		notifyObservers(this);
	}

	public boolean isPlayerAttacking() {
		return playerAttacking;
	}

	public void setPlayerAttacking(boolean playerAttacking) {
		this.playerAttacking = playerAttacking;
	}

	public boolean isMenuOpen() {
		return menuOpen;
	}

	public void setMenuOpen(boolean menuOpen) {
		this.menuOpen = menuOpen;
		notifyObservers(this);
	}

	public boolean isWalked() {
		return walked;
	}

	public void setWalked(boolean walked) {
		this.walked = walked;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isSaveExist() {
		return saveExist;
	}

	public void setSaveExist(boolean saveExist) {
		this.saveExist = saveExist;
		notifyObservers(this);
	}

}