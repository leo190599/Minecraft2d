package base;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;

public class WindowGame extends JFrame{
	
	private static Game jogo;
	private static WindowGame janela;
	
	public static void main(String[] args)
	{
		//GraphicsDevice g= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		jogo=new Game();
		janela=new WindowGame();
		janela.add(jogo);
		janela.setIconImage(jogo.getSpritesheet().getSprite(0, 16, 16, 16));
		//janela.setUndecorated(true);
		janela.pack();
		//janela.setSize(g.getDisplayMode().getWidth(),g.getDisplayMode().getHeight());
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setLocationRelativeTo(null);
		janela.setAlwaysOnTop(true);
		//janela.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(jogo.getSpritesheet().getSprite(224, 224, 16, 16), new Point(0,0), "cursor"));
		janela.setVisible(true);
		jogo.start();
	}
	
	public WindowGame()
	{
		super("Minecraft");
	}
}
