package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

import server.ControllServer;
import server.ServerConnection;


public class World {

	public static int mapx = 0, mapy = 0;
	
	public static String watchServer = "localhost/127.0.0.1:25565";
	
	
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
		
	}
	
	
	public static void draw(Graphics g) {
		Map.draw(g);
		
		
		for(ServerConnection csc : ControllServer.connections) {
			if(csc.ip.equalsIgnoreCase(watchServer))
				drawPlayer(g, csc);
		}
		
		g.setColor(Color.BLACK);
		g.drawString("Server: "+watchServer, 100, 100);
		
	}
	
	static void drawPlayer(Graphics g, ServerConnection client) {
		if(client.location[0]==0&&client.location[1]==0&&client.location[2]==0)
			return;
		g.setColor(Color.RED);
		g.fillRect(client.location[0]-mapx, client.location[2]-mapy, 2, 2);
		g.drawString(client.name+" ["+client.location[1]+"]", client.location[0]-mapx, client.location[2]-mapy);
	}
	
	
}
