package Heroes;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Attacks.Attack;
import Attacks.OnHitAttack;
import Main.Game;
import TileMap.TileMap;

public class Scout extends Player{
	
	private int rollTimer;
	private boolean hasArrow;
	
	private BufferedImage[] arrowEffect = new BufferedImage[1];
	private BufferedImage[] emptyEffect = new BufferedImage[1];
	private BufferedImage[] crossHair = new BufferedImage[1];
	
	protected static String type = "scout"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 4, 5, 4
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 40, 30, 30	
	};

	public Scout(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		
		try{
			BufferedImage gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/arrow.gif"
					)
				);
				

			arrowEffect[0] = gif.getSubimage(0, 0, 20, 10);
			
			gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/emptyeffect.gif"));
			
			emptyEffect[0] = gif.getSubimage(0, 0, 40, 40);
				
			gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/crosshair.gif"));
	
			crossHair[0] = gif.getSubimage(0, 0, 30, 30);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		super.update();
		if(rollTimer > 1){ 
			if(!facingRight) dx = -10;
			else dx = 10;
			rollTimer--;
		}
		if(rollTimer == 1){
			rollTimer--;
			invulnerable = false;
			maxSpeed = 1.6;
			dx = 0;
		}
		if(currentAction == Q && animation.getFrame() == 3 && hasArrow){
			Attack arrow = new Attack(tileMap, arrowEffect, x + cwidth, y, 30, (int)(damage * 1.25), 20, 10);
			arrow.setVector( 6, 0);
			if(!facingRight){
				arrow.setPosition(arrow.getx() - 2*cwidth, arrow.gety());
				arrow.setVector( -6, 0);
			}
			arrow.facingRight = facingRight;
			attacks.add(arrow);
			hasArrow = false;
			
		}
	}
	
	public void QAbility(){
		stop();
		hasArrow = true;
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(150);
		width = 40;
	}
	
	public void WAbility(){
		if(mana > 0){
			stop();
			OnHitAttack dummy = new OnHitAttack(tileMap, emptyEffect, x, y, 30, 0, 20, 20);
			dummy.setNoCollide(true);
			dummy.addFlag("remove");
			dummy.setVector(15, 0);
			if(!facingRight){
				dummy.setVector(-15, 0);
			}
			OnHitAttack crossHairs = new OnHitAttack(tileMap, crossHair, x, y, 50, 0, 30, 30);
			crossHairs.setDim(0, 0);
			crossHairs.addFlag("trigger");
			crossHairs.setOffset(-20, -3);
				if(!facingRight){
					crossHairs.setOffset(20,-3);
				}
			Attack snipe = new Attack(tileMap, arrowEffect, x, y, 50, (int)(damage * 3), 20, 10);
				snipe.addFlag("remove");
				snipe.setNoCollide(true);
				snipe.setOffset(-150, -50);
				snipe.setVector(12, 4);
				if(!facingRight){
					snipe.setVector(-12, 4);
				}
				snipe.facingRight = facingRight;
			crossHairs.addSpawnable(snipe);
			dummy.addSpawnable(crossHairs);
			attacks.add(dummy);
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(100);
			width = 30;
			mana--;
		}
	}
	
	public void EAbility(){
			setLadder(false);
			rollTimer = 9;
			maxSpeed = 10;
			invulnerable = true;
			currentAction = E;
			animation.setFrames(sprites.get(E));
			animation.setDelay(100);
			width = 30;
			ecd = 50;
		
	}

	
	
}