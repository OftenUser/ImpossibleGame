package main;

import java.util.ArrayList;

public class GameState  {
	private int currentState;
	private final int SCREEN_WIDTH = 1000;
	private final int SCREEN_HEIGHT = 800;
	private final String TITLE = "World's Hardest Game";
	
	ArrayList<Integer> states = new ArrayList<Integer>();
	
	public void setState(int newState) {
		currentState = newState;
		
		if (currentState == 0) {
			MainMenu menu = new MainMenu(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE);
			Thread t1 = new Thread(menu);
			t1.start();
		} else if (currentState == 1) {
			PlayGame game = new PlayGame(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE);
			Thread t2 = new Thread(game);
			t2.start();
		} 
	}
	
	public int getState() {
		return currentState;
	}
}
