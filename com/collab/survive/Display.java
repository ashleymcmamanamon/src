package com.collab.survive;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.collab.survive.graphics.Screen;
import com.collab.survive.graphics.Render;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Survive Pre-Alpha 1.0";

	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private Render render;
	private boolean running = false;
	private int[] pixels;

	public Display() {
        screen = new Screen(WIDTH, HEIGHT);
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}

	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();

	}

	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void run() {

		while (running) {
			tick();
			render();

		}

	}

	private void tick() {

	}

	private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
        	createBufferStrategy(3);
        	return;
        	
        }
        
        screen.render();
        
        
       
        
        for (int i = 0; i<WIDTH*HEIGHT; i++) {
        	pixels[i] = screen.pixels[i];
        	
       
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH*20, HEIGHT*20, null);
        g.dispose();
        bs.show();
	}

	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		System.out.println("Running...");

		game.start();
	}

}
