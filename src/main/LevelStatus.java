package main;

public class LevelStatus {
	public int id;
	public boolean unlocked;
	public int x;
	public int y;
	public int width;
	public int height;
	public boolean mouseOver = false;
	public int[][] levelMap = new int[5][5];

	public LevelStatus(int id, boolean status, int newX, int newY, int newWidth, int newHeight) {
		this.id = id;
		this.unlocked = status;
		this.x = newX;
		this.y = newY;
		this.width = newWidth;
		this.height = newHeight;
	}
}
