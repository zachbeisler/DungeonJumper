package HUD;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Heroes.Player;
import Main.Game;
import ResourceManagers.ResourceLoader;

public class HUD {
	
	private Player player;
	
	private BufferedImage image;
	private Font font;
	private Font mono;
	
	public HUD(Player p) {
		player = p;
		try {
			image = Game.res.hud.get("hud");
			font = new Font("Arial", Font.PLAIN, 14);
			mono = new Font("Courier New", Font.PLAIN, 14);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 13, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(
			player.getHealth() + "/" + player.getMaxHealth(),
			30,
			28
		);
		g.drawString(
			player.getMana() + "/" + player.getMaxMana(),
			30,
			48
		);
		
		if(player.qcd == 0){
			g.drawString("Q", 6, 229);
		}else{
			g.drawString(Long.toString(player.qcd/60), 7, 229);
		}
		
		if(player.wcd == 0){
			g.drawString("W", 27, 230);
		}else{
			g.drawString(Long.toString(player.wcd/60), 29, 229);
		}
		
		if(player.ecd == 0){
			g.drawString("E", 51, 229);
		}else{
			g.drawString(Long.toString(player.ecd/60), 51, 229);
		}
		g.setColor(Color.WHITE);
		g.fillRect(120, 5, 64, 11);
		g.setColor(Color.BLACK);
		g.setFont(mono);
		g.drawString(String.format("%08d", player.score), 120, 15);
		
		
		
		
		
	}
	
}













