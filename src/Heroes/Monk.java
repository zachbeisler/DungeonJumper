package Heroes;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import TileMap.TileMap;

public class Monk extends Player{
	
	private BufferedImage[] punchEffect = new BufferedImage[3];
	private BufferedImage[] shockwave = new BufferedImage[1];
	
	
	private int regenTimer = 60;
	
	protected static String type = "monk"; 
	
	protected static int[] numFrames = {
		6, 4, 1, 1, 2, 4, 5, 
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 40, 30, 40	
	};

	public Monk(TileMap tm) {
		
		super(tm, numFrames, frameWidth, type);
		try{
			BufferedImage gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/emptyeffect.gif"
					)
				);
				
				for(int i = 0; i < 3; i++) {
							punchEffect[i] = gif.getSubimage(30 * i, 0, 30, 30);
				}
				
				shockwave[0] = ImageIO.read(getClass().getResourceAsStream("/Attacks/shockwave.gif"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		super.update();
		if(regenTimer > 0) regenTimer--;
		else{
			if(mana < maxMana)mana++;
			regenTimer = 90;
		}
		if(currentAction == W && animation.getFrame() == 3){
			Attack wave1 = new Attack(tileMap, shockwave, x + 2*cwidth/3, y, 10, (int)(damage), 30, 20);
			wave1.setVector(6,0);
			wave1.setNoCollide(true);
			Attack wave2 = new Attack(tileMap, shockwave, x - 2*cwidth/3, y, 10, (int)(damage), 30, 20);
			wave2.setVector(-6,0);
			wave1.setRight(true);
			wave2.setNoCollide(true);
			attacks.add(wave1);
			attacks.add(wave2);
			
		}
		
	}
	
	public void QAbility(){
		stop();
		Attack jab = new Attack(tileMap, punchEffect, x + cwidth, y - 3, 10, (int)(damage/2 + 1), 30, 30);
		if(!facingRight){
			jab.setPosition(jab.getx() - 2*cwidth, jab.gety());
		}
		jab.facingRight = facingRight;
		jab.addFlag("restore");
		attacks.add(jab);
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(65);
		width = 40;
	}
	
	public void WAbility(){
		if(mana > 0){
			stop();
			onLadder = false;
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(40);
			mana--;
			wcd = 30;
		}
	}
	
	public void EAbility(){
		if(mana > 0){
			stop();
			onLadder = false;
			Attack wave = new Attack(tileMap, shockwave, x, y, 18, (int)(1.5 * damage), 30, 20);
			wave.setNoCollide(true);
			dy = -6;
			if(facingRight){ dx = -5; wave.setVector(5,2); wave.setRight(true);}
			else{ dx = 5; wave.setVector(-5,2);}
			attacks.add(wave);
			currentAction = E;
			animation.setFrames(sprites.get(E));
			animation.setDelay(55);
			width = 40;
		mana--;
		}
		
	}

}
