import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class TetrisMain 
{
	private static JFrame window;
	public static final int WIDTH=500,HEIGHT=770;
	private static PlayingScreen screen;
	public TetrisMain()
	{
	
	
	}
	public static void main(String[] args) 
	{
		window=new JFrame("Tetris");
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.setSize(WIDTH, HEIGHT);
		window.setVisible(true);
		window.getContentPane().setLayout(new BorderLayout(0, 0));
		screen=new PlayingScreen(window);
		
		window.getContentPane().add(screen);
		screen.setLayout(new BorderLayout(0, 0));
		Thread g=new Thread() {
			@Override
			public void run()
			{
				window.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					
							if(e.getKeyCode()==KeyEvent.VK_RIGHT)
							{
								screen.deltaX(1);
							}
							if(e.getKeyCode()==KeyEvent.VK_LEFT)
							{
								screen.deltaX(-1);
							}
							if(e.getKeyCode()==KeyEvent.VK_DOWN)
							{
									if(screen.getcPiece().isActive() && screen.isFalling())
										screen.getcPiece().setYMod(screen.getcPiece().getYMod()+1);
								
							}
							if(e.getKeyCode()==KeyEvent.VK_UP)
							{
								screen.rotatePiece();
								screen.repaint();
							}
							if(e.getKeyCode()==KeyEvent.VK_SPACE)
							{
								screen.hardDrop();
							}
							if(e.getKeyChar()=='n')
							{
								screen.restart();
							}
							if(e.getKeyChar()=='p')
							{
								System.out.println(!screen.isPaused());
								screen.setPaused(!screen.isPaused());
							}
							if((e.getKeyChar()+"").matches("[0-9]+"))
							{
								if(e.getKeyChar()=='0')
									screen.setLevel(10);
								else	
								screen.setLevel(Integer.parseInt(e.getKeyChar()+""));
							}
				}
			});
				long sleepTime,maxCalcTime=0;
				long start,end,lastCom=System.nanoTime();
				long count=0;
				while(true)
				{
					count++;
					start=System.nanoTime()-lastCom;

					screen.updateField();
					screen.repaint();
					//screen.restart();
					end=System.nanoTime()-lastCom-start;
					if(16-end/1000000>0)
					sleepTime=16-end/1000000;
					else
						sleepTime=0;
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(1000/(sleepTime+end/1000000));
					if(maxCalcTime<end)
					{
						maxCalcTime=end;
						System.out.println((end/1000000.));
					}
					lastCom=System.nanoTime();
					while(screen.isPaused())
					{
						try {
							Thread.sleep(16);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		g.start();
	}
private static double i=0,j=0,l=0,s=0,o=0,t=0,z=0,total=0;
public static void printPercentages()
{
	Tetramino tetra=new Tetramino(screen);
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

total=i+j+l+s+o+t+z;

String print="I percent:"+(100*(i/total))+" "+"J percent:"+(100*(j/total))+" "+"L percent:"+(100*(l/total))+" ";
print=print+"S percent:"+(100*(s/total))+" "+"O percent:"+(100*(o/total))+" "+"T percent:"+(100*(t/total))+" ";
print=print+"Z percent:"+(100*(z/total));

if(total%100000==0)
System.out.println(print+" count: "+total);
}
}
