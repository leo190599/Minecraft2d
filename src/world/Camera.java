package world;

public class Camera {
	private int x=0;
	private int y=0;
	
	public Camera(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public int clamp(int position, int minPos, int maxPos)
	{
		if(position<minPos)
		{
			position=minPos;
		}
		else if(position>maxPos)
		{
			position=maxPos;
		}
		return position;
	}
	
	public void setX(int x)
	{
		this.x=x;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setY(int y)
	{
		this.y=y;
	}
	
	public int getY()
	{
		return y;
	}
}
