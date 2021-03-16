package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;

public class Tile {
	
	protected int x;
	protected int y;
	private int width;
	private int height;
	private boolean changeAble=true;
	
	protected BufferedImage sprite;
	
	public Tile(int x, int y, int width, int height,boolean changeAble, BufferedImage sprite)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.changeAble=changeAble;
		this.sprite=sprite;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, x-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
	}
	
	public boolean getChangeAble()
	{
		return changeAble;
	}
}
