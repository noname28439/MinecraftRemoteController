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
	
	public static void load() {
		
		frame = new JFrame();
		panel = new JPanel();
		
		panel.add(connectionList);
		
		
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setTitle("Main IOT Controller");
		frame.setVisible(true);
		
		
	}
	
	public static void setList(ArrayList<ServerConnection> list) {
		String[] newList = new String[list.size()];
		for(int i = 0; i<list.size();i++) {
			ServerConnection cc = list.get(i);
			newList[i]=(cc.name+"["+cc.location[0]+"|"+cc.location[1]+"|"+cc.location[2]+"]");
		}
			
		
		connectionList.setListData(newList);
		
	}
	
}