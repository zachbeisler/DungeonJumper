package GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public int previousState;
	protected boolean MOUSE_CONTROL = false;
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void unload();
	public abstract void restart();
	public boolean mouseControl() {return MOUSE_CONTROL;}
	protected void mouseClicked(MouseEvent e){};
	protected void mouseMoved(MouseEvent e){}
	protected void mouseWheelMoved(MouseWheelEvent e) {};
	
}
