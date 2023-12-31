package main;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	public BufferedImage spriteSheet;
	
	public SpriteSheet(BufferedImage spritesheet) {
		this.spriteSheet = spritesheet;
	}
	
	public BufferedImage grabSprite(int x, int y, int width, int height) {
		BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
		return sprite;
	}
}
