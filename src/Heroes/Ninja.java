package Heroes;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import Attacks.OnHitAttack;
import TileMap.TileMap;

public class Ninja extends Player{
	
	private BufferedImage[] slashEffect = new BufferedImage[2];
	private BufferedImage[] shuriken = new BufferedImage[3];
	private BufferedImage[] shadowStep = new BufferedImage[3];
	
	protected static String type = "ninja";
	protected int shurikenCount;
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 3, 3, 1
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 35, 30, 30	
	};

	public Ninja(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
			BufferedImage gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/emptyeffect.gif"
					)
				);
				
				for(int i = 0; i < 2; i++) {
							slashEffect[i] = gif.getSubimage(30 * i, 0, 30, 30);
				}
				
				gif = ImageIO.read(
						getClass().getResourceAsStream(
							"/Attacks/shuriken.gif"
						)
					);
				
				for(int i = 0; i < 3; i++) {
					shuriken[i] = gif.getSubimage(15 * i, 0, 15, 15);
				}
				
				gif = ImageIO.read(
						getClass().getResourceAsStream(
							"/Attacks/shadowstep.gif"
						)
					);
				
				for(int i = 0; i < 3; i++) {
					shadowStep[i] = gif.getSubimage(30 * i, 0, 30, 30);
				}
						
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		super.update();
		if(currentAction == W && animation.getFrame() == shurikenCount){
			Attack missile = new Attack(tileMap, shuriken, x + cwidth/2, y, 50, (int)(1 + damage/2), 15, 15);
			missile.setVector(6,0);
			if(!facingRight){
				missile.setPosition(missile.getx() - cwidth* 3/2, missile.gety());
				missile.setVector(-6,0);
			}
			missile.facingRight = facingRight;
			attacks.add(missile);
			shurikenCount++;
		}
	}
	
	
	
	
	
	public void QAbility(){
		stop();
		Attack slash = new Attack(tileMap, slashEffect, x + cwidth, y - 6, 10, damage, 30, 30);
		if(!facingRight){
			slash.setPosition(slash.getx() - 2*cwidth, slash.gety());
		}
		slash.facingRight = facingRight;
		attacks.add(slash);
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(80);
		width = 35;
	}
	
	public void WAbility(){
		if(mana > 0){
			stop();
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(80);
			width = 30;
			shurikenCount = 0;
			mana--;
			wcd += 160;
		}
	}
	
	public void EAbility(){
		if(mana > 0){
			stop();
			onLadder = false;
			OnHitAttack dummy = new OnHitAttack(tileMap, slashEffect, x, y - 6, 20, 1 + (damage*2), 20, 20);
			dummy.setVector(10, 0);
			dummy.setNoCollide(true);
			dummy.addFlag("switch");
			dummy.addFlag("flinch");
			if(!facingRight){
				dummy.setVector(-10, 0);
			}
				Attack smoke = new Attack(tileMap, shadowStep, x, y, 30, 0, 30, 30);
				Attack smoke2 = new Attack(tileMap, shadowStep, x, y, 30, 0, 30, 30);
				smoke.setMoving(false);
				smoke2.setFollowing(true);
			dummy.addSpawnable(smoke);
			dummy.addSpawnable(smoke2);
			attacks.add(dummy);
			currentAction = E;
			animation.setFrames(sprites.get(E));
			animation.setDelay(20);
			width = 30;
			mana--;
			ecd = 160;
		}
	}

}
