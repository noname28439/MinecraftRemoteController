package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

import server.ControllServer;
import server.ServerConnection;


public class World {

	public static float mapx = 0, mapy = 0;
	public static int zoom = 1;
	
	public static String watchServer = "raspberrypi.fritz.box/192.168.178.45:25567";
	/*
	 * localhost/127.0.0.1:25565
	 * raspberrypi.fritz.box/192.168.178.45:25567
	 */
	
	
	public static void load() {
		
		
	}
	
	
	
	
	
	public static void update() {
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_DOWN))
			mapy++;
		if(Keyboard.isKeyPressed(KeyEvent.VK_UP))
			mapy--;
		if(Keyboard.isKeyPressed(KeyEvent.VK_LEFT))
			mapx--;
		if(Keyboard.isKeyPressed(KeyEvent.VK_RIGHT))
			mapx++;
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_CONTROL)) {
			if(Keyboard.isKeyPressed(KeyEvent.VK_PLUS)) {
				zoom++;
				Keyboard.keys[KeyEvent.VK_PLUS] = false;
			}
			if(Keyboard.isKeyPressed(KeyEvent.VK_MINUS)) {
				zoom--;
				Keyboard.keys[KeyEvent.VK_MINUS] = false;
			}
		}
		
		
	}
	
	
	public static void draw(Graphics g) {
		Map.draw(g);
		
		
		for(ServerConnection csc : ControllServer.connections) {
			if(csc.ip.equalsIgnoreCase(watchServer))
				drawPlayer(g, csc);
		}
		
		g.setColor(Color.BLACK);
		Display.frame.setTitle("Server: "+watchServer);
		g.drawString("["+mapx+"|"+mapy+"]", 100, 100);
		
	}
	
	static void drawPlayer(Graphics g, ServerConnection client) {
		if(client.location[0]==0&&client.location[1]==0&&client.location[2]==0)
			return;
		g.setColor(Color.RED);
		g.fillRect((client.location[0]-(int)mapx)*zoom, (client.location[2]-(int)mapy)*zoom, zoom, zoom);
		g.drawString(client.name+" ["+client.location[1]+"]", (client.location[0]-(int)mapx)*zoom, (client.location[2]-(int)mapy)*zoom);
	}
	
	
}
