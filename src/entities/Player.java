package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import base.Game;
import world.World;

public class Player extends Entidade{
	
	private final BufferedImage[] leftSprite= {Game.getSpritesheet().getSprite(0, 0, 16, 16),Game.getSpritesheet().getSprite(0, 16, 16, 16)};
	private final BufferedImage[] rightSprite= {Game.getSpritesheet().getSprite(16, 0, 16, 16),Game.getSpritesheet().getSprite(16, 16, 16, 16)};
	private final BufferedImage leftAttack=Game.getSpritesheet().getSprite(80, 0, 16, 16);
	private final BufferedImage rightAttack=Game.getSpritesheet().getSprite(96, 0, 16, 16);
	private int curFrameIndex=0;
	private int curFrameTime=0;
	private final int maxFrameTime=15;
	private boolean mRight=false;
	private boolean mLeft=false;
	private boolean lookingLeft=false;
	private boolean jumping=false;
	private double fallingSpeed=0;
	private int life=100;
	private boolean punching=false;
	private int punshingCurFrame=0;
	private int maxPunshingFrames=2;
	
	public Player(int x, int y) {
		super(x, y);
		gravitySpeed=4;
		speed=2;
		maskY=2;
		maskHeight=14;
		maskX=4;
		maskWidth=8;
	}

	public int getLife()
	{
		return life;
	}
	
	private void initializeFrames(int iFSize,int dFSize,int uFSize,int rFSize,int lFSize)
	{
		
	}

	public void tick()
	{
		jump();
		fall();
		move();
		checkCollisionEntities();//Possivelmente o java inclui referencias em fors ou em whiles como impedimentos para o trash colector de um endereço
		if(punching)
		{
			punch();
			punshingCurFrame++;
			if(punshingCurFrame>=maxPunshingFrames)
			{
				punshingCurFrame=0;
				punching=false;
			}
		}
	}
	
	private void jump()
	{
		if(jumping)
		{
			if(World.jumpCollider(x, y, maskX, maskY, maskWidth,1))
			{
				fallingSpeed+=Game.gravity;
				y+=fallingSpeed;//Isso deve ser feito antes da proxima checagem, pois, provavelmente, o java internamente converte o y para double, soma com 
				//fallingSpeed e converte o resultado para int

				if(!World.jumpCollider(x, y, maskX, maskY, maskWidth, -(int)fallingSpeed)) 
				{	
					while(World.jumpCollider(x, y, maskX, maskY, maskWidth, 1))
					{
						y-=1;
					}
					fallingSpeed=0;
				}
			}
			else
			{
				fallingSpeed=0;
			}
			if(fallingSpeed>=0)
			{
				jumping=false;
			}
		}
		Game.getCamera().setY(Game.getCamera().clamp(y-Game.height/2+60, 0, Game.getWorld().getHeight()*16-Game.height-1));
	}
	
	private void fall()
	{
		if(World.gravityColliderIsFree(x, y, maskX, maskY, maskWidth, maskHeight, 1)&&!jumping)
		{
			if(fallingSpeed<5)
			{
				fallingSpeed+=Game.gravity;
			}
			if(!World.gravityColliderIsFree(x, y, maskX, maskY, maskWidth, maskHeight, (int)fallingSpeed))
			{
				while(World.gravityColliderIsFree(x, y, maskX, maskY, maskWidth, maskHeight, 1))
				{
					y+=1;
				}
				fallingSpeed=0;
			}
			y+=fallingSpeed;
		}		
		Game.getCamera().setY(Game.getCamera().clamp(y-Game.height/2+60, 0, Game.getWorld().getHeight()*16-Game.height-1));
	}
	
	public void attack()
	{
		punching=true;
	}
	
	private void move()
	{
		if(mRight && World.moveColliderRight(x, y, maskX, maskY, maskWidth, maskHeight, speed))
		{
			lookingLeft=false;
			x+=speed;
			Game.getCamera().setX(Game.getCamera().clamp(x-Game.width/2+8, 0, Game.getWorld().getWidth()*16-Game.width-1));
			//System.out.println(Game.getCamera().getX()+Game.width);
		}
		else if(mLeft && World.moveColliderLeft(x, y, maskX, maskY,maskHeight , -speed))
		{
			lookingLeft=true;
			x-=speed;
			Game.getCamera().setX(Game.getCamera().clamp(x-Game.width/2+8, 0, Game.getWorld().getWidth()*16-Game.width-1));
			//System.out.println(Game.getCamera().getX()+Game.width);
		}
	}
	
	public void setMRight(boolean mRight)
	{
		this.mRight=mRight;
	}
	public void setMLeft(boolean mLeft)
	{
		this.mLeft=mLeft;
	}
	public void setJump()
	{
		if(!World.gravityColliderIsFree(x, y, maskX, maskY, maskWidth, maskHeight, 1))
		{
			fallingSpeed=-6;
			jumping=true;
		}
	}
	
	private void punch()
	{
		//Golpe com 10 pixels de expessura e 2 de offset em relação ao player
		Rectangle r;
		if(lookingLeft)
		{
			r=new Rectangle(x+maskX-12,y+maskY,10,maskHeight);
		}
		else
		{
			r=new Rectangle(x+maskX+maskWidth+2,y+maskY,10,maskHeight);
		}
		
		for(int i=0;i<Game.getEntidades().size();i++)
		{
			Entidade e=Game.getEntidades().get(i);
			if(e instanceof Enemy)
			{
				Rectangle eR=new Rectangle(e.getX()+e.getMaskX(),e.getY()+e.getMaskY(),e.getMaskWidth(),e.getMaskWidth());
				if(r.intersects(eR))
				{
					((Enemy) e).doDamage();
				}
			}
		}
		
	}
	
	private void checkCollisionEntities()
	{
		Rectangle thisColMask=new Rectangle(x+maskX,y+maskY,maskWidth,maskHeight);
		for(int i=0;i<Game.getEntidades().size();i++)
		{
			if(Game.getEntidades().get(i).equals(this))
				continue;
			Entidade otherEntity=Game.getEntidades().get(i);
			Rectangle otherColMask=new Rectangle(otherEntity.getX()+otherEntity.getMaskX(),
					otherEntity.getY()+otherEntity.getMaskY(),
					otherEntity.getMaskWidth(),
					otherEntity.getMaskHeight());
			if(thisColMask.intersects(otherColMask))
			{
				if(otherEntity instanceof Enemy)
				{
					life-=1;
				}
			}
		}
	}
	
	public void render(Graphics g)
	{
		//super.render(g);
		if(curFrameTime>=maxFrameTime)
		{
			curFrameIndex++;
			curFrameTime=0;
			if(curFrameIndex>=leftSprite.length)
			{
				curFrameIndex=0;
			}
		}
		if(lookingLeft)
		{
			g.drawImage(leftSprite[curFrameIndex], x-Game.getCamera().getX(), y-Game.getCamera().getY(),null);
			if(punching)
			{
				g.drawImage(leftAttack, x-16-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
			}
		}
		else
		{
			g.drawImage(rightSprite[curFrameIndex], x-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
			if(punching)
			{
				g.drawImage(rightAttack, x+16-Game.getCamera().getX(), y-Game.getCamera().getY(), null);
			}
		}
		curFrameTime++;
	}
	public void setPosition(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	

}
