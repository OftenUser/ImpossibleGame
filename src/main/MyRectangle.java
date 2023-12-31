package main;

import java.awt.Color;
import main.MyRectangle;

public class MyRectangle {
	public int width;
	public int height;
	public int x, y, dx, dy, id, maxX, maxY, minX, minY, i, j;
	public Color color;
	
	public MyRectangle(int setWidth, int setHeight, int setX, int setY) {
		width = setWidth;
		height = setHeight;
		x = setX;
		y = setY;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int centerX() {
		int centerX = this.x + (this.width / 2);
		return centerX;
	}
	
	public int centerY() {
		int centerY = this.y + (this.height / 2);
		return centerY;
	}
	
	public int halfWidth() {
		int halfWidth = width / 2;
		return halfWidth;
	}
	
	public int halfHeight() {
		int halfHeight = height / 2;
		return halfHeight;
	}
	
	public int blockRectangle(MyRectangle rectangle1, MyRectangle rectangle2) {
		// For Collision Sides: 1 = 'Top', 2 = 'Bottom', 3 = 'Left', 4 = 'Right'
		int collisionSide, overlapX, overlapY;
		
		int velocityX = rectangle1.centerX() - rectangle2.centerX();
		int velocityY = rectangle1.centerY() - rectangle2.centerY();
		
		int combinedHalfWidths = rectangle1.halfWidth() + rectangle2.halfWidth();
		int combinedHalfHeights = rectangle1.halfHeight() + rectangle2.halfHeight();
		
		if (Math.abs(velocityX) < combinedHalfWidths) {
			if (Math.abs(velocityY) < combinedHalfHeights) {
				overlapX = combinedHalfWidths - Math.abs(velocityX);
				overlapY = combinedHalfHeights - Math.abs(velocityY);
				
				if (overlapX >= overlapY) {
					if (velocityY > 0) {
						collisionSide = 1;
						rectangle1.y += overlapY;
					} else {
						collisionSide = 2;
						rectangle1.y -= overlapY;
					}
				} else {
					if (velocityX > 0) {
						collisionSide = 3;
						rectangle1.x += overlapX;
					} else {
						collisionSide = 4;
						rectangle1.x -= overlapX;
					}
				}
			} else {
				collisionSide = 0;
			}
		} else {
			collisionSide = 0;
		}
		
		return collisionSide;
	}

}
