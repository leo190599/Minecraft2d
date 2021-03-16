package base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.EnemySpawner;
import entities.Entidade;
import entities.Player;
import menus.Inventory;
import menus.Menu;
import world.Camera;
import world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread thread;
	public final static int width=240;
	public final static int height = 240;
	public final static int scale = 4;
	private boolean isRunning=false;
	private Graphics g;
	private BufferStrategy bs;
	private BufferedImage baseI;
	private static List<Entidade> entidades;
	private static Player player;
	private int curFrame=0;
	private static Camera cam;
	public static final double gravity=0.3;
	private static World world;
	private static SpriteSheet spriteSheet;
	private static Random rand;
	private static int seconds=0;
	private static int minutes=0;
	private Menu menu;
	private static Inventory inventory;
	private EnemySpawner eSpawner;
	
	public Game()
	{
		rand=new Random();
		menu=new Menu();
		spriteSheet=new SpriteSheet();
		inventory=new Inventory();
		baseI=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.setPreferredSize(new Dimension(width*scale,height*scale));
		this.addKeyListener(this);
		this.addMouseListener(this);
		entidades=new ArrayList<Entidade>();
		player=new Player(0,0);
		world=new World(spriteSheet,player);
		int camY=player.getY()-height/2;
		if(camY<0)
		{
			camY=0;
		}
		else if(camY>=world.getHeight()*16-height)
		{
			camY=world.getHeight()*16-height-1;
		}
		cam=new Camera(0,camY);
		eSpawner=new EnemySpawner();
		entidades.add(player);
	}
	
	public static Random getRandom()
	{
		return rand;
	}
		
	public static void restartGame()
	{
		seconds=0;
		minutes=0;
		entidades=new ArrayList<Entidade>();
		player=new Player(0,0);
		world=new World(spriteSheet,player);
		int camY=player.getY()-height/2;
		if(camY<0)
		{
			camY=0;
		}
		else if(camY>=world.getHeight()*16-height)
		{
			camY=world.getHeight()*16-height-1;
		}
		entidades.add(player);
		cam=new Camera(0,0);
		System.out.println(cam.getX()+"       "+cam.getY());
	}
	
	public static World getWorld()
	{
		return world;
	}
	
	public static Camera getCamera()
	{
		//System.out.println(cam.getX());
		return cam;
	}
	
	public static Player getPlayer()
	{
		return player;
	}
	
	public static int getSeconds()
	{
		return seconds;
	}
	
	public static int getMinutes()
	{
		return minutes;
	}
	
	public void tick()
	{
		for(int i=0;i<entidades.size();i++)
		{
			entidades.get(i).tick();
		}
		eSpawner.tick();
		countTime();
	}
	
	private void countTime()
	{
		curFrame++;
		if(curFrame>=60)
		{
			curFrame=0;
			seconds++;
			if(seconds>=60)
			{
				seconds=0;
				minutes++;
				if(minutes%2==0)
				{
					World.cycleDay();
				}
			}
		}
	}
	
	public void render()
	{
		g=baseI.getGraphics();
		g.setColor(Color.black);
		//g.fillRect(0, 0,GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(),
				//GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight());
		//g.setColor(Color.blue);
		g.fillRect(0, 0, width, height);
		world.render(g);
		for(int i=0;i<entidades.size();i++)
		{
			entidades.get(i).render(g);
		}
		g=bs.getDrawGraphics();
		g.drawImage(baseI, 0, 0,width*scale,height*scale,null);
		menu.render(g);
		inventory.render(g);
		bs.show();
	}
	

	public void start()
	{
		isRunning=true;
		thread =new Thread(this);
		thread.start();
	}
	public void stop()
	{
		isRunning=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<Entidade> getEntidades()
	{
		return entidades;
	}
	
	public static SpriteSheet getSpritesheet()
	{
		return spriteSheet;
	}
	
	@Override
	public void run() {
		this.createBufferStrategy(3);
		bs=this.getBufferStrategy();
		//int frames=0;
		
		//double lasTime=System.currentTimeMillis();
		//double curTime;
		
		double lastTime=System.nanoTime();
		final int fps=60;
		final double nsTime=1000000000/fps;
		double currentTime;
		double deltaTime=0;
		this.requestFocus();
		while(isRunning)
		{
			//System.out.println(deltaTime);
			//curTime=System.currentTimeMillis();
			
			currentTime=System.nanoTime();
			deltaTime+=(currentTime-lastTime);
			lastTime=currentTime;
			if(deltaTime>=nsTime)
			{	
				tick();
				render();
				deltaTime=0;
				//frames++;
			}
			
			//if((curTime-lasTime)>=1000)
			//{
				//lasTime=curTime;
				//System.out.println(frames);
				//frames=0;
			//}
		}
		stop();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_A:
				player.setMLeft(true);
			break;
			case KeyEvent.VK_D:
				player.setMRight(true);
			break;
			case KeyEvent.VK_SPACE:
				player.setJump();
			break;
			case KeyEvent.VK_SHIFT:
				player.attack();
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_A:
				player.setMLeft(false);
			break;
			case KeyEvent.VK_D:
				player.setMRight(false);
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(e.getButton())
		{
			case MouseEvent.BUTTON1:
			inventory.select(e.getX(), e.getY());
			break;
			case MouseEvent.BUTTON3:
			inventory.putBlock(e.getX()/scale+getCamera().getX(), e.getY()/scale+getCamera().getY());
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
