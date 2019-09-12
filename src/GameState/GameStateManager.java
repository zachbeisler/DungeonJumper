package GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import Main.Game;

public class GameStateManager {
	
	public GameState[] gameStates;
	public int currentState;
	
	public static final int NUMGAMESTATES = 4;
	public static final int MENUSTATE = 0;
	public static final int CHARACTERSTATE = 1;
	public static final int LEVELSTATE = 2;
	public static final int OPTIONSTATE = 3;
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		Game.loadResources();
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == CHARACTERSTATE)
			gameStates[state] = new CharacterState(this);
		if(state == OPTIONSTATE)
			gameStates[state] = new OptionState(this);
	}	
	
	public void setState(int state, int param){
		int prevState = currentState;
		unloadState(currentState);
		gameStates[state] = new LevelState(this,param);
		currentState = state;
		gameStates[currentState].previousState = prevState;
	}
	
	private void unloadState(int state) {
		gameStates[state].unload();
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		int prevState = currentState;
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		gameStates[currentState].previousState = prevState;
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
	public void mouseClicked(MouseEvent e) {
		if(gameStates[currentState].mouseControl()) gameStates[currentState].mouseClicked(e);
	}

	public void mouseMoved(MouseEvent e) {
		if(gameStates[currentState].mouseControl()) gameStates[currentState].mouseMoved(e);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if(gameStates[currentState].mouseControl()) gameStates[currentState].mouseWheelMoved(e);
	}
	
}









