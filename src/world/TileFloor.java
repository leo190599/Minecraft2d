package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;

public class TileFloor extends Tile{

	public TileFloor(int x, int y, int width, int height,boolean changeAble, BufferedImage sprite) {
		super(x, y, width, height, changeAble, sprite);
		// TODO Auto-generated constructor stub
	}

	public void render(Graphics g)
	{
		if(World.getIsDay())
		{
			g.drawImage(Game.getSpritesheet().getSprite(192, 0, 16, 16), x-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
		}
		else
		{
			g.drawImage(Game.getSpritesheet().getSprite(176, 0, 16, 16), x-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
		}
	}
	
}
