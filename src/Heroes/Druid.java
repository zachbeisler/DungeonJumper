package Heroes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Attacks.Attack;
import Attacks.OnHitAttack;
import TileMap.TileMap;

public class Druid extends Player{

	private BufferedImage[] wrathMissile = new BufferedImage[1];
	private BufferedImage[] moonFire = new BufferedImage[6];
	private BufferedImage[] shapeShift = new BufferedImage[1];
	private BufferedImage[] swipeEffect = new BufferedImage[3];
	private BufferedImage[] pounceEffect = new BufferedImage[3];
	private ArrayList<BufferedImage[]> humanForm;
	private ArrayList<BufferedImage[]> catForm;

	private boolean form = true;
	public int leapTimer;

	protected static String type = "druid"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 3, 1, 3
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 30, 30, 30	
	};
	protected static int[] catFrameWidth = {
		30, 30, 30, 30, 50, 30, 30	
	};
	
	
	public Druid(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
		BufferedImage gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/wratheffect.gif"
				)
			);

			wrathMissile[0] = gif.getSubimage(0, 0, 17, 17);
		
		gif = ImageIO.read(
				getClass().getResourceAsStream(
					"/Attacks/moonfire.gif"
				)
			);
				
			for(int i = 0; i < 6; i++) {
						moonFire[i] = gif.getSubimage(30 * i, 0, 30, 50);
			}
			
			
			gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/shapeshift.gif"
					)
				);
					
				shapeShift[0] = gif.getSubimage(0, 0, 30, 30);
				
			gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/emptyeffect.gif"
					)
				);
					
			for(int i = 0; i < 3; i++) {
				swipeEffect[i] = gif.getSubimage(30 * i, 0, 30, 30);
			}
			
			gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/pounceeffect.gif"
					)
				);
			pounceEffect[0] = gif.getSubimage(0, 0, 70, 40);
			
			gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Player/druidCatForm.gif"
					)
				);
				
				catForm = new ArrayList<BufferedImage[]>();
				for(int i = 0; i < numFrames.length; i++) {
					
					BufferedImage[] bi =
						new BufferedImage[numFrames[i]];
					
					for(int j = 0; j < numFrames[i]; j++) {
							bi[j] = gif.getSubimage(
									j * catFrameWidth[i],
									i * height,
									catFrameWidth[i],
									height
							);
						
					}
					
					catForm.add(bi);
					
				}
			
			
			humanForm = sprites;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		super.update();
		if(leapTimer > 1){ 
			if(dx < 0) dx = -20;
			else dx = 20;
			leapTimer--;
		}
		if(leapTimer == 1){
			leapTimer--;
			invulnerable = false;
			maxSpeed = 1.6;
			dx = 0;
		}
	}
	
	public void QAbility(){
		if(form == false){
			stop();
			setLadder(false);
			Attack slash = new Attack(tileMap, swipeEffect, x + cwidth, y - 6, 10, damage, 30, 30);
			if(!facingRight){
				slash.setPosition(slash.getx() - 2*cwidth, slash.gety());
			}
			slash.facingRight = facingRight;
			attacks.add(slash);
			currentAction = Q;
			animation.setFrames(sprites.get(Q));
			animation.setDelay(50);
			width = 50;
		}else if(mana > 0){
			stop();
			Attack missile = new Attack(tileMap, wrathMissile, x + cwidth/2, y, 50, (int)(damage * 1.5), 17, 17);
			missile.setVector(4,0);
			missile.addFlag("remove");
			missile.setNoCollide(true);
			if(!facingRight){
				missile.setPosition(missile.getx() - cwidth * 3/2, missile.gety());
				missile.setVector(-4,0);
			}
			missile.facingRight = facingRight;
			attacks.add(missile);
			currentAction = Q;
			animation.setFrames(sprites.get(Q));
			animation.setDelay(40);
			width = 30;
			mana--;
			qcd = 100;
		}
	}
	
	public void WAbility(){
		if(form == false){
			setLadder(false);
			OnHitAttack dummy = new OnHitAttack(tileMap, swipeEffect, x, y, 45, (int)(damage * 1.5), 35, 35);
			dummy.addFlag("remove");
			dummy.addFlag("flinch");
			dummy.addFlag("stop");
			dummy.setFollowing(true);
			Attack pounce = new Attack(tileMap, pounceEffect, x, y, 30, 0, 70, 40);
				pounce.facingRight = facingRight;
				dummy.addSpawnable(pounce);
			attacks.add(dummy);
			setVector(10, -3);
			if(!facingRight) setVector(-10, -3);
			invulnerable = true;
			wcd = 120;
			leapTimer = 9;
			maxSpeed = 10;
			currentAction = E;
			animation.setFrames(sprites.get(E));
			animation.setDelay(100);
			width = 30;
			
		}else{
			stop();
			setLadder(false);
			OnHitAttack dummy = new OnHitAttack(tileMap, swipeEffect, x, y - 6, 5, (int)(1 + damage/2), 20, 20);
			dummy.setNoCollide(true);
			dummy.setVector(15, 0);
			dummy.addFlag("");
			if(!facingRight){
				dummy.setVector(-15, 0);
			}
			Attack moon = new Attack(tileMap, moonFire, x, y, 17, 0, 30, 50);
			dummy.addSpawnable(moon);
			attacks.add(dummy);
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(100);
			width = 30;
		}
	}
	
	
	public void EAbility(){
		stop();
		setLadder(false);
		Attack shift = new Attack(tileMap, shapeShift, x, y, 30, 0, 30, 30);
		shift.setFollowing(true);
		attacks.add(shift);
		currentAction = E;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(110);
		width = 30;
		if(form == true){
			form = false;
			sprites = catForm;
			jumpStart = -5.5;
		}else{
			form = true;
			sprites = humanForm;
			jumpStart = -4.8;
		}
		ecd = 300;
	}
	

}
