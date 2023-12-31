package main;

public class Main {
	public static boolean playGame = false;
	public static boolean drawMenu = true;
	static GameState stateManager = new GameState();
	
	public static void init() {
		if (playGame) {
			stateManager.setState(1);
		} else if (drawMenu) {
			stateManager.setState(0);
		}
	}
	
	public static void main(String[] args) {
		init();
	}
}
