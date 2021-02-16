package display;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorChooserUI;

public class Map implements ChangeListener{

	
	public static ArrayList<int[]> mapData = new ArrayList<>();
	public static HashMap<Integer, Color> blockColors = new HashMap<>();
	
	public static void loadColorPresets() {
		
		
		blockColors.put(9, Color.GREEN);
		blockColors.put(10, new Color(161, 81, 21));
		blockColors.put(34, new Color(51, 102, 255));
		blockColors.put(14, new Color(128, 127, 122));
		blockColors.put(1,new Color(128, 127, 122));
		blockColors.put(66, new Color(250, 234, 60));
		blockColors.put(246, new Color(224, 196, 81));
		blockColors.put(3921, new Color(255, 255, 255));
		blockColors.put(6, new Color(138, 138, 138));
		blockColors.put(4, new Color(207, 207, 207));
		blockColors.put(2, new Color(138, 90, 84));
		//blockColors.put(9, Color.GREEN);
		
//		else if(isLeave(id))	//Leave
//			g.setColor(new Color(92, 140, 87));
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof ColorSelectionModel) {
			ColorSelectionModel csm = (ColorSelectionModel)e.getSource();
			
			 selectorState = csm.getSelectedColor();
			 
		}  
	}
	
	static Color selectorState = new Color(0, 0 , 0);
	
	static void setColor(int id) {
		Color result = new Color(0, 0, 0);
		if(blockColors.containsKey(id))
			result = blockColors.get(id);
		
		JColorChooser chooser = new JColorChooser(result);
		chooser.getSelectionModel().addChangeListener(new Map());
		
		JFrame selectorFrame = new JFrame("Select Color for "+id);
		selectorFrame.add(chooser);
		
		selectorFrame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent windowEvent) {
		    	blockColors.put(id, selectorState);
		    }
		});
		
		
		selectorFrame.setLocationRelativeTo(null);
		selectorFrame.setResizable(true);
		selectorFrame.setVisible(true);
		selectorFrame.pack();
		selectorFrame.setAlwaysOnTop(true);
		
		
	}
	
	public static void draw(Graphics g) {
		try {
			for(int i = 0; i<mapData.size();i++) {
				int[] ci = mapData.get(i);
				if(ci.length>=3)
				colorFromID(g, ci[2]);
				g.fillRect((ci[0]-(int)World.mapx)*World.zoom, (ci[1]-(int)World.mapy)*World.zoom, World.zoom, World.zoom);
				
				int selbid = 0;
				if(Collision.rectToRect((ci[0]-(int)World.mapx)*World.zoom, (ci[1]-(int)World.mapy)*World.zoom, World.zoom, World.zoom, Keyboard.getMousex(), Keyboard.getMousey(), 1, 1)) 
					selbid = ci[2];
				if(selbid!=0) {
					g.setColor(Color.BLACK);
					g.drawString("ID: "+selbid, 100, 200);
					if(Keyboard.isKeyPressed(KeyEvent.VK_C)) {
						Keyboard.keys[KeyEvent.VK_C] = false;
						setColor(selbid);
					}
						
				}
				
					
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
	
	static int invert(int color) {
		 return color ^ 0x00ffffff;
	}
	
	static void colorFromID(Graphics g, int id) {
		
		g.setColor(new Color(invert(Frame.backGround.getRGB())));
		if(blockColors.containsKey(id)) {
			if((blockColors.get(id).getRed()+blockColors.get(id).getGreen()+blockColors.get(id).getBlue())==0)
				g.setColor(new Color(invert(Frame.backGround.getRGB())));
			else
				g.setColor(blockColors.get(id));
		}
			
		
		
	}

	
	
}
