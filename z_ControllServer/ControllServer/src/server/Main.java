package server;

import display.ConnectionListFrame;
import display.Display;

public class Main {

	public static void main(String[] args) {
		ConnectionListFrame.load();
		Display.start();
		ControllServer.startServer();

	}

}
