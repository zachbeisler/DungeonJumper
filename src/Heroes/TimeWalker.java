package Heroes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Attacks.Attack;
import TileMap.TileMap;

public class TimeWalker extends Player {
	
	private Point[] pastLocations = new Point[50];
	private int reversing = -1;
	private int frames = 0;
	private Attack aoe;

	private BufferedImage[] emptyEffect = new BufferedImage[1];
	private BufferedImage[] crossHair = new BufferedImage[1];
	
	protected static String type = "time"; 
	
	protected static int[] numFrames = {
		6, 8, 1, 8, 4, 5, 3
	};
	protected static int[] frameWidth = {
		30, 30, 30, 30, 40, 30, 30	
	};

	public TimeWalker(TileMap tm) {
		super(tm, numFrames, frameWidth, type);
		
		
		try{
			
			BufferedImage gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/emptyeffect.gif"));
			
			emptyEffect[0] = gif.getSubimage(0, 0, 40, 40);
				
			gif = ImageIO.read(getClass().getResourceAsStream("/Attacks/crosshair.gif"));
	
			crossHair[0] = gif.getSubimage(0, 0, 30, 30);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		ecd = 180;
		
	}
	
	public void update(){
		super.update();
		if(reversing >= 0){
			if(pastLocations[reversing] == null){ reversing = -1; return;}
			if(currentAction != E){
				currentAction = E;
				animation.setFrames(sprites.get(E));
				animation.setDelay(60);
			}
			setPosition(pastLocations[reversing].getX(),pastLocations[reversing].getY());
			pastLocations[reversing] = null;
			dx = 0;
			dy = 0;
			reversing -= 2;
		}else{
			frames++;
			if(frames == 3) {
				for(int i=0;i<pastLocations.length-1;i++){
					pastLocations[i] = pastLocations[i+1];
				}
				pastLocations[pastLocations.length-1] = new Point((int)(x),(int)(y));
				frames = 0;
			}
			invulnerable = false;
			if(aoe != null) attacks.remove(aoe);
		}
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
		g.setColor(new Color(96,192,224,100));
		for(int i=0; i<pastLocations.length-1; i++){
			if(pastLocations[i] != null && pastLocations[i+1] != null)
			g.drawLine((int)(pastLocations[i].x + xmap),(int)(pastLocations[i].y + ymap),(int)(pastLocations[i+1].x + xmap),(int)(pastLocations[i+1].y+ymap));
		}
	}
	
	public void QAbility(){
		
	}
	
	public void WAbility(){
		
	}
	
	public void EAbility(){
		setLadder(false);
		invulnerable = true;
		reversing = pastLocations.length-1;
		currentAction = E;
		animation.setFrames(sprites.get(E));
		animation.setDelay(60);
		aoe = new Attack(tileMap, emptyEffect, x, y, 1000, (int) (damage * 2), 50, 50);
		aoe.setFollowing(true);
		attacks.add(aoe);
		
		width = 30;
		ecd = 230;
	}
	
	public void reset(){
		System.out.println("reset");
		for(int i=0;i<pastLocations.length;i++){
			pastLocations[i] = new Point((int)x,(int)y);
		}
	}

}
