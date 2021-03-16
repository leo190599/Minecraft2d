package base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private static BufferedImage imagemSpritesheet;
	
	public SpriteSheet()
	{
		try {
			imagemSpritesheet=ImageIO.read(new File("./res/Spritesheet_pac_man.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x,int y, int w,int h)
	{
		return imagemSpritesheet.getSubimage(x, y, w, h);
	}
	
}
