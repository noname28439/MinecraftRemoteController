package display;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.util.ArrayList;

public class Map {

	public static ArrayList<int[]> mapData = new ArrayList<>();
	
	
	public static void draw(Graphics g) {
		try {
			for(int i = 0; i<mapData.size();i++) {
				int[] ci = mapData.get(i);
				colorFromID(g, ci[2]);
				g.fillRect((ci[0]-(int)World.mapx)*World.zoom, (ci[1]-(int)World.mapy)*World.zoom, World.zoom, World.zoom);
			}
		}catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		}	
	}
	
	static String[] leaves = "190/192/194/148/150/152/188".split("/");
	static boolean isLeave(int id) {
		for(String cl : leaves)
			if(Integer.valueOf(cl)==id)
				return true;
		return false;
	}
	
	static void colorFromID(Graphics g, int id) {
		
		g.setColor(Color.WHITE);
		
		if(id==9)	//Grass
			g.setColor(Color.GREEN);
		else if(id==10)	//Dirt
			g.setColor(new Color(161, 81, 21));
		else if(id==34) //Wasser
			g.setColor(new Color(51, 102, 255));
		else if(id==14)	//Cobblestone
			g.setColor(new Color(128, 127, 122));
		else if(id==1)	//Cleanstone
			g.setColor(new Color(128, 127, 122));
		else if(id==66)	//Sand
			g.setColor(new Color(250, 234, 60));
		else if(id==246)	//Sandstone
			g.setColor(new Color(224, 196, 81));
		else if(id==3921)	//Snow
			g.setColor(new Color(255, 255, 255));
		else if(id==6)	//Andestit
			g.setColor(new Color(138, 138, 138));
		else if(id==4)	//Diorit
			g.setColor(new Color(207, 207, 207));
		else if(id==2)	//Granit
			g.setColor(new Color(138, 90, 84));
		else if(isLeave(id))	//Leave
			g.setColor(new Color(92, 140, 87));
		else if(id==-1)
			g.setColor(new Color(0, 0, 0));
		
	}
	
	
}
