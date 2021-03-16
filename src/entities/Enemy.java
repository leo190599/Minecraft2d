package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import base.Game;
import world.TileFloor;
import world.TileWall;
import world.World;

public class Enemy extends Entidade{

	private BufferedImage lSprite=Game.getSpritesheet().getSprite(32, 0, 16, 16);
	private BufferedImage rSprite=Game.getSpritesheet().getSprite(48, 0, 16, 16);
	private int speed=1;
	private boolean goingLeft=true;
	private final int maxVida=Game.getRandom().nextInt(40)+60;
	private int vida=maxVida;
	
	public Enemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		maskX=1;
		maskY=5;
		maskWidth=14;
		maskHeight=11;
		gravitySpeed=2;
	}

	public void doDamage()
	{
		vida-=20;
		if(vida<=0)
		{
			Game.getEntidades().remove(this);
		}
	}
	
	public void tick()
	{
		if(World.gravityColliderIsFree(x, y, maskX, maskY, maskWidth, maskHeight, gravitySpeed))
		{
			y+=gravitySpeed;
		}
		else if(World.moveColliderLeft(x, y, maskX, maskY, maskHeight, -speed) && goingLeft)
		{
			x-=speed;
		}
		else if(World.moveColliderRight(x, y, maskX, maskY, maskWidth, maskHeight, speed)&& !goingLeft)
		{
			x+=speed;
		}
		else
		{
			if(goingLeft)
			{
				World.liberateBlock(x, y, 0, 0);
			}
			else
			{
				World.liberateBlock(x+16, y,0, 0);
			}
			goingLeft=!goingLeft;
		}
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		if(goingLeft)
		{
			g.drawImage(lSprite, x-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
		}
		else
		{
			g.drawImage(rSprite, x-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
		}
		g.setColor(Color.red);
		g.fillRect(x-Game.getCamera().getX()-maxVida/20, y-Game.getCamera().getY()-2, maxVida/4, 3);
		g.setColor(Color.green);
		g.fillRect(x-Game.getCamera().getX()-maxVida/20, y-Game.getCamera().getY()-2, vida/4, 3);
	}
}