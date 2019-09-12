package Heroes;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import Attacks.OnHitAttack;
import TileMap.TileMap;

public class ArchMage extends Player{
	
	private BufferedImage[] fireBall = new BufferedImage[3];
	private BufferedImage[] meteor = new BufferedImage[1];
	private BufferedImage[] emptyEffect = new BufferedImage[1];

	protected static String type = "archmage"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 3, 3, 1
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 47, 30, 36	
	};
	
	
	public ArchMage(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
		BufferedImage gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/fireball.gif"
				)
			);
			
			for(int i = 0; i < 3; i++) {
						fireBall[i] = gif.getSubimage(20 * i, 0, 20, 20);
			}
		
		gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/meteor.gif"
				)
			);
				
			meteor[0] = gif.getSubimage(0, 0, 60, 60);
			
			gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/emptyeffect.gif"
					)
				);
					
				emptyEffect[0] = gif.getSubimage(0, 0, 30, 30);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	public void QAbility(){
		stop();
		Attack fire = new Attack(tileMap, fireBall, x + width/2, y, 30, (int)(damage * 1.25), 30, 30);
		fire.setDim(15, 15);
		if(facingRight){
			fire.setVector(5, 0);
		}else{
			fire.setVector(-5, 0);
		}
		fire.setNoCollide(true);
		fire.addFlag("remove");
		attacks.add(fire);
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(50);
		width = 35;
		qcd += 70;
	}
	
	public void WAbility(){
		if(mana > 0){
			stop();
			setLadder(false);
			OnHitAttack dummy = new OnHitAttack(tileMap, emptyEffect, x, y - 6, 5, 0, 30, 30);
			dummy.setNoCollide(true);
			dummy.setVector(20, 0);
			dummy.addFlag("");
			if(!facingRight){
				dummy.setVector(-20, 0);
			}
			Attack met = new Attack(tileMap, meteor, x, y, 20, damage * 2, 60, 60);
				met.setOffset(-50, -100);
				met.facingRight = facingRight;
				met.setVector(5, 5);
				if(!facingRight){
					met.setVector(-5, 5);
				}
				met.setNoCollide(true);
			dummy.addSpawnable(met);
			attacks.add(dummy);
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(100);
			width = 30;
			mana--;
			wcd += 160;
		}
	}
	
	public void EAbility(){
		if(mana > 0){
			stop();
			onLadder = false;
			Attack fire = new Attack(tileMap, fireBall, x, y, 15, (int)(damage * 1.5), 30, 30);
			fire.setDim(15, 15);
			fire.setVector(0, 5);
			fire.addFlag("remove");
			attacks.add(fire);
			dy = -7;
			currentAction = E;
			animation.setFrames(sprites.get(E));
			animation.setDelay(190);
			width = 30;
			mana--;
		}
	}
	
}
