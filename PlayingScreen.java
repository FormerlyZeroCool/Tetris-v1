import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayingScreen extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame window;
	private int columns=10,rows=20;
	private boolean field[][],isPaused=false;
	private Tetramino cPiece,nPiece;
	private Color black=new Color(0,0,0),white=new Color(255,255,255);
	private ArrayList<ArrayList> colorParent;
	private ArrayList<Tetramino> pieces;
	private int width,height;
	private int rowsCleared=0,score=0,level=1;
	private double ip,lp,jp,zp,sp,op,tp;
	private String occurences="1 2 3 4 5 6 7",occArr[];
public PlayingScreen(JFrame w)
	{
	refreshColorParent();
	pieces=new ArrayList<Tetramino>();
	field=new boolean[columns][rows+1];
	for(int x=0;x<columns;x++)
	{
		for(int y=0;y<rows;y++)
		{
			field[x][y]=false;
		}
	}
	for(int x=0;x<columns;x++)
	{
		field[x][rows]=true;
	}
		window=w;
		setBounds(window.getHeight());
		setcPiece();
		
	}
private void setBounds(int h) {
	// TODO Auto-generated method stub
	this.setSize((int)(h*(columns/(rows*1.))+(h*(columns/(rows*1.))*(5./columns))),h-(h/rows));	
	this.setBounds((window.getWidth()-this.getWidth())/2,0,(int)(h*(columns/(rows*1.))+(h*(columns/(rows*1.))*(5./columns))),h-(h/rows));		
	width=(int)(h*(columns/(rows*1.)));
	height=h-(h/rows);
}
private void refreshColorParent() {
	// TODO Auto-generated method stub
	if(colorParent==null) colorParent=new ArrayList<ArrayList>();
	synchronized(colorParent) {
	colorParent=new ArrayList<ArrayList>();
	for(int y=0;y<rows;y++)
	{
		colorParent.add(new ArrayList<Color>());
		for(int x=0;x<columns;x++)
			colorParent.get(y).add(black);
	}
}}
public int getFieldWidth()
{
	return width;
}
public int getFieldHeight()
{
	return height;
}
@Override
public void paint(Graphics g)
	{
	g.drawString("Level: "+level,width+30,6*(height/rows));
	g.drawString("Score: "+score,width+30,7*(height/rows));
	for(int i=0;i<occArr.length;i++)
	{
		g.drawString(occArr[i], width+30, (8+i)*(height/rows));
		
	}
	if(this.getWidth()!=(int)(window.getHeight()*(10/25.)))
	{
		setBounds(window.getHeight());
	}
	synchronized(colorParent)
	{
	for(int x=0;x<columns;x++)
		for(int y=0;y<rows;y++)
		{
		g.setColor((Color)colorParent.get(y).get(x));
		g.fillRect(
				x*(width/columns), y*(height/rows), 
				width/columns, (height/rows)-1);
		
		g.setColor(white);
		g.drawRect(
				x*(width/columns), y*(height/rows), 
				width/columns, (height/rows)-1);
		if(field[x][y])
		{
			g.drawRect(x*(width/columns)+(width/columns)/4,
					y*(height/rows)+(height/rows)/4,
					(width/columns)-(width/columns)/2,
					(height/rows)-(height/rows)/2);
		}
		
		}
	
	g.setColor(new Color(0,0,255));
	g.drawLine(columns*(width/columns), 0,columns*(width/columns), 4*(height/rows));
	nPiece.drawPreview(g);
	if(cPiece.isActive())
			cPiece.draw(g);
		g.setColor(black);
	
	}}
private void shiftScreen(int deltaY,int startRow,int endRow)
{
	boolean[][] nArr=new boolean[columns][rows];
	ArrayList<ArrayList> nCArr=new ArrayList<ArrayList>();
	for(int y=0;y<rows;y++)
	{
		nCArr.add(new ArrayList<Color>());
		for(int x=0;x<columns;x++)
			nCArr.get(y).add(white);
	}
	for(int x=0;x<columns;x++)
	{
		for(int y=0;y<rows;y++)
		{
			nArr[x][y]=field[x][y];
			nCArr.get(y).set(x, colorParent.get(y).get(x));
		}
	}
	
	if(deltaY<0)
	for(int y=endRow;y>=startRow;y--)
	{
		if(y+deltaY>0)
		{
		for(int x=columns-1;x>=0;x--)
		{
			nArr[x][y]=field[x][y+deltaY];
			nCArr.get(y).set(x, colorParent.get(y+deltaY).get(x));		
		}
		}
	}
	else
	for(int y=endRow;y<=startRow;y++)
	{
		if(y+deltaY<rows)
		{
		for(int x=0;x<columns;x++)
		{
			nArr[x][y]=field[x][y+deltaY];
			nCArr.get(y).set(x, colorParent.get(y+deltaY).get(x));
		}
		}
	}
	

	for(int x=0;x<columns;x++)
	{
		for(int y=0;y<rows;y++)
		{
			field[x][y]=nArr[x][y];
		}
	}
	synchronized(colorParent)
	{
		colorParent=nCArr;
	}
}
private int counter=0;
public void updateField()
{
	//System.out.println("piece type id:"+cPiece.getShapeId());
	if(!cPiece.isActive())
	{
		synchronized(cPiece) {
			setcPiece();
		}
	}
	//tick tetris sprite
	counter++;
	int interval=(60/level);
	if(level>60)
		interval=1;
	if(counter%interval==0)
	{
		if(isFalling())
			cPiece.setYMod(cPiece.getYMod()+1);
		else//sleepTime after 
		{
			long start,lastCom=System.nanoTime(),end,sleepTime;
			for(int i=0;i<30;i++)
			{
				start=System.nanoTime()-lastCom;
				adjustOverlapping();
				repaint();
				//screen.restart();
				//System.out.println(count);
				end=System.nanoTime()-lastCom-start;

				if(16-end/1000000>0)
				sleepTime=16-end/1000000;
				else
					sleepTime=0;
				//if(sleepTime!=16)
				//	System.out.println(sleepTime);
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!isFalling())
				deActivateCPiece();
		}
	}
	for(int x=0;x<columns;x++)
		if(field[x][0])
		{
			restart();
		}
	adjustOverlapping();
	boolean deleteRow;
	int index=0;
	ArrayList<Int> indexes= new ArrayList<Int>();
	for(int y=0; y<rows;y++)
	{
		deleteRow=true;
		for(int x=0;x<columns && deleteRow==true;x++)
		{
			index=y;
			if(!field[x][y])
			{
				deleteRow=false;
			}
		}
		if(deleteRow)
		{ 
			indexes.add(new Int(index));
		}
	}
	int deleteCount=0;
	for(Int i:indexes)
	{
		deleteRow(i.getIndex());
		deleteCount++;
		rowsCleared++;
	}
	updateScore(deleteCount);
}
private boolean lastClearTetris=false;
private void updateScore(int deleteCount) {

	
	if(deleteCount/4>=1)
	{
		if(lastClearTetris)
			score+=1200*(deleteCount/4);
		else
			score+=800*(deleteCount/4);
		score+=100*(deleteCount%4);
		lastClearTetris=true;
	}
	else 
	{
		score+=100*(deleteCount);
		lastClearTetris=false;
	}
	//score+=(level-1)*100*deleteCount;
	if(score/(Math.pow(2, level)*100.)>=1.)
	{
		level++;
	}
	//System.out.println(score);
}
private void deleteRow(int index) {
	for(int x=0;x<columns;x++)
	{
		field[x][index]=false;
	}
	shiftScreen(-1,0,index);
	rowsCleared++;
}
public int getRows()
{
	return columns;
}
public int getColumns()
{
	return rows;
}
public void deltaX(int d)
{
	boolean move=false;

	//System.out.println(cPiece.getMaxDrawnWidth()+" "+cPiece.getXMod()+" "+d);
	int l=cPiece.getLength();
	int len=cPiece.getLength()+cPiece.getYMod();
	int sy=cPiece.getYMod(),sx=cPiece.getXMod();
	int dw=cPiece.getMaxDrawnWidth();
	int wid=dw+sx;
	int xadj=0;
	boolean prevColumnEmpty=true;
	for(int x=0;x<=cPiece.getWidth() && prevColumnEmpty;x++)
	{
		boolean columnEmpty=true;
		for(int y=0;y<l;y++)
		{
		if(cPiece.map[x][y])
		columnEmpty=false;	
		}
		if(columnEmpty)
		{
		xadj++;
		}
		prevColumnEmpty=columnEmpty;
	}
	
	if((d>0 || sx+d+xadj>=0) && (sx+d+dw<columns || d<0))
		move=true;
	
	for(int x=sx;x<=wid && move && x+d>=0;x++)
	{
		//System.out.println(move);
		for(int y=sy;y<len;y++)
		{		
				//if(y!=26)
				if(field[x+d][y])
				{
					if(cPiece.map[x-sx][y-sy] && x-sx>=0)
					move=false;
					//System.out.println((x+d)+","+y);
				}
				

				//System.out.println(move)
		}
	}

	if(move)
		cPiece.setXMod(sx+d);	
}

public boolean isFalling()
{
	boolean prevColumnEmpty=true;
	boolean move=true;
	int xadj=0;
	//synchronize(cPiece);
	
	for(int x=0;x<=cPiece.getWidth() && prevColumnEmpty;x++)
	{
		boolean columnEmpty=true;
		for(int y=0;y<cPiece.getLength();y++)
		{
		if(cPiece.map[x][y])
		columnEmpty=false;	
		}
		if(columnEmpty && cPiece.getXMod()<0)
		{
		xadj++;
		}
		prevColumnEmpty=columnEmpty;
	}
	int ymod=cPiece.getYMod();
	int xmod;
	if(cPiece.getXMod()==-1)
	{
		xmod=0;
	}
	else
	{
		xmod=cPiece.getXMod();
	}
	for(int y=ymod; y<=cPiece.getMaxDrawnHeight()+ymod && move;y++)
	{
		for(int x=xmod+xadj;x<=cPiece.getMaxDrawnWidth()+xmod && move && x<columns;x++)
		{
				if(field[x-xadj][y+1] && cPiece.map[x-(xmod)][y-ymod])
					{
						move=false;

						//System.out.println("Collision: "+(x-xadj)+","+(y+1)+" piece:"+(x-(xmod))+","+(y-ymod));
					}
			
			
			
			
		}
	}
	

	if(cPiece.getYMod()+cPiece.getMaxDrawnHeight()>=rows-1)
	{
		
		if(cPiece.getYMod()+cPiece.getMaxDrawnHeight()>=rows-1)
		move=false;
	}
	
	return	move;
	
	
}

public void restart() 
{
	for(int x=0;x<columns;x++)
	{
		for(int y=0;y<rows;y++)
		{
			field[x][y]=false;
		}
	}
	score=0;
	level=1;
	rowsCleared=0;
	refreshColorParent();
	pieces.clear();
	setcPiece();
}
private void deActivateCPiece()
{
	for(int x1=0;x1<getcPiece().getWidth();x1++)
	{
		for(int y1=0;y1<getcPiece().getLength();y1++)
		{
			if(getcPiece().map[x1][y1])
			{
				field[getcPiece().getXMod()+x1][getcPiece().getYMod()+y1]=true;
				colorParent.get(y1+cPiece.getYMod()).set(x1+cPiece.getXMod(), cPiece.getColor());	
			}
		}
	}
	cPiece.setActive(false);
}

public Tetramino getcPiece() {
	return cPiece;
}
public void setcPiece() {
	if(nPiece==null)
		nPiece=new Tetramino(this);
	cPiece=nPiece;
	nPiece=new Tetramino(this);
	pieces.add(cPiece);
	calcPiecePercentage();
	//cPiece=new Tetramino(5,this);
}
private void calcPiecePercentage()
{
	double i=0,j=0,l=0,s=0,o=0,t=0,z=0,total=0;
	for(Tetramino tetra:pieces)
	{
		if(tetra.getShapeId()==Tetramino.I)
			i++;
		else if(tetra.getShapeId()==Tetramino.J)
			j++;
		else if(tetra.getShapeId()==Tetramino.L)
			l++;
		else if(tetra.getShapeId()==Tetramino.S)
			s++;
		else if(tetra.getShapeId()==Tetramino.O)
			o++;
		else if(tetra.getShapeId()==Tetramino.T)
			t++;
		else if(tetra.getShapeId()==Tetramino.Z)
			z++;
	}
	total=i+j+l+s+o+t+z;
	
	ip=Math.round(10000*(i/total))/100.;
	jp=Math.round(10000*(j/total))/100.;
	lp=Math.round(10000*(l/total))/100.;
	sp=Math.round(10000*(s/total))/100.;
	op=Math.round(10000*(o/total))/100.;
	tp=Math.round(10000*(t/total))/100.;
	zp=Math.round(10000*(z/total))/100.;
	occurences="I percent:"+ip+"%"+"J percent:"+jp+"%"+"L percent:"+lp+"%";
	occurences=occurences+"S percent:"+sp+"%"+"O percent:"+op+"%"+"T percent:"+tp+"%";
	occurences=occurences+"Z percent:"+zp;
	occArr=occurences.split("%");
}
private int rotateCounter=0;
public void rotatePiece() 
{
	rotateCounter++;
	int l=cPiece.getLength(),w=cPiece.getWidth(),activeL=0,activeW=0,startX=w,startY=l;
	for(int y=0;y<l;y++)
	{
		for(int x=0;x<w;x++)
		{
			if(cPiece.map[x][y] && x>activeW)
				activeW=x;
			if(cPiece.map[x][y] && y>activeL)
				activeL=y;
			if(cPiece.map[x][y] && x<startX)
				startX=x;
			if(cPiece.map[x][y] && y<startY)
				startY=y;
			
		}
	}
	boolean nArr[][]=new boolean[w][l];
	for(int y=0;y<l;y++)
	{
		for(int x=0;x<w;x++)
		{
			nArr[x][y]=cPiece.map[x][y];
			
		}
	}
	if(cPiece.getShapeId()>=0 && cPiece.getShapeId()<=3 || cPiece.getShapeId()==6)
	{
		boolean nArr1[][]=new boolean[cPiece.getWidth()][cPiece.getLength()];
		int maxX=cPiece.getMaxDrawnWidth()-1,maxY=cPiece.getMaxDrawnHeight()-1,minX=-1,minY=-1;
		for(int y=0;y<cPiece.getLength();y++)
		{
			for(int x=0;x<cPiece.getWidth();x++)
			{
				int cx=x-1,cy=y-1;
				int nx=cy,ny=-1*cx;
				nx++;ny++;
				nArr1[nx][ny]=nArr[x][y];
			}
		}
		for(int y=0;y<l;y++)
		{
			for(int x=0;x<w;x++)
			{
				nArr[x][y]=nArr1[x][y];
				
			}
		}
		
	}
	else if(cPiece.getShapeId()==Tetramino.I)
	{
		if(rotateCounter%2==0)
		{
			nArr[0][0]=false;
			nArr[0][1]=false;
			nArr[0][2]=false;
			nArr[0][3]=false;
			nArr[1][0]=true;
			nArr[1][1]=true;
			nArr[1][2]=true;
			nArr[1][3]=true;
			nArr[2][0]=false;
			nArr[2][1]=false;
			nArr[2][2]=false;
			nArr[2][3]=false;
			nArr[3][0]=false;
			nArr[3][1]=false;
			nArr[3][2]=false;
			nArr[3][3]=false;
		}
		else 
		{
			nArr[0][0]=false;
			nArr[0][1]=false;
			nArr[0][2]=true;
			nArr[0][3]=false;
			nArr[1][0]=false;
			nArr[1][1]=false;
			nArr[1][2]=true;
			nArr[1][3]=false;
			nArr[2][0]=false;
			nArr[2][1]=false;
			nArr[2][2]=true;
			nArr[2][3]=false;
			nArr[3][0]=false;
			nArr[3][1]=false;
			nArr[3][2]=true;
			nArr[3][3]=false;
		}
	}
	boolean[][] a=new boolean[cPiece.getWidth()][cPiece.getLength()];
	for(int y=0;y<l;y++)
	{
		for(int x=0;x<w;x++)
		{
			a[x][y]=cPiece.map[x][y];
			
		}
	}
	for(int y=0;y<l;y++)
	{
		for(int x=0;x<w;x++)
		{
			cPiece.map[x][y]=nArr[x][y];
			
		}
	}
	
	if(cPiece.getXMod()<0 ||  cPiece.getMaxDrawnWidth()+cPiece.getXMod()>=columns)
	{
		System.out.println("x Adjustment Roation "+(cPiece.getMaxDrawnWidth()+cPiece.getXMod())+" "+getRows()+" "+cPiece.getXMod());
		if(cPiece.getXMod()>0)
		{
			deltaX(getRows()-(cPiece.getMaxDrawnWidth()+cPiece.getXMod()+1));
			System.out.println(cPiece.getXMod()+" adjusted"+" "+(getRows()-(cPiece.getMaxDrawnWidth()+cPiece.getXMod())+1));
		}
		else
			deltaX(-1*cPiece.getXMod());
	}
	else
	{
	
	for(int y=0;y<l;y++)
	{
		for(int x=0;x<w;x++)
		{
			cPiece.map[x][y]=nArr[x][y];
			
		}
	}
	}	
}
public void adjustOverlapping()//in development
{
	
	boolean collision=false,left=true,right=true,up=true;
	int cX=-1,cY=-1,xMod=cPiece.getXMod(),yMod=cPiece.getYMod(),l=cPiece.getLength(),w=cPiece.getWidth();
	
	for(int y=0;y<l;y++)
	{
		for(int x=0;x<w;x++)
		{

			cX=x+xMod;
			cY=y+yMod;
			if(x+xMod<columns && x>0)
			{
				if(cPiece.map[x][y]&&field[cX][cY])
				{
					collision=true;
					if(cX-1>0) 
					{
					if(field[cX-1][cY])
						left=false;
					}
					else
						left=false;
					if(cX+1<columns)
					{
						if(field[cX+1][cY])
							right=false;	
					}
					else
						right=false;
				}
			}
			else 
			{
				collision=false;
				if(x+xMod>columns)
					right=false;
				if(x<=0)
				left=false;
			}
			
			
			
			
			if(yMod>0) 
			{
				if(x+xMod>0 && cPiece.map[x][y])
				if(field[x+xMod][y+yMod-1])
					up=false;
			}
			else
				up=false;
				
		}
	}
	if(collision)
	{
		System.out.println("Collision: "+collision+" Right: "+right+" Left: "+left);
		if(right)
			deltaX(1);
		else if(left)
		{
			deltaX(-1);
		}
		else
		{
			cPiece.setYMod(cPiece.getYMod()-1);
		}
	}
}
public boolean isPaused() {
	return isPaused;
}
public void setPaused(boolean isPaused) {
	this.isPaused = isPaused;
}
public void hardDrop()
{
	while(isFalling())
	{
		cPiece.setYMod(cPiece.getYMod()+1);
	}
	deActivateCPiece();
}
public void setLevel(int l)
{
	level=l;
}
}
