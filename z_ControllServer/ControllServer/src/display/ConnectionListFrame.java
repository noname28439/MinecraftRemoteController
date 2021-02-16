package display;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import server.ServerConnection;


public class ConnectionListFrame {

	public static JFrame frame;
	public static JPanel panel;
	public static JList<String> connectionList = new JList<>();
	static String[] currentList = new String[] {};
	
	public static void load() {
		
		frame = new JFrame();
		panel = new JPanel();
		
		panel.add(connectionList);
		
		
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setTitle("ConnectoinList");
		frame.setSize(200, 300);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		
	}
	
	public static void setList(ArrayList<ServerConnection> list) {
		try {
		String[] newList = new String[list.size()];
		for(int i = 0; i<list.size();i++) {
			ServerConnection cc = list.get(i);
			newList[i]=(cc.name+"[Server:"+cc.ip+"|X:"+cc.location[0]+"|Y:"+cc.location[1]+"|Z:"+cc.location[2]+"|HP:"+cc.hp+"|Food:"+cc.food+"]");
		}
		
		connectionList.setListData(newList);
		currentList = newList;
		frame.pack();
		}catch (java.lang.ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
		
		
	}
	
}