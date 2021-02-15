package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Random;

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
	
	static Color backGround = new Color(127, 127, 127);
	
	public void draw(Graphics g) {
		
		backGround = new Color(backGround.getRed()+new Random().nextInt(3)-1, backGround.getGreen()+new Random().nextInt(3)-1, backGround.getBlue()+new Random().nextInt(3)-1);
		
		g.setColor(backGround);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		World.draw(g);
		
	}
	
	public void update(float tslf) {
		ConnectionListFrame.setList(ControllServer.connections);
		
		World.update();
	}
	
}
