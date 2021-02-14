package display;

import java.util.Random;


public class Display {

	public static boolean DO_GAME_TICK = true;
	
	
	public static Frame frame;
	public static void start() {
		Thread t = new Thread() {
			public void run() {
				try {
					World.load();
					
					
					
					frame = new Frame();
					//frame.setUndecorated(true);
					frame.setSize(Frame.WIDTH, Frame.HEIGHT);
					frame.setDefaultCloseOperation(3);
					frame.setLocationRelativeTo(null);
					frame.setResizable(true);
					frame.setVisible(true);
					frame.setAlwaysOnTop(true);
					
					frame.makestrat();
					
					
					long lastFrame = System.currentTimeMillis();
					
					while(true) {
						
							long thisFrame = System.currentTimeMillis();
							
							float timesincelastframe = (float) ((thisFrame - lastFrame) / 1000.0);
							lastFrame = thisFrame;
							
							frame.update(timesincelastframe);
							
							frame.repaint();
							
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						
					
//						if(Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
//							Main.DO_GAME_TICK = !Main.DO_GAME_TICK;
//							Keyboard.keys[KeyEvent.VK_SPACE]=false;
//						}	
					}
						
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		
	}
	
}
