import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

public class Tetramino 
{
	public boolean map[][];
	private int xMod=0,yMod=0,drawCount=0;
	private PlayingScreen screen;
	public static final int S=0,T=1,L=2,J=3,O=4,I=5,Z=6;
	private int shapeId,length,width;
	private boolean isActive;
	private Color color,black=new Color(0,0,0),white=new Color(255,255,255);
	public Tetramino(PlayingScreen s)
	{
		shapeId=(int) Math.round(Math.random()*6);
		if(shapeId!=S && shapeId!=Z && Math.random()<.142)//even the distrubution of pieces created
		{
			if(Math.random()<=.5)
				shapeId=S;
			else
				shapeId=Z;
		}
			
		
			
			isActive=true;
		screen=s;
		switch(shapeId)
		{
		case S:
			color=new Color(255,0,0);
			length=3;
			width=3;
			map=new boolean[width][length];
			map[0][0]=false;
			map[0][1]=true;
			map[1][0]=true;
			map[1][1]=true;
			map[2][0]=true;
			map[2][1]=false;
			break;
		case T:
			color=new Color(255,160,0);
			length=3;
			width=3;
			map=new boolean[width][length];
			map[0][0]=true;
			map[0][1]=false;
			map[1][0]=true;
			map[1][1]=true;
			map[2][0]=true;
			map[2][1]=false;
			break;
		case L:
			color=new Color(0,225,0);
			length=3;
			width=3;
			map=new boolean[width][length];
			map[0][0]=false;
			map[0][1]=true;
			map[1][0]=false;
			map[1][1]=true;
			map[2][0]=true;
			map[2][1]=true;
			break;
		case J:
			color=new Color(0,0,255);
			length=3;
			width=3;
			map=new boolean[width][length];
			map[0][0]=true;
			map[0][1]=false;
			map[1][0]=true;
			map[1][1]=false;
			map[2][0]=true;
			map[2][1]=true;
			break;
		case O:
			color=new Color(130,0,150);
			length=2;
			width=2;
			map=new boolean[width][length];
			map[0][0]=true;
			map[0][1]=true;
			map[1][0]=true;
			map[1][1]=true;
			break;
		case I:
			color=new Color(0,160,160);
			shapeId=I;
			length=4;
			width=4;
			map=new boolean[width][length];
			map[0][0]=false;
			map[0][1]=false;
			map[0][2]=false;
			map[0][3]=false;
			map[1][0]=true;
			map[1][1]=true;
			map[1][2]=true;
			map[1][3]=true;
			map[2][0]=false;
			map[2][1]=false;
			map[2][2]=false;
			map[2][3]=false;
			map[3][0]=false;
			map[3][1]=false;
			map[3][2]=false;
			map[3][3]=false;
			break;
		case Z:
			color=new Color(230,230,0);
			length=3;
			width=3;
			map=new boolean[width][length];
			map[0][0]=true;
			map[0][1]=false;
			map[1][0]=true;
			map[1][1]=true;
			map[2][0]=false;
			map[2][1]=true;
			break;
		
		}
		
		xMod=screen.getRows()/2-width/2;yMod=0;
	}

	public void draw(Graphics g)
	{
		drawCount++;
		for(int x=0;x<width && x<screen.getColumns();x++)
		{
			for(int y=0;y<length && y<screen.getRows();y++)
			{
				if(map[x][y])
				{
					int xU=x+xMod;
					int yU=y+yMod;
					g.setColor(color);
					g.fillRect(xU*(screen.getFieldWidth()/screen.getRows()),
							yU*(screen.getFieldHeight()/screen.getColumns()),
							(screen.getFieldWidth()/screen.getRows()),
							(screen.getFieldHeight()/screen.getColumns()));

					g.setColor(white);
					g.drawRect(xU*(screen.getFieldWidth()/screen.getRows()),
							yU*(screen.getFieldHeight()/screen.getColumns()),
							(screen.getFieldWidth()/screen.getRows()),
							(screen.getFieldHeight()/screen.getColumns()));

					g.drawRect(xU*(screen.getFieldWidth()/screen.getRows())+(screen.getFieldWidth()/screen.getRows())/4,
							yU*(screen.getFieldHeight()/screen.getColumns())+(screen.getFieldHeight()/screen.getColumns())/4,
							(screen.getFieldWidth()/screen.getRows())-(screen.getFieldWidth()/screen.getRows())/2,
							(screen.getFieldHeight()/screen.getColumns())-(screen.getFieldHeight()/screen.getColumns())/2);
				}
			}
		}

		
	}
	public int getXMod()
	{
		return xMod;
	}
	public void setXMod(int d)
	{
		xMod=d;
	}
	public int getColDrawnHeight(int colIndex)
	{
		int height=0;
		for(int y=0;y<length;y++)
		{
			if(map[colIndex][y])
			{
				height=y;
			}
		}
			
		return height;
	}
	public int getMaxDrawnHeight()
	{
		int max=0;
		for(int x=0;x<width;x++)
		{
			 if(getColDrawnHeight(x)>max)
				 max=getColDrawnHeight(x);
		}
		return max;
	}
	public int getMaxDrawnWidth()
	{
		int max=0;
		for(int y=0;y<length;y++)
		{
			 if(getRowDrawnWidth(y)>max)
				 max=getRowDrawnWidth(y);
		}
		return max;
	}
	public int getRowDrawnWidth(int rowIndex)
	{
		int w=0;
		for(int x=0;x<width;x++)
		{
			if(map[x][rowIndex])
			{
				w=x;
			}
		}
			
		return w;
	}
	public int getYMod()
	{
		return yMod;
	}
	public void setYMod(int d)
	{
		yMod=d;
	}
	public int getWidth() {
		
		return width;
	}
	public int getLength() {
		
		return length;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}
	public int getShapeId() {
		// TODO Auto-generated method stub
		return shapeId;
	}
	public int getDrawCount() {
		return drawCount;
	}
	public void setDrawCount(int drawCount) {
		this.drawCount = drawCount;
	}
	private int getMaxEmptyRow()
	{
		int index=0;
		boolean rowEmpty=true;
		for(int y=0;y<length && rowEmpty;y++)
		{
			for(int x=0;x<width;x++)
			{
				if(map[x][y])
					rowEmpty=false;
			}
			if(rowEmpty)
			{
				index=y;
			}
		}
		return index;
	}
	private int getMaxEmptyColumn()
	{
		int index=0;
		boolean columnEmpty=true;
		for(int x=0;x<width && columnEmpty;x++)
		{
			for(int y=0;y<length;y++)
			{
				if(map[x][y])
					columnEmpty=false;
			}
			if(columnEmpty)
			{
				index++;
			}
		}
		return index;
	}
	public void drawPreview(Graphics g) {
		int rowHeight=(screen.getFieldHeight()/screen.getColumns());
		int columnWidth=screen.getFieldWidth()/screen.getRows();
		g.setColor(black);
		g.fillRect((screen.getRows()+1+getMaxEmptyColumn())*columnWidth-(columnWidth/2),rowHeight/2,(2+getMaxDrawnWidth())*columnWidth,(getMaxDrawnHeight()+2)*rowHeight);
		
		for(int x=0;x<width;x++)
		{
			for(int y=0;y<length;y++)
			{
				if(map[x][y])
				{
					int xU=x+screen.getRows()+1;
					int yU=y+1;
					g.setColor(color);
					g.fillRect(xU*columnWidth,
							yU*rowHeight,
							columnWidth,
							rowHeight);

					g.setColor(white);
					g.drawRect(xU*columnWidth,
							yU*rowHeight,
							columnWidth,
							rowHeight);

					g.drawRect(xU*columnWidth+columnWidth/4,
							yU*rowHeight+rowHeight/4,
							columnWidth-columnWidth/2,
							columnWidth-columnWidth/2);
				}
			}
		}
		
	}
}
