package main;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BufferedImageLoader {
	public BufferedImage loadImage(String pathRelativeToThis) throws IOException {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new FileInputStream(pathRelativeToThis));
		} catch (Exception e) {
			System.out.println("Couldn't Find Path: " + img);
			System.out.println(pathRelativeToThis);
		}
		return img;
	}
}
