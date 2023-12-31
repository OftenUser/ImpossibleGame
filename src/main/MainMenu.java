package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import jaba.util.List;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainMenu extends JFrame implements Runnable {
	private boolean isRunning, mouseClicked, mouseOverStart, mouseOverSelect, drawMenu, drawLevels, backArrowHovering, doParticleEffect;
	private Image doubleBufferImage, startNoHover, selectLevelNoHover, startHover, selectLevelHover, titleImage, background,
	floatingPlayer, levelBox, levelBoxHover, lockedLevel, number1, number2, number3, number4, number5,
	number6, number7, number8, number9, number0, backArrow, backArrowHover, playerColor1, playerColor2, playerColor3, playerColor4;
	private MyRectangle player, startR, selectR, backR;
	SpriteSheet spritesheet;
	private Graphics doubleBufferGraphics;
	private int SW, SH, mx, my;
	public static LevelStatus[][] levels = new LevelStatus[7][5];
	public static int playerColor = 0;
	
	private List<String> dataFile;
	private Path path;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private Color particleColor;
	private File file = null;
	private File wkdir = null;
	private boolean schylersException = false;
	
	public MainMenu(int width, int height, String title) {
		this.setSize(width, height);
		this.setTitle(title);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		isRunning = true;
		
		this.addMouseListener(new ML());
		this.addMouseMotionListener(new ML());
		// this.addKeyListener(new AL());
		
		player = new MyRectangle(64, 64, 100, 180);
		player.dx = 5;
		player.dy = 3;
		
		SW = width; 
		SH = height;
		
		drawMenu = true;
		
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			file = new File(System.getProperty("user.home") + "/impossibleGame/userData.txt");
			wkdir = new File(System.getProperty("user.home") + "/impossibleGame");
		} else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			file = new File(System.getProperty("user.home") + File.separator + "impossibleGame" + File.separator + "userData.txt");
			wkdir = new File(System.getProperty("user.home") + File.separator + "impossibleGame");
		} else if (System.getProperty("os.name").toLowerCase().contains("mac os")) {
			file = new File(System.getProperty("user.home") + File.separator + "impossibleGame" + File.separator + "userData.txt");
			wkdir = new File(System.getProperty("user.home") + File.separator + "impossibleGame");
		}
		
		path = file.toPath();
		
		try {
			dataFile = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < dataFile.size() - 1; i++) {
			String[] truth = dataFile.get(i).split("=");
			
			int x = i % 7;
			int rx = i / 7;
			int y = i % 5;
			
			boolean lockValue = false;
			
			if (truth[1].equals("true"))
				lockValue = true;
	
			levels[x][y] = new LevelStatus(i + 1, lockValue, ((x % 7) * 115) + 130, ((rx % 5) * 125) + 87, 64, 64);
		}
		
		init();
	}
	
	public class ML extends MouseAdapter implements MouseMotionListener {
		public void mousePressed(MouseEvent e) {
			mouseClicked = true;
		}
		
		public void mouseReleased(MouseEvent e) {
			mouseClicked = false;
		}
		
		public void mouseMoved(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
		}
	}
	
	private void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = loader.loadImage(wkdir.toString() + File.separator + "resources" + File.separator + "spritesheet.png");
			background = loader.loadImage(wkdir.toString() + File.separator + "resources" + File.separator + "background.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ss = new SpriteSheet(spriteSheet);
		
		startNoHover = ss.grabSprite(3, 225, 175, 135);
		startHover = ss.grabSprite(203, 225, 175, 135);
		selectLevelNoHover = ss.grabSprite(3, 88, 188, 136);
		selectLevelHover = ss.grabSprite(203, 88, 188, 136);
		titleImage = ss.grabSprite(3, 0, 570, 80);
		levelBox = ss.grabSprite(550, 296, 64, 64);
		lockedLevel = ss.grabSprite(470, 296, 64, 64);
		levelBoxHover = ss.grabSprite(623, 296, 64, 64);
		number1 = ss.grabSprite(400, 256, 20, 32);
		number2 = ss.grabSprite(430, 256, 20, 32);
		number3 = ss.grabSprite(460, 256, 20, 32);
		number4 = ss.grabSprite(490, 256, 20, 32);
		number5 = ss.grabSprite(520, 256, 20, 32);
		number6 = ss.grabSprite(550, 256, 20, 32);
		number7 = ss.grabSprite(580, 256, 20, 32);
		number8 = ss.grabSprite(610, 256, 20, 32);
		number9 = ss.grabSprite(640, 256, 20, 32);
		number0 = ss.grabSprite(670, 256, 20, 32);
		backArrow = ss.grabSprite(410, 165, 125, 75);
		backArrowHover = ss.grabSprite(550, 165, 125, 75);

		startR = new MyRectangle(175, 135, 250, 350);
		selectR = new MyRectangle(185, 135, 500, 350);
		backR = new MyRectangle(125, 75, 440, 690);
		
		floatingPlayer = spritesheet.grabSprite(400, 296, 64, 64);
		playerColor1 = spritesheet.grabSprite(400, 360, 64, 64);
		playerColor2 = spritesheet.grabSprite(470, 360, 64, 64);
		playerColor3 = spritesheet.grabSprite(550, 360, 64, 64);
		playerColor4 = spritesheet.grabSprite(623, 360, 64, 64);
	}

	
	public void move() {
		player.x += player.dx;
		player.y += player.dy;
		
		if (mx > player.x && mx < player.x + player.width && my > player.y && my < player.y + player.height && mouseClicked) {
			playerColor++;
			mouseClicked = false;
			
			if (playerColor > 4)
				playerColor = 0;
			
			doParticleEffect = true;
			
			if (playerColor == 0)
				particleColor = Color.decode("0xff6600");
			else if (playerColor == 1) 
				particleColor = Color.decode("0xff0000");
			else if (playerColor == 2)
				particleColor = Color.decode("0x008033");
			else if (playerColor == 3)
				particleColor = Color.decode("0x00aad4");
			else if (playerColor == 4)
				particleColor = Color.decode("0xaa00d4");
			
			for (int i = 0; i < 100; i++) {
				int size = (int)((Math.random() * 20) + 10);
				int life = (int)( (Math.random() * 300) + 100);
				int particleDx = (int)((Math.random() * 3) - (Math.random() * 6));
				int particleDy = (int)(-(Math.random() * 3) - 1);
				Particle p = new Particle(size, life, player.x + player.width / 2, player.y + player.height / 2, particleColor, particleDx, particleDy);
				particles.add(p);
			}
		}
		
		if (doParticleEffect) {
			for (int i = 0; i < particles.size(); i++) {
				Particle current = particles.get(i);
				
				if(!(current.update()))
					particles.remove(i);
			}
				
			if (particles.size() <= 0)
				doParticleEffect = false;
		}
		
		if (player.x + player.width > SW) {
			player.x = SW - player.width;
			player.dx = -player.dx;
		} else if (player.x < 0) {
			player.x = 0;
			player.dx = -player.dx;
		}
		
		if (player.y > SH - player.height) {
			player.y = SH - player.height;
			player.dy = -player.dy;
		} else if (player.y < 0) {
			player.y = 0;
			player.dy *= -1;
		}
		
		if (mx > startR.x && mx < startR.x + startR.width && my > startR.y && my < startR.y + startR.height && !drawLevels) {
			mouseOverStart = true;
			
			if (mouseClicked) {
				PlayGame.levelMap = GetLevelMap1.getLevelMap1(1);
				PlayGame.levelObjects = GetLevelMap1.getLevelObjects1(1);
				PlayGame.currentLevel = 1;
				
				mouseClicked = false;
				
				Main.playGame = true;
				Main.drawMenu = false;
				Main.init();
				
				setVisible(false);
				isRunning = false;
				
				dispose();
			}
		} else 
			mouseOverStart = false;
		
		if (mx > selectR.x && mx < selectR.x + selectR.width && my > selectR.y && my < selectR.y + selectR.height && !drawLevels) {
			mouseOverSelect = true;
			
			if (mouseClicked) {
				drawLevels = true;
				mouseClicked = false;
				drawMenu = false;
			}
		} else 
			mouseOverSelect = false;
		
		if (drawLevels) {
			for (int i = 0; i < levels.length; i++) {
				for (int j = 0; j < levels[i].length; j++) {
					LevelStatus cl = levels[i][j];
					if (cl.unlocked) {
						if (mx > cl.x && mx < cl.x + cl.width && my > cl.y && my < cl.y + cl.height) {
							cl.mouseOver = true;
							if (mouseClicked) {
								if (levels[i][j].id < 20) {
									PlayGame.levelMap = GetLevelMap1.getLevelMap1(levels[i][j].id);
									PlayGame.levelObjects = GetLevelMap1.getLevelObjects1(levels[i][j].id);
								} else {
									PlayGame.levelMap = GetLevelMap2.getLevel2(levels[i][j].id);
									PlayGame.levelObjects = GetLevelMap2.getLevelObjects2(levels[i][j].id);
								}

								PlayGame.currentLevel = levels[i][j].id;
								
								mouseClicked = false;
								
								Main.playGame = true;
								Main.drawMenu = false;
								Main.init();
								
								setVisible(false);
								isRunning = false;
								
								dispose();
							}
						} else {
							cl.mouseOver = false;
						}
					}
				}
			}
			
			if (mx > backR.x && mx < backR.x + backR.width && my > backR.y && my < backR.y + backR.height) {
				backArrowHovering = true;
				
				if (mouseClicked)  {
					drawLevels = false;
					drawMenu = true;
				}
			} else {
				backArrowHovering = false;
			}
		}
	}
	
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	@SuppressWarnings("static-access")
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		
		if(!schylersException) {
			try {
				if(playerColor == 0)
					g.drawImage(floatingPlayer, player.x, player.y, player.width, player.height, null);
				else if(playerColor == 1)
					g.drawImage(playerColorOne, player.x, player.y, player.width, player.height, null);
				else if(playerColor == 2)
					g.drawImage(playerColorTwo, player.x, player.y, player.width, player.height, null);
				else if(playerColor == 3) 
					g.drawImage(playerColorThree, player.x, player.y, player.width, player.height, null);
				else if(playerColor == 4) 
					g.drawImage(playerColorFour, player.x, player.y, player.width, player.height, null);
			} catch(Exception e) {
				schylersException = true;
				System.out.println("THIS SHOULD NOT HAPPEN.");
				System.out.println("UNLESS IT'S ON SCHYLER'S COMPUTER");
			}
		} else {
			g.fillRect(player.x, player.y, player.width, player.height);
			if(MainMenu.playerColor == 0) 
				g.setColor(Color.red);
			else if(MainMenu.playerColor == 1)
				g.setColor(Color.green);
			else if(MainMenu.playerColor == 2)
				g.setColor(Color.cyan);
			else if(MainMenu.playerColor == 3)
				g.setColor(Color.MAGENTA);
			else if(MainMenu.playerColor == 4)
				g.setColor(Color.orange);
			g.fillRect(player.x + 2, player.y + 2, player.width - 4, player.height - 4);
		}
		
		
		if(drawMenu) {
			g.drawImage(titleImage, 220, 155, 570, 80, null);
			
			if(doParticleEffect) {
				for(int i=0; i < particles.size(); i++) {
					Particle current = particles.get(i);
					g.setColor(Color.black);
					g.fillRect(current.x, current.y, current.size, current.size);
					g.setColor(current.color);
					g.fillRect(current.x + 2, current.y + 2, current.size - 2, current.size + 2);
				}
			}
			
			if(mouseOverStart) 
				g.drawImage(startHover, 250, 350, 175, 135, null);
			else
				g.drawImage(startNoHover, 250, 350, 175, 135, null);
			
			if(mouseOverSelect)
				g.drawImage(selectLevelHover, 500, 350, 185, 135, null);
			else
				g.drawImage(selectLevelNoHover, 500, 350, 185, 135, null);
		} else if(drawLevels) {
			for(int i=0; i < levels.length; i++) {
				for(int j=0; j < levels[i].length; j++) {
					if(levels[i][j].unlocked) {
						if(levels[i][j].mouseOver)
							g.drawImage(levelBoxHover, levels[i][j].x, levels[i][j].y, 64, 64, null);
						else
							g.drawImage(levelBox, levels[i][j].x, levels[i][j].y, 64, 64, null);
					} 
					
					switch(levels[i][j].id) {
					case 1: g.drawImage(numberOne, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
					
					case 2: g.drawImage(numberTwo, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
					
					case 3: g.drawImage(numberThree, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
					
					case 4: g.drawImage(numberFour, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 5: g.drawImage(numberFive, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 6: g.drawImage(numberSix, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 7: g.drawImage(numberSeven, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 8: g.drawImage(numberEight, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 9: g.drawImage(numberNine, levels[i][j].x + 25, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 10: 
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberZero, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 11:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberOne, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 12:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberTwo, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 13: 
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberThree, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 14:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberFour, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 15:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberFive, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 16:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberSix, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 17:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberSeven, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 18:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberEight, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
					
					case 19:
						g.drawImage(numberOne, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberNine, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 20:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberZero, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 21:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberOne, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 22:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberTwo, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 23:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberThree, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 24:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberFour, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 25:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberFive, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 26:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberSix, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 27:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberSeven, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 28:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberEight, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 29:
						g.drawImage(numberTwo, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberNine, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 30:
						g.drawImage(numberThree, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberZero, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 31:
						g.drawImage(numberThree, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberOne, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 32:
						g.drawImage(numberThree, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberTwo, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 33:
						g.drawImage(numberThree, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberThree, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 34:
						g.drawImage(numberThree, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberFour, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
						
					case 35:
						g.drawImage(numberThree, levels[i][j].x + 12, levels[i][j].y + 15, 20, 32, null);
						g.drawImage(numberFive, levels[i][j].x + 32, levels[i][j].y + 15, 20, 32, null);
						break;
					}
					
					if(!levels[i][j].unlocked){
						g.drawImage(lockedLevel, levels[i][j].x, levels[i][j].y, 64, 64, null);
					}
					
					if(backArrowHovering)
						g.drawImage(backArrowHover, backR.x, backR.y, backR.width, backR.height, null);
					else 
						g.drawImage(backArrow, backR.x, backR.y, backR.width, backR.height, null);
				}
			}
		}
		
		
		repaint();
	}
	
	public void run() {
		try {
			while(isRunning) {
				move();
				
				Thread.sleep(30);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
