package entities;

import java.awt.Graphics;

import base.Game;
import world.World;

public class EnemySpawner {
	
	private int secondsToPass = 4;
	private final int second=60;
	private int frames=0;
	
	public void tick()
	{
		frames++;
		if(frames>=secondsToPass*second)
		{
			frames=0;
			Game.getEntidades().add(new Enemy(Game.getRandom().nextInt(World.getWidth()*16-32)+16,32));
		}
	}
	
	public void render(Graphics g)
	{
		
	}
}
