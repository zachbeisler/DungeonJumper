package Heroes;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import TileMap.TileMap;

public class Paladin extends Player{
	
	private BufferedImage[] hammerEffect = new BufferedImage[4];
	private BufferedImage[] novaEffect = new BufferedImage[3];
	private BufferedImage[] shieldEffect = new BufferedImage[2];
	
	private long shieldTimer = 0;
	
	protected static String type = "paladin"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 3, 1, 0
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 30, 30, 0	
	};

	public Paladin(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
		BufferedImage gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/hammereffect.gif"
				)
			);
			
			for(int i = 0; i < 4; i++) {
						hammerEffect[i] = gif.getSubimage(20 * i, 0, 20, 20);
			}
		
		gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/holynova.gif"
				)
			);
				
			for(int i = 0; i < 3; i++) {
						novaEffect[i] = gif.getSubimage(70 * i, 0, 70, 45);
			}
			
			
			gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/shieldeffect.gif"
					)
				);
					
				for(int i = 0; i < 2; i++) {
							shieldEffect[i] = gif.getSubimage(30 * i, 0, 30, 30);
				}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		super.update();
		if(shieldTimer > 1) shieldTimer--;
		if(shieldTimer == 1){
			shieldTimer--;
			invulnerable = false;
		}
	}
	
	public void QAbility(){
		stop();
		onLadder = false;
		Attack hammer = new Attack(tileMap, hammerEffect, x + cwidth, y - 10, 20, (int)(damage * 1.75), 20, 20);
		if(!facingRight){
			hammer.setPosition(hammer.getx() - cwidth*2, hammer.gety());
		}
		hammer.facingRight = facingRight;
		attacks.add(hammer);
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(90);
		width = 30;
	}
	
	public void WAbility(){
		if(mana >= 1){
			stop();
			onLadder = false;
			Attack nova = new Attack(tileMap, novaEffect, x, y - cwidth/2, 20, 2 + (int)(damage) + (maxHealth - 4), 70, 45);
			attacks.add(nova);
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(270);
			width = 30;
			mana--;
		}
	}
	
	public void EAbility(){
		if(mana >= 1){
			Attack shield = new Attack(tileMap, shieldEffect, x, y, 120 + (maxHealth - 4) * 9, (int) Math.floor(1 + damage/4), 30, 30);
			shield.setFollowing(true);
			attacks.add(shield);
			currentAction = E;
			animation.setFrames(sprites.get(Q));
			animation.setDelay(60);
			width = 30;
			invulnerable = true;
			shieldTimer += 120 + (maxHealth - 4) * 9;
			ecd = 60*8;
			mana--;
		}
		
		
	}

}
