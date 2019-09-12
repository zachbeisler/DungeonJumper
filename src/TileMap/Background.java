package TileMap;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(BufferedImage image, double ms) {
		
		this.image = image;
		moveScale = ms;
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)x, (int)y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		if(x > GamePanel.WIDTH){
			dx = -dx;
		}
		if(x < -GamePanel.WIDTH){
			dx = -dx;
		}
		
		if(x < 0) {
			g.drawImage(
				image,
				(int)x + GamePanel.WIDTH,
				(int)y,
				GamePanel.WIDTH,
				GamePanel.HEIGHT,
				null
			);
		}
		if(x > 0) {
			g.drawImage(
				image,
				(int)x - GamePanel.WIDTH,
				(int)y,
				GamePanel.WIDTH,
				GamePanel.HEIGHT,
				null
			);
		}
	}
	
}







