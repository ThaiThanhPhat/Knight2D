package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable{
    
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Thread gameThread;
    private final int FPS = 60;
    private final int UPS = 100;

    private Player player;

    public Game(){

        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }
    private void initClasses() {

        player = new Player(200, 200);
    }
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    
    }
    public void update() {
		player.update();
	}

    public void render(Graphics g) {
		player.render(g);
	}
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
		long previousTime = System.nanoTime();

		int frames = 0;
        int updates = 0;
		long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
		double deltaF = 0;

		while (true) {

			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
                deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
                updates = 0;
			}
        }
    }

    public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}
    
}
