package main;

import java.awt.Color;

public class Particle {
	public static int x, y, dx, dy, size, life, initialX, initialY, randomY;
	public static Color color;
	private int gravity = 3;
	
	@SuppressWarnings("static-access")
	public Particle(int size, int life, int x, int y, Color color, int dx, int dy) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.life = life;
		this.color = color;
		this.dx = dx;
		this.dy = dy;
		initialX = x;
		initialY = y;
		randomY = (int)(Math.random() * 20);
	}
	
	public boolean update() {
		x += dx;
		
		if (y > initialY - randomY)
			y += dy;
		else 
			y += gravity;
		
		life--;
		
		if (life <= 0)
			return true;
		
		return false;
	}
}
