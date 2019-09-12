package Heroes;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import TileMap.TileMap;

public class Knight extends Player{
	
	private BufferedImage[] emptyEffect = new BufferedImage[1];
	
	protected static String type = "knight"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 4, 4, 2
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 55, 30, 30	
	};
	
	private boolean defStance;
	private int chargeTimer;

	public Knight(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
			
			BufferedImage gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/emptyeffect.gif"));
			
			emptyEffect[0] = gif.getSubimage(0, 0, 40, 40);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		super.update();
		if(chargeTimer > 1){
			dx = Math.signum(dx) * 7;
			chargeTimer--;
		}
		if(chargeTimer == 1){
			dx = 0;
			invulnerable = false;
			chargeTimer--;
		}
		if(defStance){
			onLadder = false;
			if(currentAction != W){
				currentAction = W;
				maxSpeed = .5;
				animation.setFrames(sprites.get(W));
				animation.setDelay(140);
				width = 30;
				invulnerable = true;
			}
		}else{
			maxSpeed = 2.0;
			invulnerable = false;
		}
	}
	
	public void QAbility(){
		stop();
		defStance = false;
		Attack stab = new Attack(tileMap, emptyEffect, x + 40, y, 10, (int)(damage), 45, 30);
		if(!facingRight){
			stab.setPosition(stab.getx() - 80, stab.gety());
		}
		stab.facingRight = facingRight;
		attacks.add(stab);
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(40);
		width = 55;
	}
	
	public void WAbility(){
		defStance = !defStance;
		wcd += 60;
		if(!defStance){
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(400);
		}
	}
	
	public void EAbility(){
		if(mana > 0){	
			stop();
			defStance = false;
			onLadder = false;
			chargeTimer = 20;
			if(facingRight) dx = 7;
			else dx = -7;
			Attack dummy = new Attack(tileMap, emptyEffect, x, y, 45, (int)(damage * 1.5), 40, 40);
			dummy.setFollowing(true);
			attacks.add(dummy);
			currentAction = E;
			animation.setFrames(sprites.get(WALKING));
			animation.setDelay(60);
			width = 30;
			mana--;
		}
	}
}