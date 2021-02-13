package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import server.ControllServer;

public class Frame extends JFrame{

	
	private BufferStrategy strat;
	
	public static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width/2, HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height/2;
	
	
	public Frame() {
		super("Minecraft RemoteConroller");
		
		addKeyListener(new Keyboard());
		addMouseListener(new Keyboard());
		addMouseMotionListener(new Keyboard());
		addMouseWheelListener(new Keyboard());
	}
	
	public void makestrat() {
		createBufferStrategy(2);
		
		strat = getBufferStrategy();
	}
	
	public void repaint() {
		Graphics g = strat.getDrawGraphics();
		draw(g);
		g.dispose();
		strat.show();
	}
	
	public void draw(Graphics g) {
		
		
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			

			
			World.draw(g);
		
		
		
	}
	
	public void update(float tslf) {
		ConnectionListFrame.setList(ControllServer.connections);
		
		if(Display.DO_GAME_TICK) 
		World.update();
	}
	
}
