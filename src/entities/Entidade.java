package entities;

import java.awt.Color;
import java.awt.Graphics;

import base.Game;

public class Entidade {
	

	protected int x;
	protected int y;	
	protected int speed=1;
	protected int maskX=0,maskY=0,maskWidth=16,maskHeight=16;
	protected int gravitySpeed;
	
	public Entidade(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.yellow);
		g.fillRect(x+maskX-Game.getCamera().getX(), y+maskY-Game.getCamera().getY(),maskWidth, maskHeight);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getMaskX() {
		return maskX;
	}

	public void setMaskX(int maskX) {
		this.maskX = maskX;
	}

	public int getMaskY() {
		return maskY;
	}

	public void setMaskY(int maskY) {
		this.maskY = maskY;
	}

	public int getMaskWidth() {
		return maskWidth;
	}

	public void setMaskWidth(int maskWidth) {
		this.maskWidth = maskWidth;
	}

	public int getMaskHeight() {
		return maskHeight;
	}

	public void setMaskHeight(int maskHeight) {
		this.maskHeight = maskHeight;
	}
}
