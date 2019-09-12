package Heroes;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import TileMap.TileMap;

public class Mage extends Player{
	
	private BufferedImage[] arcaneMissile = new BufferedImage[3];
	private BufferedImage[] blinkEffect = new BufferedImage[3];
	private BufferedImage[] novaEffect = new BufferedImage[1];
	private BufferedImage[] shieldEffect = new BufferedImage[1];
	
	private long blinkTimer = 0;
	private long invulnTimer = 0;


	protected static String type = "wizard"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 3, 1, 3
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 30, 30, 30	
	};
	
	
	public Mage(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
		BufferedImage gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/arcanemissile.gif"
				)
			);
			
			for(int i = 0; i < 3; i++) {
						arcaneMissile[i] = gif.getSubimage(20 * i, 0, 20, 15);
			}
		
		gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/blink.gif"
				)
			);
				
			for(int i = 0; i < 3; i++) {
						blinkEffect[i] = gif.getSubimage(30 * i, 0, 30, 30);
			}
			
			
			gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/frostnova.gif"
					)
				);
					
				for(int i = 0; i < 1; i++) {
							novaEffect[i] = gif.getSubimage(30 * i, 0, 90, 5);
				}
				
				gif = ImageIO.read(
						getClass().getResourceAsStream(
							"/Attacks/frostshieldeffect.gif"
						)
					);
						
					shieldEffect[0] = gif.getSubimage(0, 0, 30, 30);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		super.update();
		if(blinkTimer > 1){ 
			if(dx < 0) dx = -20;
			else dx = 20;
			blinkTimer--;
		}
		if(blinkTimer == 1){
			blinkTimer--;
			maxSpeed = 1.6;
			dx = 0;
		}
		if(invulnTimer > 1){ 
			invulnTimer--;
		}else if(invulnTimer == 1){
			invulnerable = false;
			invulnTimer--;
		}
	}
	
	public void QAbility(){
		stop();
		Attack missile = new Attack(tileMap, arcaneMissile, x + cwidth/2, y, 30, damage, 20, 15);
		missile.setVector(6,0);
		missile.addFlag("remove");
		missile.setLinger(false);
		if(!facingRight){
			missile.setPosition(missile.getx() - cwidth* 3/2, missile.gety());
			missile.setVector(-6,0);
		}
		missile.facingRight = facingRight;
		attacks.add(missile);
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(90);
		width = 30;
	}
	
	public void WAbility(){
			onLadder = false;
			stop();
			Attack blink = new Attack(tileMap, blinkEffect, x, y, 20, 0, 30, 30);
			attacks.add(blink);
			if(facingRight){
				dx = 40;
			}else{dx = -40;}
			maxSpeed = 40;
			invulnerable = true;
			blinkTimer = 5;
			invulnTimer += 15;
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(50);
			width = 30;
			wcd += 14;
	}
	
	public void EAbility(){
	if(mana >= 1){
		stop();
		Attack nova = new Attack(tileMap, novaEffect, x, y + 9, 30, 2 + damage, 90, 10);
		Attack shield = new Attack(tileMap, shieldEffect, x, y, 20 + (maxMana - 3) * 15, 0, 30, 30);
		shield.setFollowing(true);
		attacks.add(nova);
		attacks.add(shield);
		currentAction = E;
		animation.setFrames(sprites.get(E));
		animation.setDelay(110);
		width = 30;
		invulnTimer = 20 + (maxMana - 3) * 15;
		mana--;
	}
	}

}
