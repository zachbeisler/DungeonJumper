package Heroes;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import Attacks.CenteredAttack;
import Attacks.OnHitAttack;
import Main.Game;
import TileMap.TileMap;

public class Link extends Player{
	
	private int qTimer;
	private int qFrame;
	
	private BufferedImage[] boomerang = new BufferedImage[8];
	private BufferedImage[] emptyEffect = new BufferedImage[1];
	private BufferedImage[] lightArrow = new BufferedImage[1];
	private BufferedImage[] lightEffect = new BufferedImage[1];
	private boolean hasArrow;
	
	protected static String type = "link"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 6, 2, 2
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 50, 30, 30	
	};

	public Link(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
			BufferedImage gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/boomerang.gif"
					)
				);
				
			for(int i=0; i<8; i++){
				boomerang[i] = gif.getSubimage(16*i, 0, 16, 16);
			}
			
			gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/emptyeffect.gif"));
			
			emptyEffect[0] = gif.getSubimage(0, 0, 40, 40);
				
			gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/lightarrow.gif"));
	
			lightArrow[0] = gif.getSubimage(0, 0, 25, 5);
			lightEffect[0] = gif.getSubimage(25, 0, 40, 40);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		super.update();
		if(qTimer > 1) qTimer--;
		if(qTimer == 1){
			qTimer--;
			qFrame = 0;
		}
		if(animation.getFrame() == qFrame && currentAction == Q){
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(400);
			width = 30;
		}else if(animation.getFrame() == 1 && currentAction == E && hasArrow){
			OnHitAttack arrow = new OnHitAttack(tileMap, lightArrow, x + cwidth, y, 30, (int)(damage * 2), 25, 5);
			arrow.setVector(6, 0);
			arrow.addFlag("trigger");
			if(!facingRight){
				arrow.setPosition(arrow.getx() - 2*cwidth, arrow.gety());
				arrow.setVector( -6, 0);
			}
			arrow.setLinger(false);
			arrow.facingRight = facingRight;
			arrow.addSpawnable(new Attack(tileMap, lightEffect, x, y, 10, damage * 2, 40, 40));
			attacks.add(arrow);
			hasArrow = false;
			mana--;
		}
	}
	
	public void QAbility(){
		stop();
		if(currentAction != Q){
			if(qFrame == 0 || qFrame == 2){
				Attack stab = new Attack(tileMap, emptyEffect, x + 30, y, 10, (int)(damage), 30, 30);
				if(!facingRight){
					stab.setPosition(stab.getx() - 60, stab.gety());
				}
				stab.facingRight = facingRight;
				attacks.add(stab);
			}else{
				Attack stab = new Attack(tileMap, emptyEffect, x + 40, y, 10, (int)(damage * 1.75), 45, 30);
				if(!facingRight){
					stab.setPosition(stab.getx() - 80, stab.gety());
				}
				stab.facingRight = facingRight;
				attacks.add(stab);
			}
			currentAction = Q;
			animation.setFrames(sprites.get(Q));
			animation.setDelay(150);
			animation.setFrame(qFrame);
			qFrame += 2;
			qTimer += 400;
			if(qFrame > 4){
				qFrame = 0;
				qTimer = 0;
			}
			width = 50;
		}
	}
	
	public void WAbility(){
		if(mana > 0){
			stop();
			CenteredAttack boom = new CenteredAttack(tileMap, boomerang, x + 17, y - 15, 70, (int)(damage * 1.5), 16, 16);
			boom.setCenter(getx() + getXmap(), gety() + getYmap());
			boom.setVector(10, 0);
			boom.setNoCollide(true);
			if(!facingRight){
				boom.setPosition(boom.getx() - 34, boom.gety());
				boom.setVector(-10, 0);
			}
			boom.setAcceleration(.5);
			boom.setCenterFollow(true);
			attacks.add(boom);
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(100);
			width = 30;
			mana--;
			wcd += 180;
		}
	}
	
	public void EAbility(){
		if(mana > 0){	
			stop();
			hasArrow = true;
			currentAction = E;
			animation.setFrames(sprites.get(E));
			animation.setDelay(100);
			width = 30;
		}
	}
}