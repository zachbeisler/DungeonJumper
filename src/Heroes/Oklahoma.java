package Heroes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;




import javax.imageio.ImageIO;

import Attacks.Attack;
import Attacks.OnHitAttack;
import Main.Game;
import TileMap.TileMap;

public class Oklahoma extends Player{
	
	private BufferedImage[] stabEffect = new BufferedImage[6];
	private BufferedImage[] whipEffect = new BufferedImage[3];
	private BufferedImage[] tntEffect = new BufferedImage[1];
	private BufferedImage[] explosionEffect = new BufferedImage[1];
	
	protected static String type = "oklahoma"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 3, 3, 6
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 40, 30, 30	
	};
	
	protected Attack whip;

	public Oklahoma(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		try{
			BufferedImage gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Attacks/emptyeffect.gif"
					)
				);
				
				for(int i = 0; i < 6; i++) {
							stabEffect[i] = gif.getSubimage(30 * i, 0, 30, 30);
				}
			
			gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/whipeffect.gif"));
			
				for(int i = 0; i < 3; i++){
					whipEffect[i] = gif.getSubimage(60*i, 0, 60, 20);
				}
				
			gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/tnt.gif"));
	
			tntEffect[0] = gif.getSubimage(0, 0, 4, 9);
			explosionEffect[0] = gif.getSubimage(4, 0, 80, 80);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		super.update();
		if(whip != null){
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(80);
			left = false;
			right = false;
			whip.update();
			if( whip.dx == 0 || (whip.dy == 0 && Math.abs(whip.dx) > 5)){
				whip.dx = 0;
				whip.dy = 0;
				dx = Math.signum(whip.getx() - x) * 4;
				dy = Math.signum(whip.gety() - y) * 5;
				
				if(Math.sqrt((whip.getx() - x)*(whip.getx() - x) + (whip.gety() - y)*(whip.gety() - y)) < 20){ 
					whip.setRemove(true);
					setJumping(true);
					setUp(true);
				}
			}
			if(Math.sqrt((whip.getx() - x)*(whip.getx() - x) + (whip.gety() - y)*(whip.gety() - y)) > 100.0) whip.setRemove(true);
			if(whip.shouldRemove()) whip = null;
		}else{
		}
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
		if(whip != null){
			whip.draw(g);
			g.setColor(Color.BLACK);
			g.fill(whip.getRectangle());
			g.drawLine((int)(x + xmap), (int)(y + ymap), (int)(whip.getx() + whip.getXmap()), (int)(whip.gety() + whip.getYmap()));
		}
	}
	
	public void QAbility(){
		stop();
		setLadder(false);
		Attack stab = new Attack(tileMap, stabEffect, x + cwidth, y - 3, 10, damage, 30, 30);
		if(!facingRight){
			stab.setPosition(stab.getx() - 2*cwidth, stab.gety());
		}
		stab.facingRight = facingRight;
		attacks.add(stab);
		currentAction = Q;
		animation.setFrames(sprites.get(Q));
		animation.setDelay(40);
		width = 40;
	}
	
	public void WAbility(){
		stop();
		setLadder(false);
		
		whip = new Attack(tileMap, whipEffect, x, y, 90, (int)(2 + damage/2), 5, 5);
		if(facingRight) whip.setVector(5, 0);
		else whip.setVector(-5, 0);
		if(dy < 0) whip.setVector(whip.dx + Math.signum(whip.dx), -5);
		
		currentAction = W;
		animation.setFrames(sprites.get(W));
		animation.setDelay(80);
		width = 30;
		wcd += 60 * 3;
	}
	
	public void EAbility(){
		if(mana >= 1){
			mana--;
			OnHitAttack tnt = new OnHitAttack(tileMap, tntEffect, x, y, 100, 2 + damage, 4, 9);
			tnt.setVector(4, -3 + dy/2);
			tnt.addFlag("trigger");
			tnt.setGravity(fallSpeed);
			tnt.setResist(.1);
			if(!facingRight) tnt.setVector(-4, -3 + dy/2);
			tnt.addSpawnable(new Attack(tileMap, explosionEffect, x, y, 10, 2 + damage, 80, 80));
			attacks.add(tnt);
			currentAction = W;
			animation.setFrames(sprites.get(W));
			animation.setDelay(80);
			width = 30;
		}
		
	}

	
	
}
