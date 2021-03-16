package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.Game;
import base.SpriteSheet;
import entities.Enemy;
import entities.Player;

public class World {
	
	private static int width;
	private static int height;
	private static Tile[] tiles;
	private static boolean isDay=Game.getRandom().nextBoolean();
	
	public static int getWidth()
	{
		return width;
	}
	
	public static int getHeight()
	{
		return height;
	}
	
	public static boolean getIsDay()
	{
		return isDay;
	}
	
	public World(SpriteSheet sprite,Player player)
	{
		width=100;
		height=80;
		tiles=new Tile[width*height];
		String[] possibleItems= {"neve","terra","areia","grama"};
		int divisor=width/possibleItems.length;
		for(int i=0;i<width;i++)
		{
			int initialTile=Game.getRandom().nextInt(3)+14;
			for(int j=0;j<height;j++)
			{
				if(i==0||i==width-1||j==0||j==height-1)
				{
					tiles[i+j*width]=new TileWall(i*16,j*16,16,16,false,Game.getSpritesheet().getSprite(208, 16, 16, 16));
				}
				else
				{
					if(j>initialTile)
					{
						if(Game.getRandom().nextInt(10)<8)
						{
							switch(possibleItems[i/divisor])
							{
								case "neve":
									tiles[i+j*width]=new TileWall(i*16,j*16,16,16,true,Game.getSpritesheet().getSprite(208, 32, 16, 16));
								break;
								case "terra":
									tiles[i+j*width]=new TileWall(i*16,j*16,16,16,true,Game.getSpritesheet().getSprite(208, 16, 16, 16));
								break;
								case "areia":
									tiles[i+j*width]=new TileWall(i*16,j*16,16,16,true,Game.getSpritesheet().getSprite(192, 16, 16, 16));
								break;
								case "grama":
									tiles[i+j*width]=new TileWall(i*16,j*16,16,16,true,Game.getSpritesheet().getSprite(208, 0, 16, 16));
								break;
							}
						}
						else
						{
							tiles[i+j*width]=new TileFloor(i*16,j*16,16,16,true,null);
						}
					}
					else
					{
					tiles[i+j*width]=new TileFloor(i*16,j*16,16,16,true,null);
					}
				}
			}
			Game.getPlayer().setPosition(40, 50);
		}
		
	}
	
	public static void setTile(int x,int y, Tile tile)
	{
		if(tiles[(x/16)+(y/16)*width].getChangeAble())
		{
			tiles[(x/16)+(y/16)*width]=tile;
		}
	}
	
	public static Tile getTile(int x,int y)
	{
		return tiles[(x/16)+(y/16)*width];
	}
	
	public static void cycleDay()
	{
		isDay=!isDay;
	}
	
	public void render(Graphics g)
	{
		int camX=Game.getCamera().getX()>>4;
		int camY=Game.getCamera().getY()>>4;
		
		int camMaxX=camX+(Game.width>>4);
		int camMaxY=camY+(Game.height>>4);
		for(int xx=camX;xx<=camMaxX;xx++)
		{
			for(int yy=camY;yy<=camMaxY;yy++)
			{
				tiles[xx+(yy*width)].render(g);
			}
		}
	}
	public Tile[] getTiles()
	{
		return tiles;
	}
	
	public static void liberateBlock(int x,int y,int dirX, int dirY)
	{
		int xx=x/16;
		int yy=y/16;
		if(tiles[(xx+dirX)+(yy+dirY)*width].getChangeAble() && !(tiles[(xx+dirX)+(yy+dirY)*width] instanceof TileFloor))
		{
			tiles[xx+dirX+(yy+dirY)*width]=new TileFloor((xx+dirX)*16,(yy+dirY)*16,16,16,true,Game.getSpritesheet().getSprite(192, 0, 16, 16));
		}
	}
	
	public static boolean isFree(int x,int y,int maskX,int maskY,int maskWidth, int maskHeight, int pretendedMovimentX,int pretendedMovimentY)
	{
		if(tiles[(x+maskX+pretendedMovimentX)/16+(y+maskY+pretendedMovimentY)/16*width] instanceof TileWall||
		   tiles[(x+maskX+maskWidth-1+pretendedMovimentX)/16+(y+maskY+pretendedMovimentY)/16*width] instanceof TileWall||
		   tiles[(x+maskX+pretendedMovimentX)/16+(y+maskY+maskHeight-1+pretendedMovimentY)/16*width] instanceof TileWall||
		   tiles[(x+maskX+maskWidth-1+pretendedMovimentX)/16+(y+maskY+maskHeight-1+pretendedMovimentY)/16*width] instanceof TileWall)
		{
			return false;
		}
		return true;
	}
	public static boolean gravityColliderIsFree(int x,int y,int maskX,int maskY,int maskWidth, int maskHeight,int gravitySpeed)
	{
		if(tiles[(x+maskX)/16+(y+maskY+maskHeight-1+gravitySpeed)/16*width] instanceof TileWall||
			tiles[(x+maskX+maskWidth-1)/16+(y+maskY+maskHeight-1+gravitySpeed)/16*width] instanceof TileWall)
			{
				return false;
			}
		return true;
	}
	public static boolean moveCollider(int x,int y,int maskX,int maskY,int maskWidth, int maskHeight, int pretendedMovimentX)
	{
		if(tiles[(x+maskX+pretendedMovimentX)/16+(y+maskY)/16*width] instanceof TileWall||
				tiles[(x+maskX+maskWidth-1+pretendedMovimentX)/16+(y+maskY)/16*width] instanceof TileWall||
				tiles[(x+maskX+pretendedMovimentX)/16+(y+maskY+maskHeight-1)/16*width] instanceof TileWall||
				tiles[(x+maskX+maskWidth-1+pretendedMovimentX)/16+(y+maskY+maskHeight-1)/16*width] instanceof TileWall)
			{
				return false;
			}
		return true;
	}
	public static boolean moveColliderRight(int x,int y,int maskX,int maskY,int maskWidth, int maskHeight, int pretendedMovimentX)
	{
		if(tiles[(x+maskX+maskWidth-1+pretendedMovimentX)/16+(y+maskY)/16*width] instanceof TileWall||
			tiles[(x+maskX+maskWidth-1+pretendedMovimentX)/16+(y+maskY+maskHeight-1)/16*width] instanceof TileWall)
			{
				return false;
			}
		return true;
	}
	public static boolean moveColliderLeft(int x,int y,int maskX,int maskY, int maskHeight, int pretendedMovimentX)
	{
		if(tiles[(x+maskX+pretendedMovimentX)/16+(y+maskY)/16*width] instanceof TileWall||
			tiles[(x+maskX+pretendedMovimentX)/16+(y+maskY+maskHeight-1)/16*width] instanceof TileWall)
			{
				return false;
			}
		return true;
	}
	public static boolean jumpCollider(int x,int y,int maskX,int maskY,int maskWidth, int jumpSpeed)
	{
		if(tiles[(x+maskX)/16+(y+maskY-jumpSpeed)/16*width] instanceof TileWall||
			tiles[(x+maskX+maskWidth-1)/16+(y+maskY-jumpSpeed)/16*width] instanceof TileWall)
			{
				return false;
			}
		return true;
	}
}
