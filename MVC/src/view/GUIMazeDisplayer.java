package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public abstract class GUIMazeDisplayer extends Canvas {

	Maze3d maze;
	Position characterPosition;
	
	GUIMazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setMaze(Maze3d maze)
	{
		this.maze = maze;
		setCharacterPosition(this.maze.getStartPosition());
	}

	public abstract void setCharacterPosition(Position position);
	public abstract void moveLeft();
	public abstract void moveRight();
	public abstract void moveForwards();
	public abstract void moveBackwards();
	
}
