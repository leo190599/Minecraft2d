package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import base.Game;

public class Menu {
	
	private String curTime;
	
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(Game.width*Game.scale-230, 25, 200, 20);
		g.setColor(Color.green);
		g.fillRect(Game.width*Game.scale-230, 25, Game.getPlayer().getLife()*2, 20);
		g.setFont(new Font("arial",Font.PLAIN,24));
		g.setColor(Color.white);
		g.drawString("Life: ", Game.width*Game.scale-280, 42);
		if(Game.getMinutes()<10)
		{
			curTime="0"+Game.getMinutes();
		}
		else
		{
			curTime=Integer.toString(Game.getMinutes());
		}
		if(Game.getSeconds()<10)
		{
			curTime+=":0"+Game.getSeconds();
		}
		else
		{
			curTime+=":"+Game.getSeconds();
		}
		g.drawString("Time: "+curTime, 30, 42);
	}
}
