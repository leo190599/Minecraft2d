package menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import base.Game;
import entities.Entidade;
import world.TileFloor;
import world.TileWall;
import world.World;

public class Inventory {
	
	private int selected=0;
	private int size=6;
	private int cellSize=64;
	private String[] items={"grama","terra","areia","neve","",""};
	private int initialPositionX;
	private int initialPositionY;
	
	public Inventory()
	{
		initialPositionX=Game.width*Game.scale/2-size*cellSize/2;
		initialPositionY=Game.height*Game.scale-cellSize;
	}

	public void render(Graphics g)
	{
		
		for(int i=0;i<size;i++)
		{
			g.setColor(Color.gray);
			g.fillRect(initialPositionX+i*cellSize, initialPositionY, cellSize-1, cellSize-1);
			if(i==selected)
			{
				g.setColor(Color.red);
			}
			else
			{
				g.setColor(Color.black);
			}
			g.drawRect(initialPositionX+i*cellSize, initialPositionY, cellSize-1, cellSize-1);
			switch(items[i])
			{
				case "grama":
					g.drawImage(Game.getSpritesheet().getSprite(208, 0, 16, 16), initialPositionX+i*cellSize+10, initialPositionY+10,cellSize-20,cellSize-20, null);
				break;
				case "terra":
					g.drawImage(Game.getSpritesheet().getSprite(208, 16, 16, 16), initialPositionX+i*cellSize+10, initialPositionY+10,cellSize-20,cellSize-20, null);
				break;
				case "neve":
					g.drawImage(Game.getSpritesheet().getSprite(208, 32, 16, 16), initialPositionX+i*cellSize+10, initialPositionY+10,cellSize-20,cellSize-20, null);
				break;
				case "areia":
					g.drawImage(Game.getSpritesheet().getSprite(192, 16, 16, 16), initialPositionX+i*cellSize+10, initialPositionY+10,cellSize-20,cellSize-20, null);
				break;
				default:
				break;
			}
		}	
	}
	
	public void select(int mX,int mY)
	{
		if(mX > initialPositionX && mX < initialPositionX+size*cellSize && mY > initialPositionY && mY<initialPositionY+cellSize)
		{
			selected = (mX-initialPositionX)/cellSize;
		}
	}
	
	public void putBlock(int mX,int mY)
	{
		if(!isOccupiedByEntity(mX,mY))
		{
			switch(items[selected])
			{
				case "grama":
					World.setTile(mX, mY, new TileWall(mX/16*16,mY/16*16,16,16,true,Game.getSpritesheet().getSprite(208, 0, 16, 16)));
				break;
				case "terra":
					World.setTile(mX, mY, new TileWall(mX/16*16,mY/16*16,16,16,true,Game.getSpritesheet().getSprite(208, 16, 16, 16)));
				break;
				case "areia":
					World.setTile(mX, mY, new TileWall(mX/16*16,mY/16*16,16,16,true,Game.getSpritesheet().getSprite(192, 16, 16, 16)));
				break;
				case "neve":
					World.setTile(mX, mY, new TileWall(mX/16*16,mY/16*16,16,16,true,Game.getSpritesheet().getSprite(208, 32, 16, 16)));
				break;
				default:
					World.setTile(mX, mY, new TileFloor(mX/16*16,mY/16*16,16,16,true,Game.getSpritesheet().getSprite(192, 0, 16, 16)));
				break;
			}
		}
	}
	private boolean isOccupiedByEntity(int x,int y)
	{
		boolean occupied=false;
		Rectangle tileRec=new Rectangle(x/16*16,y/16*16,16,16);
		//System.out.println(x/16*16+"  "+y/16*16);
		for(int i=0;i<Game.getEntidades().size();i++)
		{
			Entidade e=Game.getEntidades().get(i);
			Rectangle eRec=new Rectangle(e.getX()+e.getMaskX(),e.getY()+e.getMaskY(),e.getMaskWidth(),e.getMaskHeight());
			//System.out.println((e.getX()+e.getMaskX())+"     "+(e.getY()+e.getMaskY()));
			if(tileRec.intersects(eRec))
			{
				occupied=true;
				break;
			}
		}
		return occupied;
	}
}
