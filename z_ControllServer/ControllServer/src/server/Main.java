package server;

import display.ConnectionListFrame;
import display.Display;
import display.Map;

public class Main {

	public static void main(String[] args) {
		Map.loadColorPresets();
		ConnectionListFrame.load();
		Display.start();
		ControllServer.startServer();

	}

}
