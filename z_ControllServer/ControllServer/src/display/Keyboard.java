package display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Keyboard implements KeyListener,MouseMotionListener, MouseListener, MouseWheelListener{

	public static boolean[] keys = new boolean[1024];
	
	private static int mousex;
	private static int mousey;
	public static int button;
	public static int StartMousex, StartMousey;
	public static int tox=10, toy=10, msx, msy;
	
	public static int getMousex() {
		return mousex;
	}
	
	public static int getMousey() {
		return mousey;
	}
	
	public static int getMousexInCoords() {
		return -(World.mapx-Keyboard.getMousex());
	}
	
	public static int getMouseyInCoords() {
		return -(World.mapy-Keyboard.getMousey());
	}
	
	
	public static boolean isKeyPressed(int KeyCode) {
		return keys[KeyCode];
	}
	
	public static int getButton() {
		return button;
	}
	
	

	
	@Override
	public void keyPressed(KeyEvent e) {
	
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
		mousex = e.getX();
		mousey = e.getY();
		
		
		if(button==1) {
			
		}else 
		if(button==3) {
		World.mapx=msx+(tox-e.getX());
		World.mapy=msy+(toy-e.getY());
		}else if(button==1) {
			
			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		
		
		
		StartMousex = e.getX();
		StartMousey = e.getY();
		
		tox = Keyboard.getMousex();
		toy = Keyboard.getMousey();
		msx = World.mapx;
		msy = World.mapy;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		button = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		button = -1;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		
	}

	
	
	
}
