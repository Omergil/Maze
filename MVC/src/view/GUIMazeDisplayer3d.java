package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import algorithms.mazeGenerators.Position;

public class GUIMazeDisplayer3d extends GUIMazeDisplayer {

	final Color wall = new Color(null, 0, 0, 0);
	final Color path = new Color(null, 255, 255, 255);
	final Color character = new Color(null, 237, 28, 36);
	final Color background = new Color(null, 191, 191, 191);
	
	GUIMazeDisplayer3d(Composite parent, int style) {
		super(parent, style);
		setBackground(background);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null, 0,0,0));
				e.gc.setBackground(new Color(null, 0,0,0));
				
				paintMaze(e, characterPosition.getZ() + 1, 0);
				paintMaze(e, characterPosition.getZ(), (getSize().y/4)+(getSize().y/8));
				paintMaze(e, characterPosition.getZ() - 1, (getSize().y/4)*3);
				maze.printMaze();
				System.out.println("Start: x: " + maze.getStartPosition().getX() + " y: " + maze.getStartPosition().getY() + " z: " + maze.getStartPosition().getZ() );
			}
		});
	}
	
	public void paintMaze(PaintEvent e, int floor, int mazeHeight)
	{
		int windowWidth = getSize().x;
		int windowHeight = getSize().y;
		
		int cellWidth = windowWidth/maze.getMaze3d()[0].length;
		int cellHeight = windowHeight/maze.getMaze3d().length/4;
		
		if ((floor == -1) || (floor == maze.getMaze3d()[0][0].length))
		{
			for (int i = maze.getMaze3d().length - 1; i >= 0 ;i--)
			{
				for (int j = 0; j < maze.getMaze3d()[0].length;j++)
				{
					
					int x = j*cellWidth;
					int y = i*cellHeight;
					e.gc.setBackground(wall);
					e.gc.fillRectangle(x,y+(mazeHeight),cellWidth,cellHeight);
				}
			}
		}
		else
		{
			for (int i = maze.getMaze3d().length - 1; i >= 0 ;i--)
			{
				for (int j = 0; j < maze.getMaze3d()[0].length;j++)
				{
					
					int x = j*cellWidth;
					int y = i*cellHeight;
					if(maze.getMaze3d()[i][j][floor] == 1)
					{
						e.gc.setBackground(wall);
						e.gc.fillRectangle(x,y+(mazeHeight),cellWidth,cellHeight);
					}
					if(maze.getMaze3d()[i][j][floor] == 0)
					{
						e.gc.setBackground(path);
						e.gc.fillRectangle(x,y+(mazeHeight),cellWidth,cellHeight);
					}
				}
			}
		}
		e.gc.setBackground(character);
		e.gc.fillRectangle(cellWidth*characterPosition.getX(), cellHeight*characterPosition.getY()+(getSize().y/4)+(getSize().y/8), cellWidth, cellHeight);
	}

	@Override
	public void setCharacterPosition(Position position) {
		characterPosition = position;
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveForwards() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveBackwards() {
		// TODO Auto-generated method stub

	}
	
	public void moveUp() {
		// TODO Auto-generated method stub

	}
	
	public void moveDown() {
		// TODO Auto-generated method stub

	}

}
