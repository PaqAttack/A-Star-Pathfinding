package main.java;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyhandler implements KeyListener{
	
	DemoPanel dp;
	
	public Keyhandler(DemoPanel dp) {
		this.dp = dp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_ENTER) {
//			dp.search();
			System.out.println("starting");
			dp.autoSearch();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
